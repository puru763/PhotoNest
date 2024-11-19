//
//
//package com.photonest.user_service.security;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//@Configuration
//@EnableWebSecurity
//
//public class WebSecurity {
//
//    @Autowired
//    Environment environment;
//
//    //90
//    @Bean
//    protected SecurityFilterChain    configure(HttpSecurity http)  throws  Exception{
//        http.csrf().disable();
//
//        String gatewayIp = environment.getProperty("gateway.ip");
//        System.out.println("Configured Gateway IP: " + gatewayIp);
//
//        http.authorizeHttpRequests()
//
//
//
//
//                .requestMatchers(HttpMethod.GET,"/api/v1/user").permitAll()
//
////                .requestMatchers(HttpMethod.POST,"/api/v1/user")
////                .access(new WebExpressionAuthorizationManager("hasIpAddress('gatewayIp'))")
//
//
//                .requestMatchers(HttpMethod.POST, "/api/v1/user")
//                .access(new WebExpressionAuthorizationManager("hasIpAddress('" + gatewayIp + "')"))
//
//                .requestMatchers(HttpMethod.GET,"/api/v1/user")
//                .access(new WebExpressionAuthorizationManager("hasIpAddress('"+environment.getProperty("gateway.ip")+"')"))
//
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.headers().frameOptions().disable();
//        return http.build();
//
//    }
//}



package com.photonest.user_service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Configuration
@EnableWebSecurity
public class WebSecurity {

    @Autowired
    private Environment environment;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // Retrieve the gateway IP from environment properties
        String gatewayIp = environment.getProperty("gateway.ip");
        System.out.println("Configured Gateway IP: " + gatewayIp); // Debugging line

        // Check if gatewayIp is null and throw an exception if it is
        if (gatewayIp == null) {
            throw new IllegalStateException("Gateway IP is not configured. Please check your configuration.");
        }

        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/api/v1/user").permitAll() // Allow all GET requests
                .requestMatchers(HttpMethod.POST, "/api/v1/user")
                .access(new WebExpressionAuthorizationManager("hasIpAddress('" + gatewayIp + "')")) // Use the gateway IP for POST requests
                .anyRequest().denyAll() // Deny all other requests for security
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }
}