package io.xdea.xmux.forum.controller;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import io.xdea.xmux.forum.dto.ReportGrpcApi;
import io.xdea.xmux.forum.service.*;
import io.xdea.xmux.forum.model.*;
import io.xdea.xmux.forum.interceptor.AuthInterceptor;

import java.util.List;

public abstract class ReportController extends SavedController {
    protected final ReportService reportService;

    protected ReportController(ForumService forumService, NotifService notifService, ThreadService threadService,
                               PostService postService, SavedService savedService, ReportService reportService) {
        super(forumService, notifService, threadService, postService, savedService);
        this.reportService = reportService;
    }

    @Override
    public void reportPostOrThread(ReportGrpcApi.ReportPostOrThreadReq request, StreamObserver<ReportGrpcApi.ReportPostOrThreadResp> responseObserver) {
        String uid = AuthInterceptor.getUid();

        // Check if the user has already reported the target
        boolean isReported = true;
        if (request.hasThreadId()) {
            isReported = reportService.isReported(uid, request.getThreadId(), 0);
        } else if (request.hasPostId()) {
            isReported = reportService.isReported(uid, request.getPostId(), 1);
        }

        if (isReported) {
            responseObserver.onNext(ReportGrpcApi.ReportPostOrThreadResp.newBuilder().setSuccess(false).setMessage("report.msg.already_reported").build());
            responseObserver.onCompleted();
            return;
        }

        // Check if the target exists
        if (request.hasThreadId()) {
            if (threadService.getById(request.getThreadId()) == null) {
                responseObserver.onNext(ReportGrpcApi.ReportPostOrThreadResp.newBuilder().setSuccess(false).setMessage("report.msg.target_not_found").build());
                responseObserver.onCompleted();
                return;
            }
        } else if (request.hasPostId()) {
            if (postService.getById(request.getPostId()) == null) {
                responseObserver.onNext(ReportGrpcApi.ReportPostOrThreadResp.newBuilder().setSuccess(false).setMessage("report.msg.target_not_found").build());
                responseObserver.onCompleted();
                return;
            }
        }

        // Report the target
        if (request.hasThreadId()) {
            reportService.report(uid, request.getThreadId(), 0, request.getTypeId(), request.getDescription());
        } else if (request.hasPostId()) {
            reportService.report(uid, request.getPostId(), 1, request.getTypeId(), request.getDescription());
        }
        responseObserver.onNext(ReportGrpcApi.ReportPostOrThreadResp.newBuilder().setSuccess(true).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getReportTypes(Empty request, StreamObserver<ReportGrpcApi.GetReportTypesResp> responseObserver) {
        List<ReportType> reportTypes = reportService.getReportTypes();
        ReportGrpcApi.GetReportTypesResp.Builder response = ReportGrpcApi.GetReportTypesResp.newBuilder();
        reportTypes.forEach(reportType -> response.addTypes(ReportGrpcApi.ReportType.newBuilder()
                .setId(reportType.getId())
                .setName(reportType.getName())
                .build()));
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }
}
