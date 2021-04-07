package com.aihaibara.server.protoBuilder;


import com.aihaibara.commons.ProtoInstant;
import com.aihaibara.commons.ProtoMsg;
import org.springframework.stereotype.Service;

@Service("LoginResponceBuilder")
public class LoginResponceBuilder  {

    /**
     * 登录应答 应答消息protobuf
     * @param en
     * @param seqId
     * @param sessionId
     * @return
     */
    public ProtoMsg.Message loginResponce(ProtoInstant.ResultCodeEnum en, long seqId, String sessionId){
        ProtoMsg.Message.Builder pm = ProtoMsg.Message.newBuilder()
                .setType(ProtoMsg.HeadType.LOGIN_RESPONSE)
                .setSequence(seqId)
                .setSessionId(sessionId);
        ProtoMsg.LoginResponse.Builder b = ProtoMsg.LoginResponse.newBuilder()
                .setCode(en.getCode())
                .setInfo(en.getDesc())
                .setExpose(1);
        pm.setLoginResponse(b.build());
        return pm.build();
    }
}
