package com.loodeer.mail.springbootmail.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author loodeer
 * @date 2019-01-18 22:51
 */
@Service
public class MailService {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender javaMailSender;

    @Resource
    private TemplateEngine templateEngine;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);

        javaMailSender.send(simpleMailMessage);
    }

    public void sendHtmlMail(String to, String subject, String content) {
        logger.info("sendHtmlMail 开始, to {}, subject {}, content {}", to, subject, content);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error("sendHtmlMail 异常, mgs: {}", e.getMessage());
        }
    }

    public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
        logger.info("sendAttachmentsMail 开始, to {}, subject {}, content {}, filePath {}", to, subject, content, filePath);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content);
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = file.getFilename();
            mimeMessageHelper.addAttachment(fileName, file);
            // 可以发送多个附件
            mimeMessageHelper.addAttachment(fileName + "1", file);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error("sendAttachmentsMail 异常, mgs: {}", e.getMessage());
        }
    }

    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId) {
        logger.info("sendInlineResourceMail 开始, to {}, subject {}, content {}, rscPath {}, rscId {}", to, subject, content, rscPath, rscId);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            // text  html 参数 true
            mimeMessageHelper.setText(content, true);

            FileSystemResource fileSystemResource = new FileSystemResource(new File(rscPath));
            mimeMessageHelper.addInline(rscId, fileSystemResource);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error("sendInlineResourceMail 异常, mgs: {}", e.getMessage());
        }

    }

    public void sendTemplateMail(String to, String id) {
        Context context = new Context();
        context.setVariable("id", id);

        String emailContent = templateEngine.process("registerTemplate", context);
        sendHtmlMail(to, "欢迎注册", emailContent);
    }
}
