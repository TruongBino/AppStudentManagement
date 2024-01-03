package com.example.appstudentmanagement.Variable;

public class Assignment {
    private String subject;
    private String content;
    private String mediaUrl;

    public Assignment() {
        // Required empty public constructor
    }

    public Assignment(String subject, String content, String mediaUrl) {
        this.subject = subject;
        this.content = content;
        this.mediaUrl = mediaUrl;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }
}
