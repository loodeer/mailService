package com.loodeer.mail.springbootmail.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.mail.MessagingException;

/**
 * @author loodeer
 * @date 2019-01-18 23:13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {

    @Resource
    MailService mailService;

    @Test
    public void sendSimpleMailTest() {
        mailService.sendSimpleMail("mymail@163.com", "新年快乐", "马上到春节了，提前祝你新年快乐！");
    }

    @Test
    public void sendHtmlMailTest() {
        String content = "<html>\n" +
                "<body>\n" +
                "<h1>新年快乐</h1>\n" +
                "</body>" +
                "</html>";
        mailService.sendHtmlMail("mymail@163.com", "新年快乐html版本", content);
    }

    @Test
    public void sendAttachmentsMailTest() {
        mailService.sendAttachmentsMail("mymail@163.com", "新年快乐附件版", "送你一本书，详细见附件", "path/to/MailServiceTest.java");
    }

    @Test
    public void sendInlineResourceMailTest() {
        String imgPath = "~/Downloads/QQ图片20180628133519.png";
        String rscId = "xxx001";
        String content = "<html>" +
                "<body>" +
                "这是有图片的邮件: <img src=\'cid:" + rscId + "\'></img>" +
                "</body>" +
                "</html>";
        mailService.sendInlineResourceMail("mymail@163.com", "新年快乐图片版", content, imgPath, rscId);
    }

    @Test
    public void sendTemplateMailTest() {
        mailService.sendTemplateMail("mymail@163.com", "1");
    }
}
