package com.blockchainenergy.security.notice;

import java.util.Map;

/**
 * @descriptions:
 * @data: 2021/10/30 19:13
 */
public interface NoticeService {
    Map<String, Object> getNoticePage(NoticeDto dto);
}
