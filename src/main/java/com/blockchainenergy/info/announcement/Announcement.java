package com.blockchainenergy.info.announcement;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @descriptions: 记录公告信息
 */
@Data
public class Announcement {
    private Integer id;
    private String title;
    private String content;
    @JsonFormat(pattern = "yyyy年MM月dd日",timezone="GMT+8")
    private Date create_at;
}
