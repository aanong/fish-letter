package com.aihaibara.clientCommand;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Slf4j
@Data
@Service("ChatConsoleCommand")
public class ChatConsoleCommand implements BaseCommand {

    private String toUserId;
    private String message;
    public static final String KEY = "2";

    @Override
    public void exec(Scanner scanner) {
        System.out.println("请输入聊天信息，格式为：内容@用户名 ");
        String s = scanner.next();
        String[] array = s.split("@");

        message = array[0];
        toUserId = array[1];

        log.info("发送的目标用户:{},发送内容:{}",toUserId,message);
   }


    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public String getTip() {
        return "聊天";
    }

}
