package com.blockchainenergy.security.notice.dao;

import com.blockchainenergy.security.notice.Notice;
import com.blockchainenergy.security.notice.NoticeDto;

import java.util.List;

/**
 * @descriptions:
 * @data: 2021/10/30 19:30
 */
public interface NoticeDao {
    List<Notice> findNoticeListByCondition(NoticeDto dto);
}
