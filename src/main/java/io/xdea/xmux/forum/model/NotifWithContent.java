package io.xdea.xmux.forum.model;

public class NotifWithContent  extends Notif{
    private String refContent;
    private String objContent;

    public String getRefContent() {
        return refContent;
    }

    public void setRefContent(String refContent) {
        this.refContent = refContent;
    }

    public String getObjContent() {
        return objContent;
    }

    public void setObjContent(String objContent) {
        this.objContent = objContent;
    }
}
