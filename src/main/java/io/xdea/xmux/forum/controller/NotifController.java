package io.xdea.xmux.forum.controller;

import io.xdea.xmux.forum.service.*;

public abstract class NotifController extends GroupController {
    final protected NotifService notifService;

    protected NotifController(GroupService groupService, NotifService notifService) {
        super(groupService);
        this.notifService = notifService;
    }
}
