package com.bimbles.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import com.bimbles.services.AuthenticationService;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration{
	
	@Autowired 
	AuthenticationService authenticationService;
	
	@Autowired 
	JwtRequestFilter jwtFilter;
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Autowired
	void registerProvider (AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authenticationService);
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.
			csrf().disable()
			.cors().configurationSource(corsConfigurationSource()).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.formLogin().disable()
			.httpBasic().and()
			.authorizeHttpRequests()
				.requestMatchers(
						"/register", 
						"/login",
						"/swagger-ui/index.html")
					.permitAll()
				.requestMatchers("/preferences/**").permitAll()
				.requestMatchers(HttpMethod.POST, "/item/(restaurant|place|business|product)").hasAnyAuthority("ADMIN","NORMAL")
				.requestMatchers(HttpMethod.GET, "/item/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/item-review").hasAuthority("ADMIN")
				.requestMatchers(HttpMethod.DELETE, "/item/**").hasAuthority("ADMIN")
				.requestMatchers(HttpMethod.PUT, "/item/**/state").hasAuthority("ADMIN")
			.anyRequest().authenticated()
			.and().
			userDetailsService(authenticationService);
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
		configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT", "DELETE"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
