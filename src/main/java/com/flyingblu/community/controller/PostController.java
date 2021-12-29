package com.flyingblu.community.controller;

import com.flyingblu.community.dto.PostGrpcApi;
import com.flyingblu.community.dto.PostServiceGrpc;
import com.flyingblu.community.interceptor.AuthInterceptor;
import com.flyingblu.community.model.Post;
import com.flyingblu.community.service.PostService;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;

@GrpcService
public class PostController extends PostServiceGrpc.PostServiceImplBase {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }


    // TODO: error handling

    @Override
    public void createPost(PostGrpcApi.CreatePostReq request,
                           StreamObserver<PostGrpcApi.CreatePostResp> responseObserver) {
        String uid = AuthInterceptor.UID.get();
        Post post = new Post()
                .withTitle(request.getTitle())
                .withBody(request.getBody())
                .withCommunityId(request.getCommunityId())
                .withUid(uid);
        postService.create(post);
        var resp = PostGrpcApi.CreatePostResp.newBuilder().setPostId(post.getId()).build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void removePost(PostGrpcApi.RemovePostReq request,
                           StreamObserver<Empty> responseObserver) {
        String uid = AuthInterceptor.UID.get();
        Post post = postService.getById(request.getPostId());
        // Check if the user has privilege to remove the post
        if (post == null) {
            responseObserver.onError(new NoSuchElementException());
            return;
        }
        String postUid = post.getUid();
        if (postUid == null || !postUid.equals(uid)) {
            responseObserver.onError(new IllegalAccessException());
            return;
        }

        postService.removeById(request.getPostId());
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void getPost(PostGrpcApi.GetPostReq request, StreamObserver<PostGrpcApi.PostDetails> responseObserver) {
        Post post = postService.getById(request.getPostId());
        // TODO: JOIN
    }
}
