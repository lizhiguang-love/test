package com.example.demo.lzg.config;

//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
////@Configuration
////@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder())
//                .withUser("admin").password(passwordEncoder().encode("123456")).authorities("admin")
//                .and()
//                .withUser("test").password(passwordEncoder().encode("123456")).authorities("test");
//
//    }
//    private PasswordEncoder passwordEncoder(){
//
//        return new BCryptPasswordEncoder();
//    }
//}
