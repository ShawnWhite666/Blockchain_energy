package com.blockchainenergy.security.login;

import lombok.Data;

/**
 * 储存在前端的数据
 */
@Data
public class Login {
    private String token;
    private String name;
    private String avatar;
    private String role;
}
