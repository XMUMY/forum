package com.flyingblu.community.controller;

import com.flyingblu.community.dto.PostGrpcApi;
import com.flyingblu.community.dto.PostServiceGrpc;
import com.flyingblu.community.model.Post;
import com.flyingblu.community.service.PostService;
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


    @Override
    public void createPost(PostGrpcApi.CreatePostReq request,
                           StreamObserver<PostGrpcApi.CreatePostResp> responseObserver) {
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setBody(request.getBody());
        post.setCommunityId(request.getCommunityId());
        postService.create(post);
        var resp = PostGrpcApi.CreatePostResp.newBuilder().setPostId(post.getId()).build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }
}
