package com.blockchainenergy.security.notice.impl;

import com.blockchainenergy.common.utils.Utils;
import com.blockchainenergy.security.notice.NoticeDto;
import com.blockchainenergy.security.notice.NoticeService;
import com.blockchainenergy.security.notice.dao.NoticeDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @descriptions:
 * @data: 2021/10/30 19:14
 */

@Service //说明这是一个受spring容器管理的业务组件(Bean)
@Transactional //说明本类的所有方法都是事务性
public class NoticeServiceImpl implements NoticeService {

    @Resource
    private NoticeDao noticeDao;

    @Override
    public Map<String, Object> getNoticePage(NoticeDto dto) {
        Map<String, Object> page = Utils.getPage(dto, () -> noticeDao.findNoticeListByCondition(dto));
        return page;
    }
}
