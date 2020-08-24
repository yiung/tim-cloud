package com.yiung.tools.entity.auth;

import lombok.Data;

@Data
public class TokenEntity {
    private String accessToken;
    private String refreshToken;
    private long expireTime;

}
