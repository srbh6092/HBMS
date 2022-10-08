package com.srbh.hbms.config;

import com.srbh.hbms.jwt.JwtAuthEntryPoint;
import com.srbh.hbms.jwt.JwtAuthTokenFilter;
import com.srbh.hbms.service.jwtUserDetail.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //Accessing userDetails or Authorization
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    //For exception handling while authorization
    @Autowired
    JwtAuthEntryPoint unauthorizedHandler;

    //Creating instance of password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //End points which can be accessed by anyone
    public static final String[] WHITELIST_ANY_ONE = {
            "/api/v1/auth/**",
            "/swagger-ui/**",
    };

    //End points which can be accessed only by ADMIN
    public static final String[] WHITELIST_ONLY_ADMIN = {
            "/api/v1/transaction/**",
            "/api/v1/user/**",
            "/api/v1/payment/**",
            "/api/v1/hotel/**",
            "/api/v1/room/**"
    };

    //End points which can be accessed only by CUSTOMER
    public static final String[] WHITELIST_ONLY_CUSTOMER ={
    };

    //End points which can be accessed by both ADMIN and CUSTOMER
    public static final String[] WHITELIST_ADMIN_AND_CUSTOMER ={
            "/api/v1/booking/**"
    };

    //For filtering valid tokens
    @Bean
    public JwtAuthTokenFilter jwtAuthTokenFilter(){
        return new JwtAuthTokenFilter();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //Configuring the security with the service which will provide the JWT User
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    //Configuring the security with the other service which will provide the interface, etc.
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**");

    }

    //Configuring the security with all authentication based things
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //Configuring the security with all the roles based endpoints and exception handler
        http.cors().and()
                .csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(WHITELIST_ANY_ONE).permitAll()
                .antMatchers(WHITELIST_ONLY_ADMIN).hasAuthority("ADMIN")
                .antMatchers(WHITELIST_ONLY_CUSTOMER).hasAuthority("CUSTOMER")
                .antMatchers(WHITELIST_ADMIN_AND_CUSTOMER).hasAnyAuthority("ADMIN","CUSTOMER")
                .anyRequest()
                .authenticated();

        //Configuring the security with token filter before granting the access
        http.addFilterBefore(jwtAuthTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}