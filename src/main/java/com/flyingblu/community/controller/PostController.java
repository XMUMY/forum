package com.flyingblu.community.controller;

import com.flyingblu.community.dto.PostGrpcApi;
import com.flyingblu.community.dto.PostServiceGrpc;
import com.flyingblu.community.model.Post;
import com.flyingblu.community.service.PostService;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class PostController extends PostServiceGrpc.PostServiceImplBase {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }


    // TODO: Auth, error handling

    @Override
    public void createPost(PostGrpcApi.CreatePostReq request,
                           StreamObserver<PostGrpcApi.CreatePostResp> responseObserver) {
        Post post = new Post()
                .withTitle(request.getTitle())
                .withBody(request.getBody())
                .withCommunityId(request.getCommunityId());
        postService.create(post);
        var resp = PostGrpcApi.CreatePostResp.newBuilder().setPostId(post.getId()).build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void removePost(PostGrpcApi.RemovePostReq request,
                           StreamObserver<Empty> responseObserver) {
        postService.removeById(request.getPostId());
        responseObserver.onCompleted();
    }

    @Override
    public void getPost(PostGrpcApi.GetPostReq request, StreamObserver<PostGrpcApi.PostDetails> responseObserver) {
        Post post = postService.getById(request.getPostId());
        // TODO: JOIN
    }
}
