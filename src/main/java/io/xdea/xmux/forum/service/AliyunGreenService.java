package io.xdea.xmux.forum.service;

import com.aliyun.green20220302.models.TextModerationResponse;
import com.aliyun.green20220302.models.TextModerationResponseBody;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.aliyun.green20220302.Client;
import com.aliyun.green20220302.models.TextModerationRequest;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.sentry.Sentry;
import io.sentry.SentryLevel;
import io.xdea.xmux.forum.mapper.CensoredContentMapper;
import io.xdea.xmux.forum.model.CensoredContent;

@Service
public class AliyunGreenService {
    private static final Logger logger = LoggerFactory.getLogger(AliyunGreenService.class);
    private final Client aliyunGreenClient;
    private final CensoredContentMapper censoredContentMapper;
    private final String textService;

    @Autowired
    public AliyunGreenService(Client aliyunGreenClient, CensoredContentMapper censoredContentMapper,
            @Value("${aliyun.green.textService}") String textService) {
        this.aliyunGreenClient = aliyunGreenClient;
        this.censoredContentMapper = censoredContentMapper;
        this.textService = textService;
    }

    public enum ContentType {
        THREAD(0),
        POST(1);

        private final int value;

        ContentType(int value) {
            this.value = value;
        }
    }

    public boolean checkText(String text, String uid, ContentType contentType) {
        HashMap<String, Object> requestParams = new HashMap<>();
        requestParams.put("content", text);
        Gson gson = new Gson();
        String requestJson = gson.toJson(requestParams);

        TextModerationRequest request = new TextModerationRequest()
                .setService(textService)
                .setServiceParameters(requestJson);

        try {
            TextModerationResponse response = aliyunGreenClient.textModeration(request);
            if (response.getStatusCode() != 200) {
                logger.error("Response error. Status: {}", response.getStatusCode());
                Sentry.captureMessage("Response error. Status: " + response.getStatusCode(), SentryLevel.ERROR);
                return false;
            }

            TextModerationResponseBody result = response.getBody();

            Integer code = result.getCode();
            if (code != 200) {
                logger.warn("Text moderation failed. Code: {}", code);
                Sentry.captureMessage("Text moderation failed. Result: " + gson.toJson(result), SentryLevel.WARNING);
                return false;
            }

            TextModerationResponseBody.TextModerationResponseBodyData data = result.getData();
            // parse results
            String labels = data.getLabels();
            if (StringUtils.isBlank(labels)) {
                // No sensitive content detected
                return true;
            }

            // Record the content that is detected as sensitive
            censoredContentMapper.insertSelective(new CensoredContent()
                    .withUid(uid)
                    .withContent(text)
                    .withContentType(0)
                    .withReason(gson.toJson(data))
                    .withTypeId(contentType.value));

            return false;
        } catch (Exception e) {
            logger.error("Error during text moderation", e);
            Sentry.captureException(e);
            return false;
        }
    }
}
