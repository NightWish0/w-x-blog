package com.wxblog.core.config.shiro;

import com.wxblog.core.bean.User;
import com.wxblog.core.dao.UserMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: NightWish
 * @create: 2018-09-26 16:34
 * @description:
 **/
public class BlogRealm extends AuthorizingRealm {

    @Autowired
    private UserMapper userMapper;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authenticationInfo=new SimpleAuthorizationInfo();
        User user= (User) principalCollection.getPrimaryPrincipal();
        //只有管理员角色
        if (user != null){
            authenticationInfo.addRole("admin");
        }
        return authenticationInfo;
    }

    /**
     * 登录验证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token= (UsernamePasswordToken) authenticationToken;
        String loginName=token.getPrincipal().toString();
        User user=userMapper.checkUserIsExists(loginName);
        if (user != null){
            SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(
                                                        user,
                                                        user.getPassword(),
                                                        ByteSource.Util.bytes(user.getLoginName()+user.getSalt()),
                                                        getName());
            return authenticationInfo;
        }else{
            throw new UnknownAccountException();
        }
    }
}
