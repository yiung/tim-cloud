package com.yiung.tools.token;

import com.yiung.tools.entity.auth.TokenEntity;
import com.yiung.config.configcenter.common.ExporeTime;

import java.util.UUID;

public class TokenUtil {


    /**
     * 生成令牌
     *
     * @return
     */
    public static TokenEntity getNewToken() {
        TokenEntity token = new TokenEntity();
        token.setAccessToken(UUID.randomUUID().toString());
        token.setRefreshToken(UUID.randomUUID().toString());
        token.setExpireTime(ExporeTime.TOKEN_WECHAT_MINI_EXPIRE_TIME);
        return token;
    }
}
