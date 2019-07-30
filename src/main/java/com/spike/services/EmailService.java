package com.spike.services;
/**
 * Author: tangji
 * Date: 2019 07 26 21:22
 * Description: <...>
 */
public interface EmailService {

    /**
     * 发送邮件
     * @param to
     * @param subject
     * @param content
     */
    public void sendEmail(String to, String subject,String content);
}
