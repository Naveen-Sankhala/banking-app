package com.relx.banking.commonsecurity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.relx.banking.util.exception.AuthenticationException;

/**
 * @author Naveen.Sankhala
 * Dec 4, 2025
 */

public class Auth2TokenUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(Auth2TokenUtil.class);

	public static String getUsernameFromToken(String token) {
		return getClaimFromToken(token, JWTClaimsSet ::getSubject);
	}

	public static Date getIssuedAtDateFromToken(String token) {
		return getClaimFromToken(token, JWTClaimsSet::getIssueTime);
	}
	
	public static Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(Date.from(Instant.now()));
	}
	
	public static Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, JWTClaimsSet::getExpirationTime);
	}

	public static <T> T getClaimFromToken(String token, Function<JWTClaimsSet, T> claimsResolver) {
		JWTClaimsSet claims = null;
		try {
			claims = getAllClaimsFromToken(token);
		} catch (ParseException | JOSEException e) {
			logger.error("Invalid Token :: " +e.getMessage());
			throw new AuthenticationException("Invalid Token ::" +e.getMessage());
		}
		return claimsResolver.apply(claims);
	}

	private static JWTClaimsSet getAllClaimsFromToken(String token) throws ParseException, JOSEException {
		SignedJWT jwt = SignedJWT.parse(token);
		
		JWTClaimsSet claims = jwt.getJWTClaimsSet();
		Date expiration = claims.getExpirationTime();
		if(expiration.before(Date.from(Instant.now())))
			throw new AuthenticationException("Invalid Token,Token is Expired, It's valid till :: "+ expiration);
			
		return claims;
	}

//	private Boolean verifyToken(SignedJWT jwt) throws JOSEException {
//		JWSVerifier verifier = new RSASSAVerifier(rsaKey.toRSAPublicKey());
//	    return jwt.verify(verifier);
//	}

	
	public static Boolean validateToken(String token, ClaimsData claimsData) {
		final String username = getUsernameFromToken(token);
		return (username.equals(claimsData.getUserName()) && !isTokenExpired(token));
	}
	
	@SuppressWarnings("unchecked")
	public static ClaimsData parseToken(String token) {
		try {
			JWTClaimsSet body = getAllClaimsFromToken(token);

			ClaimsData claimsData = ClaimsData.builder()
					.UserId(((Number)body.getClaim("UserId")).longValue())
					.UserName((String) body.getClaim("sub"))
					.LoginName((String) body.getClaim("UserName"))
					.BranchId(((Number) body.getClaim("BranchId")).longValue())
					.BranchName((String) body.getClaim("BranchName"))
					.BranchType((String) body.getClaim("BranchType"))
					.Roles((List<String>) body.getClaim("Roles"))
					.build();
			
			return claimsData;
		} catch (ParseException | JOSEException e) {
			logger.error("Getting Exception Token Parsing ::: "+e.getMessage());
			return null;
		}

	}
}
