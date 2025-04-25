package com.task.taskapi.configurations;

import com.task.taskapi.domain.models.User;
import com.task.taskapi.service.contrat.UserService;
import com.task.taskapi.utils.JWTTokenFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JWTTokenFilter jwtTokenFilter;

    public WebSecurityConfiguration(PasswordEncoder passwordEncoder, UserService userService, JWTTokenFilter jwtTokenFilter) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Bean
    AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder managerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        managerBuilder.userDetailsService(identifier ->{
            User user = userService.findUserByEmail(identifier);

            if(user == null){
                throw new UsernameNotFoundException("User not found with email: " + identifier);
            }
            return user;
        })
                .passwordEncoder(passwordEncoder);
        return managerBuilder.build();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http)throws  Exception{
        http.httpBasic(withDefaults()).csrf(csrf->csrf.disable());

        http.authorizeHttpRequests(auth->
                auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .anyRequest().authenticated()
        );

        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.exceptionHandling(handling -> handling.authenticationEntryPoint((req, res, ex)->{
           res.sendError(
                   HttpServletResponse.SC_UNAUTHORIZED,
                   "Authentication failed: " + ex.getMessage()
           );
        }));

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

