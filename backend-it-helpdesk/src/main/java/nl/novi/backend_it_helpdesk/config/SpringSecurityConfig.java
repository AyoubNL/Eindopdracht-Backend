package nl.novi.backend_it_helpdesk.config;


import nl.novi.backend_it_helpdesk.filter.JwtRequestFilter;
import nl.novi.backend_it_helpdesk.services.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {


    public final CustomUserDetailService userDetailsService;

    private final JwtRequestFilter jwtRequestFilter;

    private final PasswordEncoder passwordEncoder;

    public SpringSecurityConfig(CustomUserDetailService userDetailsService, JwtRequestFilter jwtRequestFilter, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
        this.passwordEncoder = passwordEncoder;
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {

        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();

    }

    @Bean
    protected SecurityFilterChain filter(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .httpBasic(basic -> basic.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
                        .requestMatchers(HttpMethod.POST, "/tickets", "/categories", "/details", "/fixes", "/screenshots/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users", "/tickets", "/categories", "/details", "/fixes").hasAnyAuthority("manager","agent","client")
                        .requestMatchers(HttpMethod.GET, "/users/*", "/tickets/*", "/categories/*", "/details/*", "/fixes/*", "/screenshots/**").hasAnyAuthority("manager","agent")
                        .requestMatchers(HttpMethod.PUT, "/users/**").hasAnyAuthority("manager")
                        .requestMatchers(HttpMethod.PUT, "/tickets/**", "/categories/*", "/details/*", "/fixes/*").hasAnyAuthority("manager", "agent","client")
                        .requestMatchers(HttpMethod.DELETE, "/users/*", "/tickets/*", "/categories/*", "/details/*", "/fixes/*").hasAuthority("manager")
                        .requestMatchers("/authenticated").authenticated()
                        .requestMatchers("/authenticate").permitAll()
                        .anyRequest().denyAll()
                ).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }


}
