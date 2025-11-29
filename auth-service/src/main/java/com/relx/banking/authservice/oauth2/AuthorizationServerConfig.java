package com.relx.banking.authservice.oauth2;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.relx.banking.authservice.config.AppConfig;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.time.Duration;
import java.util.UUID;


/**
 * @author Naveen.Sankhala
 * Sep 23, 2025
 */
@Configuration
@RequiredArgsConstructor
public class AuthorizationServerConfig {

	final private AppConfig appConfig;

	/**
	 *	 1) Main security filter chain for the Authorization Server (exposes endpoints)
	 */
	@SuppressWarnings("removal")
	@Bean
	public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
		// Enable form login for the demo (so you can authenticate at the authorize endpoint)
		http.formLogin(Customizer.withDefaults());
		return http.build();
	}


	/**
	 * 2) Provider settings (issuer URL)
	 */
	@Bean
	@Order(1)
	public AuthorizationServerSettings authorizationServerSettings() {
		return AuthorizationServerSettings.builder()
				.issuer(appConfig.getAuthorizationServerIssuer()+"/Auth")   // adjust for prod HTTPS URL
				.build();
	}


	/**
	 * 3) Registered clients (in-memory). Add clients as needed.
	 */
	@Bean
	public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder) {
		
		RegisteredClient clientCredentialsClient = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("bank-client")
				.clientSecret(passwordEncoder.encode("secret"))
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				.scope(appConfig.getReadScope())
				.scope(appConfig.getWriteScope())
				.tokenSettings(TokenSettings.builder()
						.accessTokenTimeToLive(Duration.ofHours(1))
						.build())
				.build();

		RegisteredClient authCodeClient = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("bank-web")
				.clientSecret(passwordEncoder.encode("web-secret"))
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.redirectUri("http://localhost:9003/Auth/login/oauth2/code/bank-web") // adapt to your client
				.scope(appConfig.getOpenIdScope())
				.scope(appConfig.getProfileScope())
				.scope(appConfig.getReadScope())
				.tokenSettings(TokenSettings.builder()
						.accessTokenTimeToLive(Duration.ofHours(1))
						.refreshTokenTimeToLive(Duration.ofDays(30))
						.build())
				.build();

		return new InMemoryRegisteredClientRepository(clientCredentialsClient, authCodeClient);
	}

	/**
	 * 4) JWK source (RSA key pair) used by the Authorization Server to sign JWTs.
	 *    Exposes jwk set at /oauth2/jwks (or /.well-known/jwks.json depending on config)
	 */
	@Bean
	public JWKSet jwkSet(RSAKey rsaKey) {
		return new JWKSet(rsaKey);
	}

	/**
	 * Helper to create RSAKey (using Nimbus RSAKey builder)
	 */
	@Bean
	public RSAKey rsaKey() {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			KeyPair kp = keyPairGenerator.generateKeyPair();
			java.security.interfaces.RSAPublicKey publicKey = (java.security.interfaces.RSAPublicKey) kp.getPublic();
			java.security.interfaces.RSAPrivateKey privateKey = (java.security.interfaces.RSAPrivateKey) kp.getPrivate();

			return new RSAKey.Builder(publicKey)
					.privateKey(privateKey)
					.keyID(UUID.randomUUID().toString())
					.build();
		} catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
	}

	/**
	 * 5) Password encoder - for demo we use delegating encoder; you can change to BCrypt
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
