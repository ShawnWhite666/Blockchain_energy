package com.blockchainenergy.security.notice;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @descriptions:
 * @data: 2021/10/30 19:33
 */
public class Notice {
    private String title;
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date created_at;

    public Notice(String title, String content, Date created_at) {
        this.title = title;
        this.content = content;
        this.created_at = created_at;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
