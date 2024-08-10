package com.blockchainenergy.security.login;

import lombok.Data;

/**
 * @descriptions:
 * @data: 2021/10/23 12:33
 */
@Data
public class LoginDto {
    private String public_key;//user_id
    private String private_key;
}
