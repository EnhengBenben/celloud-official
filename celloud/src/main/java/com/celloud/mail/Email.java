package com.celloud.mail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Email {
    private List<String> to;
    private List<String> cc;
    private List<String> bcc;
    private String subject;
    private String content;
    private Map<String, String> attachments;
    private String template;
    private Map<String, Object> context;

    public Email addTo(String... emails) {
        if (to == null) {
            to = new ArrayList<>();
        }
        to.addAll(Arrays.asList(emails));
        return this;
    }

    public Email addCC(String... emails) {
        if (cc == null) {
            cc = new ArrayList<>();
        }
        cc.addAll(Arrays.asList(emails));
        return this;
    }

    public Email addBCC(String... emails) {
        if (bcc == null) {
            bcc = new ArrayList<>();
        }
        cc.addAll(Arrays.asList(emails));
        return this;
    }

    public Email setTemplate(String template) {
        this.template = template;
        return this;
    }

    public Email setSubject(String subject) {
        this.subject = subject;
        this.content = null;
        return this;
    }

    public Email setContent(String content) {
        this.content = content;
        this.template = null;
        return this;
    }

    public Email attach(String filepath, String filename) {
        if (attachments == null) {
            attachments = new HashMap<>();
        }
        attachments.put(filepath, filename);
        return this;
    }

    public Email addModel(String key, Object model) {
        if (this.context == null) {
            this.context = new HashMap<>();
        }
        this.context.put(key, model);
        return this;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public List<String> getCc() {
        return cc;
    }

    public void setCc(List<String> cc) {
        this.cc = cc;
    }

    public List<String> getBcc() {
        return bcc;
    }

    public void setBcc(List<String> bcc) {
        this.bcc = bcc;
    }

    public Map<String, String> getAttachments() {
        return attachments;
    }

    public void setAttachments(Map<String, String> attachments) {
        this.attachments = attachments;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public String getTemplate() {
        return template;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }

}
