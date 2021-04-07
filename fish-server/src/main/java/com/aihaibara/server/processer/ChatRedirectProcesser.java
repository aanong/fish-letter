package com.aihaibara.server.processer;

import com.aihaibara.commons.ProtoMsg;
import com.aihaibara.server.server.ServerSession;

public class ChatRedirectProcesser extends AbstractServerProcesser {
    @Override
    public ProtoMsg.HeadType type() {
        return null;
    }

    @Override
    public boolean active(ServerSession ch, ProtoMsg.Message proto) {
        return false;
    }
}
