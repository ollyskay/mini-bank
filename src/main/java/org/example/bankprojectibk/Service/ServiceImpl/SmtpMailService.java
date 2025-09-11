package org.example.bankprojectibk.Service.ServiceImpl;

import jakarta.annotation.Nullable;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value; // keep
import org.example.bankprojectibk.Service.MailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;



@Service
@RequiredArgsConstructor
@Slf4j
public class SmtpMailService implements MailService {

    private final JavaMailSender mailSender;

    // make template engine optional (remove 'final')
    @Nullable
    private TemplateEngine templateEngine;

    @Value("${app.mail.from:no-reply@minibank.local}")
    private String from;

    @Value("${app.mail.enabled:true}")
    private boolean enabled;

    @Override
    public void sendText(String to, String subject, String text) {
        if (!enabled) { log.debug("MAIL OFF → to={} subject={}", to, subject); return; }
        var msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(text);
        mailSender.send(msg);
    }

    @Override
    public void sendHtml(String to, String subject, String html) {
        if (!enabled) { log.debug("MAIL OFF → to={} subject={}", to, subject); return; }
        try {
            MimeMessage mime = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mime, true, "UTF-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);
            mailSender.send(mime);
        } catch (Exception e) {
            log.error("Failed to send email", e);
            throw new IllegalStateException("Failed to send email", e);
        }
    }

    @Override
    public void sendTemplate(String to, String subject, String template, java.util.Map<String,Object> model) {
        if (templateEngine == null) { sendText(to, subject, model.toString()); return; }
        Context ctx = new Context();
        ctx.setVariables(model);
        sendHtml(to, subject, templateEngine.process(template, ctx));
    }

    @Override
    public void sendHtmlWithAttachment(String to, String subject, String html, String filename, byte[] data, String contentType) {
        if (!enabled) { log.debug("MAIL OFF → to={} subject={} attachment={}", to, subject, filename); return; }
        try {
            MimeMessage mime = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mime, true, "UTF-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);
            helper.addAttachment(filename, new org.springframework.core.io.ByteArrayResource(data), contentType);
            mailSender.send(mime);
        } catch (Exception e) {
            log.error("Failed to send email with attachment", e);
            throw new IllegalStateException("Failed to send email with attachment", e);
        }
    }
}