package com.rainbowletter.server.common.config.security;

import com.rainbowletter.server.common.config.security.uri.AccessAllowUri;
import com.rainbowletter.server.common.config.security.uri.AdminAllowUri;
import com.rainbowletter.server.common.config.security.uri.AllowUri;
import com.rainbowletter.server.common.config.security.uri.AnonymousAllowUri;
import com.rainbowletter.server.common.property.ClientProperty;
import com.rainbowletter.server.user.application.OAuthUserService;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final ClientProperty clientProperty;
	private final OAuthUserService oAuthUserService;
	private final OAuthSuccessHandler oAuthSuccessHandler;
	private final JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final var configuration = new CorsConfiguration();
		configuration.setAllowCredentials(true);
		configuration.setAllowedOriginPatterns(clientProperty.getUrls());
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
		configuration.setAllowedHeaders(List.of("*"));
		configuration.setExposedHeaders(List.of("*"));

		final var source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
		configureBasicSecurity(http);
		configureAuthorizationSecurity(http);
		configureSecurityExceptionHandler(http);
		configureSecurityFilter(http);
		configurationOAuth2(http);
		return http.build();
	}

	private void configureBasicSecurity(final HttpSecurity http) throws Exception {
		http.formLogin(AbstractHttpConfigurer::disable)
				.httpBasic(AbstractHttpConfigurer::disable)
				.csrf(AbstractHttpConfigurer::disable)
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.sessionManagement(session ->
						session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
	}

	private void configureAuthorizationSecurity(final HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth ->
				auth.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
						.permitAll()
						.requestMatchers(convertUriToPathMatcher(AccessAllowUri.values())).permitAll()
						.requestMatchers(convertUriToPathMatcher(AnonymousAllowUri.values())).anonymous()
						.requestMatchers(convertUriToPathMatcher(AdminAllowUri.values())).hasRole("ADMIN")
						.anyRequest().authenticated()
		);
	}

	private AntPathRequestMatcher[] convertUriToPathMatcher(final AllowUri[] allowUris) {
		return Arrays.stream(allowUris)
				.map(AllowUri::getUri)
				.map(AntPathRequestMatcher::antMatcher)
				.toArray(AntPathRequestMatcher[]::new);
	}

	private void configureSecurityExceptionHandler(final HttpSecurity http) throws Exception {
		http.exceptionHandling(exceptionHandling ->
				exceptionHandling
						.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
						.accessDeniedHandler(new CustomAccessDeniedHandler()));
	}

	private void configureSecurityFilter(final HttpSecurity http) {
		http.addFilterBefore(jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	private void configurationOAuth2(final HttpSecurity http) throws Exception {
		http.oauth2Login(oauth ->
				oauth.authorizationEndpoint(endpoint -> endpoint.baseUri("/api/oauth2/authorization/**"))
						.redirectionEndpoint(endpoint -> endpoint.baseUri("/api/login/oauth2/code/**"))
						.userInfoEndpoint(userInfo -> userInfo.userService(oAuthUserService))
						.successHandler(oAuthSuccessHandler));
	}

}
