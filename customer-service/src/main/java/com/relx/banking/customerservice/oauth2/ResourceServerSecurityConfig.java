package com.relx.banking.customerservice.oauth2;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.relx.banking.customerservice.config.FilterChainExceptionHandler;

import lombok.RequiredArgsConstructor;


/**
 * @author Naveen.Sankhala
 * Sep 25, 2025
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ResourceServerSecurityConfig {

	private final JwtUnAuthorizedResponseAuthenticationEntryPoint jwtEntryPoint;
	private final JwtTokenAuthorizationOncePerRequestFilter jwtAuthenticationFilter;
	private final FilterChainExceptionHandler filterChainExceptionHandler;

	@Value("${cors.url}")
	private String corsUrl;

	@Value("${spring.security.oauth2.get.token-uri}")
	private String authenticationPath;

	@Value("${spring.security.oauth2.logout.token-uri}")
	private String logoutPath;

	@Value("${spring.security.oauth2.refresh.token-uri}")
	private String refreshPath;


	@Bean
	public CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration config= new CorsConfiguration();
		config.setAllowCredentials(true);
		config.setAllowedOrigins(Arrays.asList(corsUrl));
		config.setAllowedHeaders(Arrays.asList("*"));
		config.setAllowedMethods(Arrays.asList("OPTIONS","GET","PUT","POST","DELETE"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;

	}


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
		// CORS
		.cors(cors -> cors.configurationSource(corsConfigurationSource()))

		// Disable defaults
		.csrf(AbstractHttpConfigurer::disable)
		.httpBasic(AbstractHttpConfigurer::disable)
		.formLogin(AbstractHttpConfigurer::disable)

		// Stateless session
		.sessionManagement(session ->
		session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				)
		// Exception handling
		.exceptionHandling(ex ->
		ex.authenticationEntryPoint(jwtEntryPoint)
				)
		// Authorization rules (THIS REPLACES webSecurity.ignoring)
		.authorizeHttpRequests(auth -> auth

				// OPTIONS
				.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				
			    // 🔥 CONFIG SERVER (VERY IMPORTANT)
			    .requestMatchers("/common/**").permitAll()

			    // ERROR
			    .requestMatchers("/error").permitAll()

				// Auth endpoints
				.requestMatchers(HttpMethod.POST, authenticationPath).permitAll()
				.requestMatchers(HttpMethod.POST, logoutPath).permitAll()
				.requestMatchers(HttpMethod.POST, refreshPath).permitAll()

				// Public GET APIs
				.requestMatchers(HttpMethod.GET, "/share/**").permitAll()

				// Swagger & whitelisted
				.requestMatchers(AUTH_WHITELIST).permitAll()

				// Everything else secured
				.anyRequest().authenticated()
				)

		// Filters
		.addFilterBefore(filterChainExceptionHandler, LogoutFilter.class)
		.addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}


	private static final String[] AUTH_WHITELIST = {
			"/swagger-resources/**",
			"/swagger-ui.html",
			"/v2/api-docs",
			"/webjars/**",
			"/configuration/**",
			"/swagger-ui/**",
			"/sysAdmin/**"
	};

	/*	
//	@Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/bank/accounts/**").hasAuthority("SCOPE_bank.read")
//                .anyRequest().authenticated()
//            )
//            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
//        //  .oauth2ResourceServer(oauth2 -> oauth2.jwt());
//        return http.build();
//    }

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/config/branch/").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwt ->
                    jwt.jwkSetUri("http://localhost:9003/Auth/oauth2/jwks")
                )
            );

        return http.build();
    }
	 */


}
