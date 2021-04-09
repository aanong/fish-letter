package com.aihaibara.protoBuilder;

import com.aihaibara.client.ClientSession;
import com.aihaibara.commons.ProtoMsg;
import com.aihaibara.commons.bean.ChatMsg;
import com.aihaibara.commons.bean.UserDTO;

public class ChatMsgBuilder extends BaseBuilder {

    private ChatMsg chatMsg;
    private UserDTO user;


    public ChatMsgBuilder(ChatMsg chatMsg, UserDTO user, ClientSession session) {
        super(ProtoMsg.HeadType.MESSAGE_REQUEST, session);
        this.chatMsg = chatMsg;
        this.user = user;

    }


    public ProtoMsg.Message build() {
        ProtoMsg.Message message = buildCommon(-1);
        ProtoMsg.MessageRequest.Builder cb
                = ProtoMsg.MessageRequest.newBuilder();

        chatMsg.fillMsg(cb);
        return message
                .toBuilder()
                .setMessageRequest(cb)
                .build();
    }

    public static ProtoMsg.Message buildChatMsg(
            ChatMsg chatMsg,
            UserDTO user,
            ClientSession session) {
        ChatMsgBuilder builder =
                new ChatMsgBuilder(chatMsg, user, session);
        return builder.build();

    }
}
