package com.blockchainenergy.security.notice;

import com.blockchainenergy.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @descriptions:
 * @data: 2021/10/30 19:06
 */
@RestController //本注解说明本类对象为spring容器管理的对象，并且说明这是一个控制器组件
@RequestMapping("/security/notice")//本注解说明本控制器所有路径都以。。。开头
public class NoticeAPI {
    @Resource
    private NoticeService noticeService;

    @GetMapping("")
    public Result getNotice(NoticeDto dto) {
        Map<String,Object> page =  noticeService.getNoticePage(dto);
        return Result.success(page);
    }
}
