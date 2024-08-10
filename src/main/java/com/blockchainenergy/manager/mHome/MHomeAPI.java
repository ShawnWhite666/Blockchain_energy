package com.blockchainenergy.manager.mHome;

import com.blockchainenergy.common.CommonService;
import com.blockchainenergy.common.Constants;
import com.blockchainenergy.common.CurrentUser;
import com.blockchainenergy.common.Result;
import com.blockchainenergy.common.utils.MTokenUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @descriptions:
 * @data: 2021/10/23 14:00
 */
@RestController
@RequestMapping("/manager/mHome")
public class MHomeAPI {
    @Resource
    private CommonService commonService;

    @Resource
    private MHomeService mHomeService;

    @GetMapping("/menus")
    public Result menus(@RequestHeader(Constants.HEADER_TOKEN) String token) {
        if (token.equals("")) {
            return Result.fail(Result.TOKEN_LOSE, "请先登录！");
        }
        CurrentUser currentUser = MTokenUtils.getUserInfo(token, commonService);
        List<MMenuVO> menuVOList = mHomeService.getMenus(currentUser);
        return Result.success(menuVOList);
    }

    @DeleteMapping("/exit")
    public Result exit(@RequestHeader("Token") String token) {
        if (token.equals("")) {
            return Result.fail(Result.TOKEN_LOSE, "请先登录！");
        }
        //在服务端清除缓存的token
        MTokenUtils.removeToken(token);
        return Result.success();
    }
}
