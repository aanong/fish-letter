package com.aihaibara.server.server;

import com.aihaibara.commons.bean.User;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 实现服务器Socket Session会话
 */
@Data
@Slf4j
public class ServerSession {
    public static final AttributeKey<String> KEY_USER_ID =
            AttributeKey.valueOf("key_user_id");

    public static final AttributeKey<ServerSession> SESSION_KEY =
            AttributeKey.valueOf("SESSION_KEY");

    /**
     * 用户实现服务端会话管理的核心
     */
    //通道
    private Channel channel;
    //用户
    private User user;
    //session唯一标示
    private final String sessionId;
    //登录状态
    private boolean isLogin = false;




}
