package io.xdea.xmux.forum.controller;

import io.xdea.xmux.forum.dto.ForumGrpc;
import io.xdea.xmux.forum.dto.ForumGrpcApi;
import io.xdea.xmux.forum.model.Forum;
import io.xdea.xmux.forum.service.ForumService;
import io.grpc.stub.StreamObserver;

import java.util.List;

/*
    Can only get forums, creation and deletion are done manually in the database
 */
public abstract class ForumController extends ForumGrpc.ForumImplBase {
    protected final ForumService forumService;

    protected ForumController(ForumService forumService) {
        this.forumService = forumService;
    }

    @Override
    public void getForums(ForumGrpcApi.GetForumsReq request, StreamObserver<ForumGrpcApi.GetForumsResp> responseObserver) {
        // TODO: modify service
        List<Forum> forums = forumService.get(request.getFrom(), request.getCount());
        var builder = ForumGrpcApi.GetForumsResp.newBuilder();
        forums.forEach(c -> {
            var com = ForumGrpcApi.ForumDetail.newBuilder()
                    .setId(c.getId())
                    .setCreatorUid(c.getCreatorUid())
                    .setTitle(c.getTitle())
                    .setDescription(c.getDescription());
            builder.addForums(com);
        });
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
