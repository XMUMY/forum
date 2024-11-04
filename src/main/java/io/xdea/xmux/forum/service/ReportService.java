package io.xdea.xmux.forum.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import io.xdea.xmux.forum.mapper.*;
import io.xdea.xmux.forum.model.*;

import java.util.List;

@Service
public class ReportService {
    private final ReportMapper reportMapper;
    private final ReportTypeMapper reportTypeMapper;

    @Autowired
    public ReportService(ReportMapper reportMapper, ReportTypeMapper reportTypeMapper) {
        this.reportMapper = reportMapper;
        this.reportTypeMapper = reportTypeMapper;
    }

    public boolean isReported(String uid, int targetId, int targetType) {
        ReportExample reportExample = new ReportExample();
        reportExample.createCriteria().andUidEqualTo(uid).andTargetIdEqualTo(targetId).andTargetTypeEqualTo(targetType);
        return reportMapper.countByExample(reportExample) >= 1;
    }

    public boolean report(String uid, int targetId, int targetType, int typeId, String reason) {
        return reportMapper.insertSelective(new Report()
                .withUid(uid)
                .withTargetId(targetId)
                .withTargetType(targetType)
                .withTypeId(typeId)
                .withReason(reason)) == 1;
    }

    public List<ReportType> getReportTypes() {
        return reportTypeMapper.selectByExample(new ReportTypeExample());
    }
}
