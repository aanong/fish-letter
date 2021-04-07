package com.aihaibara.server.processer;

import com.aihaibara.commons.ProtoMsg;
import com.aihaibara.server.server.ServerSession;

public interface ServerProcesser {
    ProtoMsg.HeadType type();

    boolean active(ServerSession ch, ProtoMsg.Message proto);
}
