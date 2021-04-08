package com.aihaibara.server.processer;

import com.aihaibara.commons.ProtoInstant;
import com.aihaibara.commons.ProtoMsg;
import com.aihaibara.commons.bean.User;
import com.aihaibara.server.protoBuilder.LoginResponceBuilder;
import com.aihaibara.server.server.ServerSession;
import com.aihaibara.server.server.SessionMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("LoginProcesser")
public class LoginProcesser extends AbstractServerProcesser {
    @Autowired
    private LoginResponceBuilder loginResponceBuilder;
    @Override
    public ProtoMsg.HeadType type() {
        return ProtoMsg.HeadType.LOGIN_REQUEST;
    }

    @Override
    public boolean active(ServerSession session, ProtoMsg.Message proto) {
        ProtoMsg.LoginRequest loginRequest = proto.getLoginRequest();
        long sequence = proto.getSequence();
        User user = User.fromMsg(loginRequest);

        //检查用户
        boolean isValidUser = checkUser(user);
        if (!isValidUser) {
            ProtoInstant.ResultCodeEnum resultcode =
                    ProtoInstant.ResultCodeEnum.NO_TOKEN;
            //构造登录失败的报文
            ProtoMsg.Message response =
                    loginResponceBuilder.loginResponce(resultcode, sequence, "-1");
            //发送登录失败的报文
            session.writeAndFlush(response);
            return false;
        }

        session.setUser(user);

        session.bind();

        // sessionManger.addLocalSession(session);

        //登录成功
        ProtoInstant.ResultCodeEnum resultcode =
                ProtoInstant.ResultCodeEnum.SUCCESS;
        //构造登录成功的报文
        ProtoMsg.Message response =
                loginResponceBuilder.loginResponce(
                        resultcode, sequence, session.getSessionId());
        //发送登录成功的报文
        session.writeAndFlush(response);
        return true;
    }

    private boolean checkUser(User user) {
        if (SessionMap.inst().hasLogin(user)) {
            return false;
        }

        //校验用户,比较耗时的操作,需要100 ms以上的时间
        //方法1：调用远程用户restfull 校验服务
        //方法2：调用数据库接口校验

        return true;
    }
}
