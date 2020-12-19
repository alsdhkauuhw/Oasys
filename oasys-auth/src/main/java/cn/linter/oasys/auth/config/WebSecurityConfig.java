package cn.linter.oasys.auth.config;

import cn.linter.oasys.auth.dao.PrincipalDao;
import cn.linter.oasys.auth.entity.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;

/**
 * Security配置
 *
 * @author wangxiaoyang
 * @since 2020/11/03
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalDao principalDao;

    /**
     * 密钥文件路径
     */
    @Value("${key.location}")
    private String keyLocation;
    /**
     * 密钥文件密码
     */
    @Value("${key.password}")
    private String keyPassword;
    /**
     * 密钥文件别名
     */
    @Value("${key.alias}")
    private String keyAlias;

    @Bean
    public KeyPair keyPair() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                new ClassPathResource(keyLocation), keyPassword.toCharArray()
        );
        return keyStoreKeyFactory.getKeyPair(keyAlias, keyPassword.toCharArray());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService setUserDetailsService() {
        return username -> {
            Principal principal = principalDao.selectPrincipalByUsername(username);
            if (principal == null) {
                throw new UsernameNotFoundException("用户不存在！");
            }
            return principal;
        };
    }

    @Bean
    public AuthenticationManager setAuthenticationManagerBean() throws Exception {
        return authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/key/public").permitAll()
                .mvcMatchers("/user").permitAll()
                .anyRequest().authenticated()
                .and().userDetailsService(setUserDetailsService())
                .csrf().disable();
    }

}