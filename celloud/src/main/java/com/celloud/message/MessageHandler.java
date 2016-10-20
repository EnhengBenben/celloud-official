package com.celloud.message;

public interface MessageHandler {
    /**
     * 处理收到的kafka消息
     * 
     * @param key
     * @param value
     * @return 是否已经正确处理
     */
    public boolean handle(String key, String value) throws Exception;
}
