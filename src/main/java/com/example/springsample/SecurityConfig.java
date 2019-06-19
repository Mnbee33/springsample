package com.example.springsample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private DataSource dataSource;

    private static final String USER_SQL = "SELECT" +
            " user_id," +
            " password," +
            " true" +   // 使用可否：今回は全てのユーザが使用可能
            " FROM" +
            " m_user" +
            " WHERE" +
            " user_id = ?";

    private static final String ROLE_SQL = "SELECT" +
            " user_id," +
            " role" +
            " FROM" +
            " m_user" +
            " WHERE" +
            " user_id = ?";

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**", "/css/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ignoreLogin(http);
        setLogin(http);
        setLogout(http);
        ignoreCsrf(http);
        // http.csrf().disable();   // disabled：クロスサイトリクエストフォージェリ対策を無効にする
    }

    private void ignoreLogin(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/rest/**").permitAll()
                .antMatchers("/admin").hasAnyAuthority("ROLE_ADMIN")
                .anyRequest().authenticated();
    }

    private void setLogin(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginProcessingUrl("/login")
                .loginPage("/login")
                .failureUrl("/login")
                .usernameParameter("userId")
                .passwordParameter("password")
                .defaultSuccessUrl("/home", true);
    }

    private void setLogout(HttpSecurity http) throws Exception {
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login");
    }

    private void ignoreCsrf(HttpSecurity http) throws Exception {
        RequestMatcher csrfMatcher = new RestMatcher("/rest/**");
        http.csrf().requireCsrfProtectionMatcher(csrfMatcher);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(USER_SQL)
                .authoritiesByUsernameQuery(ROLE_SQL)
                .passwordEncoder(passwordEncoder());
    }
}
