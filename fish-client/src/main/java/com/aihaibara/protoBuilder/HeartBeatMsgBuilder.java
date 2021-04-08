package com.aihaibara.protoBuilder;

import com.aihaibara.client.ClientSession;
import com.aihaibara.commons.ProtoMsg;
import com.aihaibara.commons.bean.User;

public class HeartBeatMsgBuilder extends BaseBuilder {
    private final User user;

    public HeartBeatMsgBuilder(User user, ClientSession session) {
        super(ProtoMsg.HeadType.HEATBEAT_REQUEST, session);
        this.user = user;
    }

    public ProtoMsg.Message buildMsg() {
//        ProtoMsg.Message message = buildCommon(-1);
//        ProtoMsg.Message.Builder lb =
//                ProtoMsg.MessageHeartBeat.newBuilder()
//                        .setSeq(0)
//                        .setJson("{\"from\":\"client\"}")
//                        .setUid(user.getUid());
//        return message.toBuilder().setHeartBeat(lb).build();
        return null;
    }
}
