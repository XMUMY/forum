package io.xdea.xmux.forum.service;

import io.xdea.xmux.forum.mapper.LikedPostMapper;
import io.xdea.xmux.forum.mapper.PostMapper;
import io.xdea.xmux.forum.mapper.PostExtMapper;
import io.xdea.xmux.forum.model.*;
import io.xdea.xmux.forum.model.Thread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class PostService {
    private final PostMapper postMapper;
    private final PostExtMapper postExtMapper;
    private final LikedPostMapper likedPostMapper;
    private final ThreadService threadService;
    private final String[] orderStr;

    @Autowired
    public PostService(PostMapper postMapper, PostExtMapper postExtMapper,
            LikedPostMapper likedPostMapper, ThreadService threadService,
            @Qualifier("orderingStrList") String[] orderStr) {
        this.postMapper = postMapper;
        this.postExtMapper = postExtMapper;
        this.likedPostMapper = likedPostMapper;
        this.threadService = threadService;
        this.orderStr = orderStr;
    }

    @Transactional
    public boolean create(Post post) {
        final Integer threadId = post.getThreadId();
        final Thread belongThread = threadService.getById(threadId);
        post.setForumId(belongThread.getForumId());
        threadService.renewUpdateTime(threadId);
        threadService.changePostsNo(threadId, 1);
        return postMapper.insertSelective(post) == 1;
    }

    public Post getById(int postId) {
        return postMapper.selectByPrimaryKey(postId);
    }

    /**
     * Soft remove post and all its children by setting their status to deleted
     *
     * @param postId post id
     * @param postThreadId thread id
     * @return true if the post and its children were successfully marked as deleted
     */
    @Transactional
    public boolean softRemove(int postId, int postThreadId) {
        // Update status to deleted (1) for the post and all its children
        final PostExample postExample = new PostExample();
        postExample.createCriteria().andParentIdEqualTo(postId);
        
        Post updatePost = new Post();
        updatePost.setStatus(1); // Set status to deleted
        
        // Update children posts
        int updateNo = postMapper.updateByExampleSelective(updatePost, postExample);
        
        // Update the main post
        updatePost.setId(postId);
        int selfUpdateNo = postMapper.updateByPrimaryKeySelective(updatePost);

        if (!threadService.changePostsNo(postThreadId, -(updateNo + selfUpdateNo))) {
            throw new RuntimeException("threadService.changePostsNo returned false");
        };
        
        return selfUpdateNo == 1;
    }

    public List<PostWithInfo> get(int offset, int count, Integer threadId, String uid, int orderMethod) {
        return postExtMapper.select(offset, count, threadId, uid, orderStr[orderMethod]);
    }

    public List<PostWithInfo> getLvl2(int offset, int count, Integer parentId, String uid, int orderMethod) {
        return postExtMapper.selectLvl2(offset, count, parentId, uid, orderStr[orderMethod]);
    }

    public List<PostWithInfo> getTree(int limit, Integer startPostId, String uid, int ordering) {
        return postExtMapper.selectTree(limit, startPostId, uid, orderStr[ordering]);
    }

    public List<PostWithInfo> getUserPost(int offset, int count, String uid, int ordering) {
        return postExtMapper.selectByUser(offset, count, uid, orderStr[ordering]);
    }

    public List<PostWithInfo> getSaved(int offset, int count, String uid) {
        return postExtMapper.selectSaved(count, offset, uid);
    }

    /**
     * @return true if the like is successful, false if the user has already liked
     *         the post
     */
    @Transactional
    public boolean upvote(int postId, String uid) {
        int amount = 1;
        final LikedPostExample likedPostExample = new LikedPostExample();
        likedPostExample.createCriteria().andPostIdEqualTo(postId).andUidEqualTo(uid);
        var oldLike = likedPostMapper.selectByExample(likedPostExample);

        // The user has already reacted to the post
        if (oldLike.size() >= 1) {
            var likedPost = oldLike.get(0);
            // The user disliked the post
            if (!likedPost.getLiked()) {
                amount = 2;
                likedPost.setLiked(true);
                likedPost.setCreateAt(new Date());
                int result = likedPostMapper.updateByPrimaryKeySelective(likedPost);
                if (result != 1) {
                    throw new RuntimeException("likedPostMapper.updateByPrimaryKeySelective returned " + result);
                }

            } else {
                // The user has already liked the post
                amount = 0;
                return false;
            }

            // The user has not reacted to the post
        } else {
            int result = likedPostMapper.insertSelective(new LikedPost().withPostId(postId)
                    .withUid(uid).withLiked(true).withCreateAt(new Date()));
            if (result != 1) {
                throw new RuntimeException("likedPostMapper.insertSelective returned " + result);
            }
        }
        int result = postExtMapper.changeVote(postId, amount);
        if (result != 1) {
            throw new RuntimeException("postExtMapper.changeVote returned " + result);
        }
        return true;
    }

    /**
     * @return true if the downvote is successful, false if the user has already
     *         disliked the post
     */
    @Transactional
    public boolean downvote(int postId, String uid) {
        int amount = -1;
        final LikedPostExample likedPostExample = new LikedPostExample();
        likedPostExample.createCriteria().andPostIdEqualTo(postId).andUidEqualTo(uid);
        var oldLike = likedPostMapper.selectByExample(likedPostExample);

        // The user has already reacted to the post
        if (oldLike.size() >= 1) {
            var likedPost = oldLike.get(0);
            // The user liked the post
            if (likedPost.getLiked()) {
                amount = -2;
                likedPost.setLiked(false);
                likedPost.setCreateAt(new Date());
                int result = likedPostMapper.updateByPrimaryKeySelective(likedPost);
                if (result != 1) {
                    throw new RuntimeException("likedPostMapper.updateByPrimaryKeySelective returned " + result);
                }
            } else {
                // The user has already disliked the post
                amount = 0;
                return false;
            }

            // The user has not reacted to the post
        } else {
            int result = likedPostMapper.insertSelective(new LikedPost().withPostId(postId)
                    .withUid(uid).withLiked(false).withCreateAt(new Date()));
            if (result != 1) {
                throw new RuntimeException("likedPostMapper.insertSelective returned " + result);
            }
        }
        int result = postExtMapper.changeVote(postId, amount);
        if (result != 1) {
            throw new RuntimeException("postExtMapper.changeVote returned " + result);
        }
        return true;
    }

    /**
     * @return true if the cancel is successful, false if the user has not
     *         reacted to the post
     */
    @Transactional
    public boolean cancelVote(int postId, String uid) {
        final LikedPostExample likedPostExample = new LikedPostExample();
        likedPostExample.createCriteria().andPostIdEqualTo(postId).andUidEqualTo(uid);
        var oldLike = likedPostMapper.selectByExample(likedPostExample);

        if (oldLike.size() >= 1) {
            var likedPost = oldLike.get(0);
            int result = likedPostMapper.deleteByPrimaryKey(likedPost.getId());
            if (result != 1) {
                throw new RuntimeException("likedPostMapper.deleteByPrimaryKey returned " + result);
            }

            if (likedPost.getLiked()) {
                result = postExtMapper.changeVote(postId, -1);
                if (result != 1) {
                    throw new RuntimeException("postExtMapper.changeVote returned " + result);
                }
            } else {
                result = postExtMapper.changeVote(postId, 1);
                if (result != 1) {
                    throw new RuntimeException("postExtMapper.changeVote returned " + result);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean togglePinned(int id) {
        return postExtMapper.togglePinned(id) == 1;
    }
}
