package bookingcare.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

import bookingcare.security.Jwt.jwt.JwtEntryPoint;
import bookingcare.security.Jwt.jwt.JwtTokenFilter;
import bookingcare.security.Jwt.userprincal.UserDetailService;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
    UserDetailService userDetailService;
    @Autowired
    private JwtEntryPoint jwtEntryPoint;
    
//    @Autowired
//    private JwtTokenFilter jwtTokenFilter;
    @Bean
    public JwtTokenFilter jwtTokenFilter(){
        return new JwtTokenFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, MvcRequestMatcher.Builder mvc) throws Exception{
    	httpSecurity.cors().and().csrf().disable();
    	httpSecurity.authorizeHttpRequests(configurer -> configurer
				.requestMatchers(mvc.pattern("/api/auth/**")).permitAll()
				.requestMatchers(mvc.pattern("/api/doctor/**")).hasRole("DOCTOR")
				.requestMatchers(mvc.pattern("/api/patients/**")).hasRole("USER")
				.requestMatchers(mvc.pattern("/api/admin/**")).hasRole("ADMIN").anyRequest().authenticated());
				
				
    	
         
    	httpSecurity.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }


}
