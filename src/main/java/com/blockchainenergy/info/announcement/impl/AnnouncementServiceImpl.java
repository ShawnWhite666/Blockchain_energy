package com.blockchainenergy.info.announcement.impl;

import com.blockchainenergy.info.announcement.Announcement;
import com.blockchainenergy.info.announcement.AnnouncementService;
import com.blockchainenergy.info.announcement.dao.AnnouncementDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @descriptions:
 */
@Service
@Transactional
public class AnnouncementServiceImpl implements AnnouncementService {
    @Resource
    private AnnouncementDao announcementDao;

    @Override
    public List<Announcement> getAnnouncement() {
        return announcementDao.getAnnouncement();
    }
}
