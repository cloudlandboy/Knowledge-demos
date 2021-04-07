package cn.clboy.demo.shiro.p11;


import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class MyRealm extends AuthorizingRealm {

    private final String USERNAME = "sun";
    private final String PASSWORD = "93bd8d30f7f01cb8e275da2912b8b1b4";
    private final String SALT = "5e58b79de3aa6c3b86302f0adf32c11b";


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String loginUser = token.getPrincipal().toString();
        if (!USERNAME.equals(loginUser)) {
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(loginUser, PASSWORD, getName());
        ByteSource credentialsSalt = ByteSource.Util.bytes(SALT);
        authenticationInfo.setCredentialsSalt(credentialsSalt);
        return authenticationInfo;
    }
}