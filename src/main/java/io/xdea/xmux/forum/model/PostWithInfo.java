package io.xdea.xmux.forum.model;

public class PostWithInfo extends Post {
    private Integer liked;
    private Boolean saved;

    public Integer getLiked() {
        return liked;
    }

    public void setLiked(Integer liked) {
        this.liked = liked;
    }

    public Boolean getSaved() {
        return saved;
    }

    public void setSaved(Boolean saved) {
        this.saved = saved;
    }
}
