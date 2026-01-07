package com.relx.banking.authservice.oauth2;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jwt.*;
import com.relx.banking.authservice.config.AppConfig;
import com.relx.banking.authservice.entity.Users;
import com.relx.banking.authservice.util.AuthenticationException;
import com.relx.banking.commonsecurity.ClaimsData;
import com.nimbusds.jose.jwk.RSAKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * @author Naveen.Sankhala
 * Nov 19, 2025
 */
@Service
public class OAuth2TokenUtil {
	private final static Logger logger = LoggerFactory.getLogger(OAuth2TokenUtil.class);

	private final RSAKey rsaKey;
	private final AppConfig appConfig;
	
	public OAuth2TokenUtil(RSAKey rsaKey,AppConfig appConfig) {
		this.rsaKey = rsaKey;
		this.appConfig = appConfig;
	}


	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, JWTClaimsSet ::getSubject);
	}

	public Date getIssuedAtDateFromToken(String token) {
		return getClaimFromToken(token, JWTClaimsSet::getIssueTime);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, JWTClaimsSet::getExpirationTime);
	}

	public Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(Date.from(Instant.now()));
	}

	public <T> T getClaimFromToken(String token, Function<JWTClaimsSet, T> claimsResolver) {
		JWTClaimsSet claims = null;
		try {
			claims = getAllClaimsFromToken(token);
		} catch (ParseException | JOSEException e) {
			logger.error("Invalid Token");
		}
		return claimsResolver.apply(claims);
	}

	private JWTClaimsSet getAllClaimsFromToken(String token) throws ParseException, JOSEException {
		SignedJWT jwt = SignedJWT.parse(token);

		Boolean isVerify = verifyToken(jwt);
		if(!isVerify)
			throw new AuthenticationException("Invalid refresh token");
		
		JWTClaimsSet claims = jwt.getJWTClaimsSet();
		return claims;
	}

	private Boolean verifyToken(SignedJWT jwt) throws JOSEException {
		JWSVerifier verifier = new RSASSAVerifier(rsaKey.toRSAPublicKey());
	    return jwt.verify(verifier);
	}

	public Boolean validateToken(String token, ClaimsData claimsData) {
		final String username = getUsernameFromToken(token);
		return (username.equals(claimsData.getUserName()) && !isTokenExpired(token));
	}

	public String generateAccessToken(Users users, List<String> roles, long branchId, String sBranchName, String sBranchType, String sTokenType) {
		try {
			
			final Instant now = Instant.now();
			final Date expirationDate = calculateExpirationDate(now,sTokenType);
			
			JWSSigner signer = new RSASSASigner(rsaKey.toPrivateKey());

			// Create JWT claims
			JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
					.subject(users.getUsername())
					.issuer(appConfig.getAuthorizationServerIssuer()+"/Auth")
					.issueTime(Date.from(now))
					.expirationTime(expirationDate) 
					.claim("BranchType",sBranchType)
					.claim("BranchName",sBranchName)
					.claim("BranchId",branchId)
					.claim("UserName",users.getLoginName())
					.claim("UserId",users.getUserId())
					.claim("Roles",roles)
					.claim("scope", List.of(appConfig.getReadScope(), appConfig.getWriteScope()))
					.claim("TokenType", sTokenType)
					.claim("ActorType", "Service") //User/Service
					.build();

			// Sign JWT
			SignedJWT signedJWT = new SignedJWT(
					new JWSHeader.Builder(JWSAlgorithm.RS256)
					.keyID(rsaKey.getKeyID())
					.type(JOSEObjectType.JWT)
					.build(),
					jwtClaimsSet);

			signedJWT.sign(signer);

			return signedJWT.serialize();

		} catch (Exception e) {
			throw new RuntimeException("Error creating access token", e);
		}
	}

		
	public String refreshToken(String refreshToken, String sTokenType) throws Exception{
		
		final Instant now = Instant.now();
		final Date expirationDate = calculateExpirationDate(now,sTokenType);
		JWSSigner signer = new RSASSASigner(rsaKey.toRSAPrivateKey());

		JWTClaimsSet oldClaims = getAllClaimsFromToken(refreshToken);

		JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder(oldClaims)
				.subject(oldClaims.getSubject())
				.issuer(appConfig.getAuthorizationServerIssuer()+"/Auth") 
				.issueTime(Date.from(now))
				.expirationTime(expirationDate)  //Date.from(Instant.now().plus(Duration.ofMinutes(10)))
				.claim("scope", List.of(appConfig.getReadScope(), appConfig.getWriteScope()))
				.claim("TokenType", sTokenType)
				.build();

		SignedJWT signedJWT = new SignedJWT(
				new JWSHeader.Builder(JWSAlgorithm.RS256)
				.keyID(rsaKey.getKeyID())
				.type(JOSEObjectType.JWT)
				.build(),
				jwtClaimsSet
				);
		
		signedJWT.sign(signer);

		return signedJWT.serialize();

	}
	
	@SuppressWarnings("unchecked")
	public ClaimsData parseToken(String token) {
		try {
			JWTClaimsSet body = getAllClaimsFromToken(token);

			ClaimsData claimsData = ClaimsData.builder()
					.UserId(((Number)body.getClaim("UserId")).longValue())
					.UserName((String) body.getClaim("UserName"))
					.LoginName((String) body.getClaim("UserName"))
					.BranchId(((Number) body.getClaim("BranchId")).longValue())
					.BranchName((String) body.getClaim("BranchName"))
					.BranchType((String) body.getClaim("BranchType"))
					.Roles((List<String>) body.getClaim("Roles"))
					.build();

			return claimsData;

		} catch (JwtException | ClassCastException | ParseException | JOSEException e) {
			logger.error("Getting Exception Token Parsing ::: "+e.getMessage());
			return null;
		}
	}
	
	private Date calculateExpirationDate(Instant now, String sTokenType) {
		if(sTokenType.equalsIgnoreCase("access")) {
			Date accessDate = Date.from(now.plus(Duration.ofHours(5)));//Duration.ofMinutes(5)  
			//Date accessDate = new Date(createdDate.getTime() + expiration * 1000);
			return accessDate;
		}
		else {
			Date refreshDate = Date.from(now.plus(Duration.ofDays(30)));//Duration.ofMinutes(10)
			return refreshDate;
		}
	}
	 
}
