package com.aihaibara.server.server;

import com.aihaibara.commons.bean.User;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
public class SessionMap {
    private SessionMap(){
    }
    private static SessionMap singleInstance = new SessionMap();

    private static ConcurrentHashMap<String,ServerSession> map = new ConcurrentHashMap<>();

    public static SessionMap inst() {
        return singleInstance;
    }

    /**
     * 添加session对象
     */
    public void addSession(String sessionId,ServerSession s){
        map.put(sessionId,s);
        log.info("用户登录:id= " + s.getUser().getUid()
                + "   在线总数: " + map.size());

    }

    /**
     * 获取session对象
     */
    public ServerSession getSession(String sessionId){
        //sessionId不存在返回空
        if(!map.containsKey(sessionId)){
            return null;
        }
        return map.get(sessionId);
    }
    /**
     * 根据用户id获取session对象
     */
    public List<ServerSession>  getSessionByUserId(String userId){
        List<ServerSession> list = map.values()
                .stream()
                .filter(s -> s.getUser().getUid().equals(userId))
                .collect(Collectors.toList());
        return list;
    }
    /**
     * 删除session
     */
    public void removeSession(String sessionId){
        if(map.containsKey(sessionId)){
            return;
        }
        ServerSession serverSession = map.get(sessionId);
        map.remove(sessionId);
        log.info("用户下线:id= " + serverSession.getUser().getUid()
                + "   在线总数: " + map.size());
    }

    /**
     * 判断用户是否登录
     */
    public boolean hasLogin(User user){

        Iterator<Map.Entry<String, ServerSession>> it =
                map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, ServerSession> next = it.next();
            User u = next.getValue().getUser();
            if (u.getUid().equals(user.getUid())
                    && u.getPlatform().equals(user.getPlatform())) {
                return true;
            }
        }

        return false;
    }


}
