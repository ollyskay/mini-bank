package org.example.bankprojectibk.Service;

import java.util.Map;

public interface MailService {
    void sendText(String to, String subject, String text);
    void sendHtml(String to, String subject, String html);
    void sendTemplate(String to, String subject, String templateName, Map<String, Object> model);
    void sendHtmlWithAttachment(String to, String subject, String html, String filename, byte[] data, String contentType);
}

