package io.xdea.xmux.forum.model;

public class PostWithGroupName extends Post {
    private String groupName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "PostWithGroupName{" +
                "groupName='" + groupName + '\'' +
                "} " + super.toString();
    }
}
