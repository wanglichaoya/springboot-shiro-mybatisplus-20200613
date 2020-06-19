package com.example.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2020/6/12
 * Time: 下午 04:57
 * Description:
 */
public class JwtToken implements AuthenticationToken {
    private String token;

    public JwtToken(String jwt) {
        this.token = jwt;
    }

    /**
     * @return the account identity submitted during the authentication process.
     * @see UsernamePasswordToken
     * @see 返回身份验证过程中提交的帐户标识。
     */
    @Override
    public Object getPrincipal() {
        return token;
    }

    /**
     *
     * @see 返回用户在验证过程中提交的凭据
     * @return the credential submitted by the user during the authentication process.
     */
    @Override
    public Object getCredentials() {
        return token;
    }
}
