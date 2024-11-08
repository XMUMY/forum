package io.xdea.xmux.forum;

import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import io.xdea.xmux.forum.controller.ThreadController;
import io.xdea.xmux.forum.dto.CommonGrpcApi;
import io.xdea.xmux.forum.dto.ThreadGrpcApi;
import io.xdea.xmux.forum.interceptor.AuthInterceptor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class ThreadControllerTest {

    @Autowired
    private ThreadController threadController;

    private static final String TEST_UID = "cst1709364";
    private static int TEST_FORUM_ID;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeAll
    static void setupTestData(@Autowired JdbcTemplate jdbcTemplate) {

        TEST_FORUM_ID = jdbcTemplate.queryForObject(
                "SELECT id FROM forum.forum WHERE title = 'General Discussion'",
                Integer.class
        );

        // Insert some test threads
        String threadSql = """
                INSERT INTO forum.thread (
                    forum_id, title, body, body_type, create_at, update_at, 
                    last_update, uid, likes, posts, pinned, digest
                )
                VALUES 
                    (?, 'First Thread', '{"content": "Test content 1"}', 31, ?, ?, ?, ?, 0, 0, false, false),
                    (?, 'Second Thread', '{"content": "Test content 2", "images": []}', 30, ?, ?, ?, ?, 0, 0, false, false),
                    (?, 'Third Thread', '{"content": "Test content 3", "images": []}', 30, ?, ?, ?, ?, 0, 0, false, false)
                """;

        Timestamp now = Timestamp.from(Instant.now());
        jdbcTemplate.update(threadSql,
                TEST_FORUM_ID, now, now, now, TEST_UID,
                TEST_FORUM_ID, now, now, now, TEST_UID,
                TEST_FORUM_ID, now, now, now, TEST_UID
        );
    }

    @BeforeEach
    void setup() {
        // Mock the AuthInterceptor to return our test UID
        try (MockedStatic<AuthInterceptor> mockedStatic = Mockito.mockStatic(AuthInterceptor.class)) {
            mockedStatic.when(AuthInterceptor::getUid).thenReturn(TEST_UID);
        }
    }

    @Test
    @Order(1)
    void testCreateThread() {
        @SuppressWarnings("unchecked")
        StreamObserver<ThreadGrpcApi.CreateThreadResp> responseObserver = mock(StreamObserver.class);

        // Create a thread with plain content
        ThreadGrpcApi.CreateThreadReq request = ThreadGrpcApi.CreateThreadReq.newBuilder()
                .setForumId(TEST_FORUM_ID)
                .setTitle("Test Thread 4")
                .setPlainContent(CommonGrpcApi.PlainContent.newBuilder()
                        .setContent("This is a test thread content")
                        .addImages(CommonGrpcApi.Image.newBuilder().setUrl("https://example.com/image1.jpg").build())
                        .addImages(CommonGrpcApi.Image.newBuilder().setUrl("https://example.com/image2.jpg").build())
                        .build())
                .build();

        threadController.createThread(request, responseObserver);

        // Capture and verify the response
        org.mockito.ArgumentCaptor<ThreadGrpcApi.CreateThreadResp> responseCaptor =
                org.mockito.ArgumentCaptor.forClass(ThreadGrpcApi.CreateThreadResp.class);
        Mockito.verify(responseObserver).onNext(responseCaptor.capture());
        Mockito.verify(responseObserver).onCompleted();

        ThreadGrpcApi.CreateThreadResp response = responseCaptor.getValue();
        assertTrue(response.getThreadId() == 4);
    }

    @Test
    @Order(2)
    void testCreateThreadWithMarkdownContent() {
        @SuppressWarnings("unchecked")
        StreamObserver<ThreadGrpcApi.CreateThreadResp> responseObserver = mock(StreamObserver.class);

        // Create a thread with markdown content
        ThreadGrpcApi.CreateThreadReq request = ThreadGrpcApi.CreateThreadReq.newBuilder()
                .setForumId(TEST_FORUM_ID)
                .setTitle("Test Thread")
                .setMarkdownContent(CommonGrpcApi.MarkdownContent.newBuilder()
                        .setContent("This is a test thread content")
                        .build())
                .build();

        threadController.createThread(request, responseObserver);

        // Capture and verify the response
        org.mockito.ArgumentCaptor<ThreadGrpcApi.CreateThreadResp> responseCaptor =
                org.mockito.ArgumentCaptor.forClass(ThreadGrpcApi.CreateThreadResp.class);
        Mockito.verify(responseObserver).onNext(responseCaptor.capture());
        Mockito.verify(responseObserver).onCompleted();

        ThreadGrpcApi.CreateThreadResp response = responseCaptor.getValue();
        assertTrue(response.getThreadId() == 5);
    }

    @Test
    @Order(3)
    void testGetThreads() {
        @SuppressWarnings("unchecked")
        StreamObserver<ThreadGrpcApi.GetThreadsResp> responseObserver = mock(StreamObserver.class);

        // Create request to get threads
        ThreadGrpcApi.GetThreadsReq request = ThreadGrpcApi.GetThreadsReq.newBuilder()
                .setForumId(TEST_FORUM_ID)
                .setOffset(0)
                .setCount(10)
                .build();

        threadController.getThreads(request, responseObserver);

        // Capture and verify the response
        org.mockito.ArgumentCaptor<ThreadGrpcApi.GetThreadsResp> responseCaptor =
                org.mockito.ArgumentCaptor.forClass(ThreadGrpcApi.GetThreadsResp.class);
        Mockito.verify(responseObserver).onNext(responseCaptor.capture());
        Mockito.verify(responseObserver).onCompleted();

        ThreadGrpcApi.GetThreadsResp response = responseCaptor.getValue();
        List<ThreadGrpcApi.Thread> threads = response.getThreadsList();

        // Verify the results
        assertNotNull(threads);
        assertEquals(5, threads.size());

        // Verify first thread details
        ThreadGrpcApi.Thread firstThread = threads.get(0);
        assertEquals("First Thread", firstThread.getTitle());
        assertEquals(ThreadGrpcApi.Thread.BodyCase.MARKDOWNCONTENT, firstThread.getBodyCase());
        assertEquals("Test content 1", firstThread.getMarkdownContent().getContent());
        assertEquals(TEST_UID, firstThread.getUid());
        assertEquals(0, firstThread.getLikes());
        assertEquals(0, firstThread.getPosts());
        assertFalse(firstThread.getPinned());
        assertFalse(firstThread.getDigest());

        // Verify fourth thread details
        ThreadGrpcApi.Thread fourthThread = threads.get(3);
        assertEquals("Test Thread 4", fourthThread.getTitle());
        assertEquals(ThreadGrpcApi.Thread.BodyCase.PLAINCONTENT, fourthThread.getBodyCase());
        assertEquals("This is a test thread content", fourthThread.getPlainContent().getContent());
        assertEquals(2, fourthThread.getPlainContent().getImagesCount());
        assertEquals("https://example.com/image1.jpg", fourthThread.getPlainContent().getImages(0).getUrl());
        assertEquals("https://example.com/image2.jpg", fourthThread.getPlainContent().getImages(1).getUrl());
        assertEquals(TEST_UID, fourthThread.getUid());
        assertEquals(0, fourthThread.getLikes());
        assertEquals(0, fourthThread.getPosts());
        assertFalse(fourthThread.getPinned());
        assertFalse(fourthThread.getDigest());
    }


    @Test
    @Order(4)
    void testRemoveThread() {
        @SuppressWarnings("unchecked")
        StreamObserver<Empty> responseObserver = mock(StreamObserver.class);

        // Get first thread ID
        int threadId = jdbcTemplate.queryForObject(
                "SELECT id FROM forum.thread WHERE title = 'Third Thread'",
                Integer.class
        );

        // Test removing the thread
        ThreadGrpcApi.RemoveThreadReq request = ThreadGrpcApi.RemoveThreadReq.newBuilder()
                .setThreadId(threadId)
                .build();

        threadController.removeThread(request, responseObserver);

        // Verify the response
        Mockito.verify(responseObserver).onNext(Empty.getDefaultInstance());
        Mockito.verify(responseObserver).onCompleted();

        // Verify thread was removed (soft delete) in database
        boolean exists = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) = 1 FROM forum.thread WHERE id = ? AND status = 1",
                Boolean.class,
                threadId
        );
        assertTrue(exists);
    }

    @Test
    @Order(5)
    void testGetThreadsWithPagination() {
        @SuppressWarnings("unchecked")
        StreamObserver<ThreadGrpcApi.GetThreadsResp> responseObserver1 = mock(StreamObserver.class);
        @SuppressWarnings("unchecked")
        StreamObserver<ThreadGrpcApi.GetThreadsResp> responseObserver2 = mock(StreamObserver.class);

        // Test getting threads with different pagination parameters
        ThreadGrpcApi.GetThreadsReq firstPageRequest = ThreadGrpcApi.GetThreadsReq.newBuilder()
                .setForumId(TEST_FORUM_ID)
                .setOffset(0)
                .setCount(2)
                .build();

        ThreadGrpcApi.GetThreadsReq secondPageRequest = ThreadGrpcApi.GetThreadsReq.newBuilder()
                .setForumId(TEST_FORUM_ID)
                .setOffset(2)
                .setCount(2)
                .build();

        // Get both pages
        threadController.getThreads(firstPageRequest, responseObserver1);
        threadController.getThreads(secondPageRequest, responseObserver2);

        // Capture responses
        org.mockito.ArgumentCaptor<ThreadGrpcApi.GetThreadsResp> firstPageCaptor =
                org.mockito.ArgumentCaptor.forClass(ThreadGrpcApi.GetThreadsResp.class);
        org.mockito.ArgumentCaptor<ThreadGrpcApi.GetThreadsResp> secondPageCaptor =
                org.mockito.ArgumentCaptor.forClass(ThreadGrpcApi.GetThreadsResp.class);

        Mockito.verify(responseObserver1).onNext(firstPageCaptor.capture());
        Mockito.verify(responseObserver2).onNext(secondPageCaptor.capture());

        List<ThreadGrpcApi.Thread> firstPage = firstPageCaptor.getValue().getThreadsList();
        List<ThreadGrpcApi.Thread> secondPage = secondPageCaptor.getValue().getThreadsList();

        // Verify pagination results
        assertEquals(2, firstPage.size());
        assertEquals(2, secondPage.size());

        // Verify completion
        Mockito.verify(responseObserver1).onCompleted();
        Mockito.verify(responseObserver2).onCompleted();
    }

    @Test
    @Order(6)
    void testLikeThread() {
        @SuppressWarnings("unchecked")
        StreamObserver<Empty> responseObserver = mock(StreamObserver.class);

        // Get first thread ID from database
        int threadId = jdbcTemplate.queryForObject(
                "SELECT id FROM forum.thread WHERE title = 'First Thread'",
                Integer.class
        );

        // Test liking a thread
        ThreadGrpcApi.LikeThreadReq likeRequest = ThreadGrpcApi.LikeThreadReq.newBuilder()
                .setThreadId(threadId)
                .setLike(1)  // 1 for like
                .build();

        threadController.likeThread(likeRequest, responseObserver);

        // Verify the response
        Mockito.verify(responseObserver).onNext(Empty.getDefaultInstance());
        Mockito.verify(responseObserver).onCompleted();

        // Verify the like was recorded in database
        int likes = jdbcTemplate.queryForObject(
                "SELECT likes FROM forum.thread WHERE id = ?",
                Integer.class,
                threadId
        );
        assertEquals(1, likes);

        // Test unliking the thread
        ThreadGrpcApi.LikeThreadReq unlikeRequest = ThreadGrpcApi.LikeThreadReq.newBuilder()
                .setThreadId(threadId)
                .setLike(0)  // 0 for unlike
                .build();

        threadController.likeThread(unlikeRequest, responseObserver);
        // Verify the response
        Mockito.verify(responseObserver).onNext(Empty.getDefaultInstance());
        Mockito.verify(responseObserver).onCompleted();

        // Verify the like was removed
        likes = jdbcTemplate.queryForObject(
                "SELECT likes FROM forum.thread WHERE id = ?",
                Integer.class,
                threadId
        );
        assertEquals(0, likes);

        // Test dislike the thread
        ThreadGrpcApi.LikeThreadReq dislikeRequest = ThreadGrpcApi.LikeThreadReq.newBuilder()
                .setThreadId(threadId)
                .setLike(-1)
                .build();

        threadController.likeThread(dislikeRequest, responseObserver);
        // Verify the response
        Mockito.verify(responseObserver).onNext(Empty.getDefaultInstance());
        Mockito.verify(responseObserver).onCompleted();

        // Verify the dislike was recorded in database
        likes = jdbcTemplate.queryForObject(
                "SELECT likes FROM forum.thread WHERE id = ?",
                Integer.class,
                threadId
        );
        assertEquals(-1, likes);

        // Test like the thread again
        threadController.likeThread(likeRequest, responseObserver);
        // Verify the response
        Mockito.verify(responseObserver).onNext(Empty.getDefaultInstance());
        Mockito.verify(responseObserver).onCompleted();

        // Verify the like was recorded in database
        likes = jdbcTemplate.queryForObject(
                "SELECT likes FROM forum.thread WHERE id = ?",
                Integer.class,
                threadId
        );
        assertEquals(1, likes);

        // Test dislike the thread, then unlike it
        threadController.likeThread(dislikeRequest, responseObserver);
        // Verify the response
        Mockito.verify(responseObserver).onNext(Empty.getDefaultInstance());
        Mockito.verify(responseObserver).onCompleted();

        // Verify the dislike was recorded
        likes = jdbcTemplate.queryForObject(
                "SELECT likes FROM forum.thread WHERE id = ?",
                Integer.class,
                threadId
        );
        assertEquals(-1, likes);

        threadController.likeThread(unlikeRequest, responseObserver);
        // Verify the response
        Mockito.verify(responseObserver).onNext(Empty.getDefaultInstance());
        Mockito.verify(responseObserver).onCompleted();

        // Verify the like was removed
        likes = jdbcTemplate.queryForObject(
                "SELECT likes FROM forum.thread WHERE id = ?",
                Integer.class,
                threadId
        );
        assertEquals(0, likes);
    }

    @Test
    @Order(7)
    void testPinThread() {
        @SuppressWarnings("unchecked")
        StreamObserver<Empty> responseObserver = mock(StreamObserver.class);

        // Get first thread ID
        int threadId = jdbcTemplate.queryForObject(
                "SELECT id FROM forum.thread WHERE title = 'First Thread'",
                Integer.class
        );

        // Test pinning the thread
        ThreadGrpcApi.PinThreadReq request = ThreadGrpcApi.PinThreadReq.newBuilder()
                .setThreadId(threadId)
                .build();

        threadController.pinThread(request, responseObserver);

        // Verify the response
        Mockito.verify(responseObserver).onNext(Empty.getDefaultInstance());
        Mockito.verify(responseObserver).onCompleted();

        // Verify thread was pinned in database
        boolean isPinned = jdbcTemplate.queryForObject(
                "SELECT pinned FROM forum.thread WHERE id = ?",
                Boolean.class,
                threadId
        );
        assertTrue(isPinned);

        // Test unpinning the thread
        threadController.pinThread(request, responseObserver);
        // Verify the response
        Mockito.verify(responseObserver).onNext(Empty.getDefaultInstance());
        Mockito.verify(responseObserver).onCompleted();

        // Verify thread was unpinned in database
        isPinned = jdbcTemplate.queryForObject(
                "SELECT pinned FROM forum.thread WHERE id = ?",
                Boolean.class,
                threadId
        );
        assertFalse(isPinned);
    }

    @Test
    @Order(8)
    void testDigestThread() {
        @SuppressWarnings("unchecked")
        StreamObserver<Empty> responseObserver = mock(StreamObserver.class);

        // Get first thread ID
        int threadId = jdbcTemplate.queryForObject(
                "SELECT id FROM forum.thread WHERE title = 'First Thread'",
                Integer.class
        );

        // Test making thread a digest
        ThreadGrpcApi.DigestThreadReq request = ThreadGrpcApi.DigestThreadReq.newBuilder()
                .setThreadId(threadId)
                .build();

        threadController.digestThread(request, responseObserver);

        // Verify the response
        Mockito.verify(responseObserver).onNext(Empty.getDefaultInstance());
        Mockito.verify(responseObserver).onCompleted();

        // Verify thread was marked as digest in database
        boolean isDigest = jdbcTemplate.queryForObject(
                "SELECT digest FROM forum.thread WHERE id = ?",
                Boolean.class,
                threadId
        );
        assertTrue(isDigest);

        // Test unmarking the thread as digest
        threadController.digestThread(request, responseObserver);
        // Verify the response
        Mockito.verify(responseObserver).onNext(Empty.getDefaultInstance());
        Mockito.verify(responseObserver).onCompleted();

        isDigest = jdbcTemplate.queryForObject(
                "SELECT digest FROM forum.thread WHERE id = ?",
                Boolean.class,
                threadId
        );
        assertFalse(isDigest);
    }


    @Test
    @Order(9)
    void testUpdateThread() {
        @SuppressWarnings("unchecked")
        StreamObserver<Empty> responseObserver = mock(StreamObserver.class);

        // Get first thread ID
        int threadId = jdbcTemplate.queryForObject(
                "SELECT id FROM forum.thread WHERE title = 'First Thread'",
                Integer.class
        );

        // Test updating the thread
        String newTitle = "Updated Thread Title";
        String newBody = "Updated thread content";
        ThreadGrpcApi.UpdateThreadReq request = ThreadGrpcApi.UpdateThreadReq.newBuilder()
                .setId(threadId)
                .setTitle(newTitle)
                .setBody(newBody)
                .build();

        threadController.updateThread(request, responseObserver);

        // Verify the response
        Mockito.verify(responseObserver).onNext(Empty.getDefaultInstance());
        Mockito.verify(responseObserver).onCompleted();

        // Verify thread was updated in database
        var result = jdbcTemplate.queryForMap(
                "SELECT title, body FROM forum.thread WHERE id = ?",
                threadId
        );
        String updatedTitle = (String) result.get("title");
        String updatedBody = (String) result.get("body");
        assertEquals(newTitle, updatedTitle);
        assertEquals("{\"content\":\"" + newBody + "\"}", updatedBody);

        // Test updating the thread with empty title
        request = ThreadGrpcApi.UpdateThreadReq.newBuilder()
                .setId(threadId)
                .setTitle("")
                .setBody("Empty title")
                .build();

        threadController.updateThread(request, responseObserver);

        // Verify the response
        Mockito.verify(responseObserver).onError(Status.INVALID_ARGUMENT
                .withDescription("error.content_empty").asException());

        // Verify thread was not updated
        result = jdbcTemplate.queryForMap(
                "SELECT title, body FROM forum.thread WHERE id = ?",
                threadId
        );
        updatedTitle = (String) result.get("title");
        updatedBody = (String) result.get("body");
        assertEquals(newTitle, updatedTitle);
        assertEquals("{\"content\": \"" + newBody + "\"}", updatedBody);

        // Test updating the thread with empty body
        request = ThreadGrpcApi.UpdateThreadReq.newBuilder()
                .setId(threadId)
                .setTitle("Empty body")
                .setBody("")
                .build();

        threadController.updateThread(request, responseObserver);

        // Verify the response
        Mockito.verify(responseObserver).onError(Status.INVALID_ARGUMENT
                .withDescription("error.content_empty").asException());

        // Verify thread was not updated
        result = jdbcTemplate.queryForMap(
                "SELECT title, body FROM forum.thread WHERE id = ?",
                threadId
        );
        updatedTitle = (String) result.get("title");
        updatedBody = (String) result.get("body");
        assertEquals(newTitle, updatedTitle);
        assertEquals("{\"content\": \"" + newBody + "\"}", updatedBody);
    }
} 
