package com.ssm.crud.bean;

import javax.servlet.annotation.WebServlet;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName Message
 * @Description 通用的返回的类
 * @Author wk
 * @Date 2022/4/29 22:27
 * @Version 1.0
 */
public class Message {
    private int code; // 状态码
    private String message; // 提示信息
    private Map<String,Object> data = new HashMap<>(); // 用户要返回给浏览器的数据

    public static Message success(){
        Message message = new Message();
        message.setCode(100);
        message.setMessage("处理成功");
        return message;
    }

    public static Message fail(){
        Message message = new Message();
        message.setCode(200);
        message.setMessage("处理失败");
        return message;
    }

    public Message add(String key,Object value){
        this.getData().put(key,value);
        return this;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
