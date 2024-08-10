package com.blockchainenergy.common.base;

import com.blockchainenergy.common.CommonService;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter("/*")
public class SecurityFilter implements Filter {
    //放行通过带有/xx前路径的请求
    private static final String[] freePrefixPaths = {"/css", "/fonts", "/images", "/js", "/layer", "/security/login", "/security/register", "/security/home", "/security/test", "/manager", "/blockChain", "/nodes", "/trade/dealResult", "/trade/deal-match"};
    //放行通过是/xx/uu的路径的请求
    private static final String[] freePaths = {"/trade/tradeInfo/getTimeSlot"};

    @Resource
    private CommonService commonService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    private static boolean isFreePath(String thePath) {
        for (String path : freePaths) {
            if (path.equals(thePath)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasPrefix(String thePath) {
        for (int i = 0; i < freePrefixPaths.length; i++) {
            if (thePath.startsWith(freePrefixPaths[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        filterChain.doFilter(request, response);
        
//        String path = request.getServletPath();
//        if (isFreePath(path) || hasPrefix(path)) {
//        filterChain.doFilter(request, response);
        return;
//        }
//
//        String clientToken = request.getHeader(Constants.HEADER_TOKEN);
//
//        try {
//            TokenUtils.verifyToken(clientToken,commonService);
//            filterChain.doFilter(servletRequest,servletResponse);
//        } catch (Exception e) {
//            e.printStackTrace();
//            Utils.outJson(response, Result.fail(Result.ERR_CODE_UNLOGIN,e.getMessage()));
//
//        }
    }

    @Override
    public void destroy() {

    }
}
