package com.aihaibara.server.processer;

import com.aihaibara.commons.ProtoMsg;
import com.aihaibara.server.server.ServerSession;
import io.netty.channel.Channel;


public abstract class AbstractServerProcesser  implements ServerProcesser{
    /**
     * 获取key
     */
    protected String getKey(Channel ch){
        return ch.attr(ServerSession.KEY_USER_ID).get();
    }


    /**
     * 设置key
     */
    protected void setKey(Channel ch ,String key){

        ch.attr(ServerSession.KEY_USER_ID).set(key);
    }

    /**
     * 验证
     */
    protected void checkAuth(Channel ch) throws Exception{

        if(getKey(ch)==null){
            throw new Exception("此用户，没有登录成功");
        }
    }
}
