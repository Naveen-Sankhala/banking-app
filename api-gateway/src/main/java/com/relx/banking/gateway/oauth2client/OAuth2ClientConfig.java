package com.relx.banking.gateway.oauth2client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;

/**
 * @author Naveen.Sankhala
 * Dec 30, 2025
 */
@Configuration
public class OAuth2ClientConfig {

	@Bean
	public ReactiveOAuth2AuthorizedClientManager authorizedClientManager(
			ReactiveClientRegistrationRepository repo,
			ServerOAuth2AuthorizedClientRepository service) {

		ReactiveOAuth2AuthorizedClientProvider provider =
				ReactiveOAuth2AuthorizedClientProviderBuilder.builder()
				.clientCredentials()
				.build();

		DefaultReactiveOAuth2AuthorizedClientManager manager =
				new DefaultReactiveOAuth2AuthorizedClientManager(repo, service);

		manager.setAuthorizedClientProvider(provider);
		return manager;
	}
}
