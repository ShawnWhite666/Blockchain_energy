package com.blockchainenergy.info.announcement.dao;

import com.blockchainenergy.info.announcement.Announcement;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @descriptions:
 */
public interface AnnouncementDao {
    @Select("select * from announcement")
    List<Announcement> getAnnouncement();
}
