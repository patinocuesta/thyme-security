package fr.laerce.thymesecurity.configuration;

import fr.laerce.thymesecurity.security.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * Projet thyme-security
 * Pour LAERCE SAS
 * <p>
 * Créé le  21/03/2017.
 *
 * @author fred
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private UserServiceImpl userServiceImpl;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private void setUserDetailsService (UserServiceImpl userServiceImpl){
        this.userServiceImpl = userServiceImpl;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/login").permitAll()
                .antMatchers("/user/**") .hasAuthority("ROLE_USER")
                .antMatchers("/admin/**").hasAuthority("ADMIN").anyRequest()
                .authenticated().and().formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                .usernameParameter("name")
                .passwordParameter("password")
                .and()
                .logout()
                .permitAll()
        ;

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("recup")
                .password("recup")
                .roles("ADMIN", "USER_ROLE")
                .authorities("WITHDRAW", "DEPOSIT", "ADMIN");

        auth.userDetailsService(userServiceImpl).passwordEncoder(bCryptPasswordEncoder());

    }
}
