package com.relx.banking.authservice.config;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jwt.*;
import com.relx.banking.authservice.entity.Users;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Naveen.Sankhala
 * Nov 19, 2025
 */
@Service
public class TokenService {

	private final RSAKey rsaKey;

	public TokenService(RSAKey rsaKey) {
		this.rsaKey = rsaKey;
	}

	public String createAccessToken(Users users, List<String> roles, long branchId, String sBranchName, String sBranchType, String string) {
		try {
			Instant now = Instant.now();
			JWSSigner signer = new RSASSASigner(rsaKey.toPrivateKey());

			// Create JWT claims
			JWTClaimsSet claims = new JWTClaimsSet.Builder()
					.subject(users.getUsername())
					.issuer("http://localhost:9003/Auth") // same as in AuthorizationServerSettings
					.issueTime(Date.from(now))
					.expirationTime(Date.from(now.plusSeconds(3600))) // 1 hour
					.claim("roles",roles)
					.claim("scope", List.of("bank.read", "bank.write"))
					.claim("type", "access")
					.build();

			// Sign JWT
			SignedJWT signedJWT = new SignedJWT(
					new JWSHeader.Builder(JWSAlgorithm.RS256)
					.keyID(rsaKey.getKeyID())
					.type(JOSEObjectType.JWT)
					.build(),
					claims);

			signedJWT.sign(signer);

			return signedJWT.serialize();

		} catch (Exception e) {
			throw new RuntimeException("Error creating access token", e);
		}
	}

}
