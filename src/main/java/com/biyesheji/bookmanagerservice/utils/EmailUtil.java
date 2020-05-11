package com.biyesheji.bookmanagerservice.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
* Description: 邮箱工具类<br/>
* @author: wuwenguang<br/>
* @date: 2020/3/16 21:12<br/>
* @param:<br/>
* @return:
*/
@Log4j2
@Component
public class EmailUtil {
    @Value("${spring.mail.username}")
    //使用@Value注入application.properties中指定的用户名
    private String username;
    @Autowired
    //用于发送文件
    private JavaMailSender mailSender;

    /**
     *
     * @param theRecipient 收件人
     * @param subject 主题
     * @param content 内容
     */
    public void sendSimpleMail(String theRecipient, String subject, String content) {
        log.info("发送邮件");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(theRecipient);//收信人
        message.setSubject(subject);//主题
        message.setText(content);//内容
        message.setFrom(username);//发信人
        mailSender.send(message);
        log.info("邮件发送成功");
    }

    /**
     * 【发送HTML文件】HTML文件就是指在文件内容中可以添加<html>等标签，收件人收到邮件后显示内容也和网页一样，比较丰富多彩
     * @param theRecipient 收件人
     * @param subject 主题
     * @param content 内容（可以包含<html>等标签）
     */
    public void sendHtmlMail(String theRecipient, String subject, String content) {

        //使用MimeMessage，MIME协议
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper;
        //MimeMessageHelper帮助我们设置更丰富的内容
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(username);
            helper.setTo(theRecipient);
            helper.setSubject(subject);
            helper.setText(content, true);//true代表支持html
            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("发送HTML邮件失败：", e);
        }
    }

    /**
     *发送带附件的邮件】带附件的邮件在HTML邮件上添加一些参数即可
     * @param theRecipient 收件人
     * @param subject 主题
     * @param content 内容
     * @param filePath 附件路径
     */
    public void sendAttachmentMail(String theRecipient, String subject, String content, String filePath) {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            //true代表支持多组件，如附件，图片等
            helper.setFrom(username);
            helper.setTo(theRecipient);
            helper.setSubject(subject);
            helper.setText(content, true);
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = file.getFilename();
            helper.addAttachment(fileName, file);//添加附件，可多次调用该方法添加多个附件
            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("发送HTML邮件失败：", e);
        }


    }

    /**
     * 【发送带图片的邮件】带图片即在正文中使用<img>标签，并设置我们需要发送的图片，也是在HTML基础上添加一些参数
     * @param theRecipient 收件人
     * @param subject 主题
     * @param content 文本
     * @param rscPath 图片路径
     * @param rscId 图片ID，用于在<img>标签中使用，从而显示图片
     */
    public void sendInlineResourceMail(String theRecipient, String subject, String content, String rscPath, String rscId) {
        //logger.info("发送带图片邮件开始：{},{},{},{},{}", to, subject, content, rscPath, rscId);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(username);
            helper.setTo(theRecipient);
            helper.setSubject(subject);
            helper.setText(content, true);
            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);//重复使用添加多个图片
            mailSender.send(message);
            log.info("发送带图片邮件成功");
        } catch (MessagingException e) {
            log.error("发送带图片邮件失败", e);
        }
    }
}
