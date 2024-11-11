package io.xdea.xmux.forum;

import io.grpc.stub.StreamObserver;
import io.xdea.xmux.forum.controller.ForumController;
import io.xdea.xmux.forum.dto.ForumGrpcApi;


import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class ForumControllerTest {

    @Autowired
    private ForumController forumController;

    private static final String TEST_UID = "cst1709364";

    @BeforeAll
    static void setupForumsData(@Autowired JdbcTemplate jdbcTemplate) {
        // Insert test forums that will be used by both forum and thread tests
        String sql = """
            INSERT INTO forum.forum (title, description, create_at, creator_uid)
            VALUES 
                ('General Discussion', 'Forum for general topics', ?, ?),
                ('Academic Discussion', 'Forum for academic topics', ?, ?),
                ('Campus Life', 'Discuss campus activities and events', ?, ?)
            """;
        
        Timestamp now = Timestamp.from(Instant.now());
        jdbcTemplate.update(sql,
            now, TEST_UID,
            now, TEST_UID,
            now, TEST_UID
        );
    }

    @AfterAll
    public static void cleanUpDatabase(@Autowired Flyway flyway) {
        flyway.clean();
    }

    @Test
    void testGetForums() {
        // Create a mock StreamObserver to capture the response
        @SuppressWarnings("unchecked")
        StreamObserver<ForumGrpcApi.GetForumsResp> responseObserver = mock(StreamObserver.class);

        // Create the request
        ForumGrpcApi.GetForumsReq request = ForumGrpcApi.GetForumsReq.newBuilder()
            .setFrom(0)
            .setCount(10)
            .build();

        // Call the controller endpoint
        forumController.getForums(request, responseObserver);

        // Capture the response
        org.mockito.ArgumentCaptor<ForumGrpcApi.GetForumsResp> responseCaptor = 
            org.mockito.ArgumentCaptor.forClass(ForumGrpcApi.GetForumsResp.class);
        Mockito.verify(responseObserver).onNext(responseCaptor.capture());
        Mockito.verify(responseObserver).onCompleted();

        // Get the captured response
        ForumGrpcApi.GetForumsResp response = responseCaptor.getValue();
        List<ForumGrpcApi.ForumDetail> forums = response.getForumsList();

        // Verify the results
        assertNotNull(forums);
        assertEquals(3, forums.size());
        
        // Verify first forum details
        ForumGrpcApi.ForumDetail firstForum = forums.get(0);
        assertEquals("General Discussion", firstForum.getTitle());
        assertEquals("Forum for general topics", firstForum.getDescription());
        assertEquals(TEST_UID, firstForum.getCreatorUid());

        // Verify second forum details
        ForumGrpcApi.ForumDetail secondForum = forums.get(1);
        assertEquals("Academic Discussion", secondForum.getTitle());
        assertEquals("Forum for academic topics", secondForum.getDescription());
        
        // Verify third forum details
        ForumGrpcApi.ForumDetail thirdForum = forums.get(2);
        assertEquals("Campus Life", thirdForum.getTitle());
        assertEquals("Discuss campus activities and events", thirdForum.getDescription());
    }

    @Test
    void testGetForumsWithPagination() {
        @SuppressWarnings("unchecked")
        StreamObserver<ForumGrpcApi.GetForumsResp> responseObserver1 = mock(StreamObserver.class);
        @SuppressWarnings("unchecked")
        StreamObserver<ForumGrpcApi.GetForumsResp> responseObserver2 = mock(StreamObserver.class);

        // Test getting forums with different pagination parameters
        ForumGrpcApi.GetForumsReq firstPageRequest = ForumGrpcApi.GetForumsReq.newBuilder()
            .setFrom(0)
            .setCount(2)
            .build();
        
        ForumGrpcApi.GetForumsReq secondPageRequest = ForumGrpcApi.GetForumsReq.newBuilder()
            .setFrom(1)
            .setCount(2)
            .build();

        // Get both pages
        forumController.getForums(firstPageRequest, responseObserver1);
        forumController.getForums(secondPageRequest, responseObserver2);

        // Capture responses
        org.mockito.ArgumentCaptor<ForumGrpcApi.GetForumsResp> firstPageCaptor = 
            org.mockito.ArgumentCaptor.forClass(ForumGrpcApi.GetForumsResp.class);
        org.mockito.ArgumentCaptor<ForumGrpcApi.GetForumsResp> secondPageCaptor = 
            org.mockito.ArgumentCaptor.forClass(ForumGrpcApi.GetForumsResp.class);

        Mockito.verify(responseObserver1).onNext(firstPageCaptor.capture());
        Mockito.verify(responseObserver2).onNext(secondPageCaptor.capture());

        List<ForumGrpcApi.ForumDetail> firstPage = firstPageCaptor.getValue().getForumsList();
        List<ForumGrpcApi.ForumDetail> secondPage = secondPageCaptor.getValue().getForumsList();

        // Verify pagination results
        assertEquals(2, firstPage.size());
        assertEquals(1, secondPage.size());
        
        // Verify first page forums
        ForumGrpcApi.ForumDetail firstForum = firstPage.get(0);
        assertEquals("General Discussion", firstForum.getTitle());
        assertEquals("Forum for general topics", firstForum.getDescription());
        assertEquals(TEST_UID, firstForum.getCreatorUid());

        ForumGrpcApi.ForumDetail secondForum = firstPage.get(1);
        assertEquals("Academic Discussion", secondForum.getTitle());
        assertEquals("Forum for academic topics", secondForum.getDescription());
        assertEquals(TEST_UID, secondForum.getCreatorUid());

        // Verify second page forum
        ForumGrpcApi.ForumDetail thirdForum = secondPage.get(0);
        assertEquals("Campus Life", thirdForum.getTitle());
        assertEquals("Discuss campus activities and events", thirdForum.getDescription());
        assertEquals(TEST_UID, thirdForum.getCreatorUid());

        // Verify completion
        Mockito.verify(responseObserver1).onCompleted();
        Mockito.verify(responseObserver2).onCompleted();
    }
}