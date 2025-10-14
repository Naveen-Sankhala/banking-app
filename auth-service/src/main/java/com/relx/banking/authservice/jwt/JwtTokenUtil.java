package com.relx.banking.authservice.jwt;


import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import com.relx.banking.authservice.entity.Users;

/**
 * @author Naveen.Sankhala
 * Sep 26, 2025
 */
@Component
public class JwtTokenUtil implements Serializable{

	private static final long serialVersionUID = -1446113981124111619L;

	private Instant now = Instant.now();
	static final long expiry = 3600L;
	
	//@Autowired
    //private AuthenticationManager authenticationManager;
	
	//@Autowired
    //private JwtEncoder jwtEncoder; // Provided by Authorization Server

//	static final String CLAIM_KEY_USERNAME = "sub";
//	static final String CLAIM_KEY_CREATED = "iat";
//	private Clock clock = DefaultClock.INSTANCE;
//
//	@Value("${jwt.signing.key.secret}")
//	private String secret;
//
//	@Value("${jwt.token.expiration.in.seconds}")
//	private Long expiration;
//
//	@Value("${jwt.refreshToken.expiration.in.seconds}")
//	private Long refTokenExpiration;
//
//	public String getUsernameFromToken(String token) {
//		return getClaimFromToken(token, Claims::getSubject);
//	}
//
//	public Date getIssuedAtDateFromToken(String token) {
//		return getClaimFromToken(token, Claims::getIssuedAt);
//	}
//
//	public Date getExpirationDateFromToken(String token) {
//		return getClaimFromToken(token, Claims::getExpiration);
//	}
//
//	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
//		final Claims claims = getAllClaimsFromToken(token);
//		return claimsResolver.apply(claims);
//	}
//
//	private Claims getAllClaimsFromToken(String token) {
//		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
//	}
//
//	private Boolean isTokenExpired(String token) {
//		final Date expiration = getExpirationDateFromToken(token);
//		return expiration.before(clock.now());
//	}
//
//	private Boolean ignoreTokenExpiration(String token) {
//		// here you specify tokens, for that the expiration is ignored
//		return false;
//	}
//
/*	public String generateToken(Users userDetails,List<String> userRoles, long branchId, String branchName, String branchType, String sTokenType) {
		Map<String, Object> claims = new HashMap<>();

		claims.put("UserId", userDetails.getUserId());
		claims.put("UserName", userDetails.getLoginName());
		claims.put("BranchlId", branchId);
		claims.put("BranchlName", branchName);
		claims.put("Roles", userRoles);
		claims.put("BranchType",branchType);
		return doGenerateToken(claims, userDetails.getUsername(),sTokenType);
		

	}

	private String doGenerateToken(Map<String, Object> claimsData, String subject, String sTokenType) {

		JwtClaimsSet claimsSet = JwtClaimsSet.builder()
				.issuer("banking-app")
				.subject(subject)
				.issuedAt(now)
				.expiresAt(now.plus(expiry, ChronoUnit.SECONDS))
				.claims(claims -> claims.putAll(claimsData))
				.build();



		String tokenValue=  jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
		Map.of(
				"access_token", tokenValue,
				"token_type", OAuth2AccessToken.TokenType.BEARER.getValue(),
				"expires_in", expiry,
				"scope", "bank.read bank.write"
				);

		return tokenValue;

		//return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(createdDate)
		//	.setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, secret).setHeaderParam("type", "IAFJWT").compact();
	}
*/	
//
//	public Boolean canTokenBeRefreshed(String token) {
//		return (!isTokenExpired(token) || ignoreTokenExpiration(token));
//	}
//
//	public String refreshToken(String token, String sTokenType) {
//		final Date createdDate = clock.now();
//		final Date expirationDate = calculateExpirationDate(createdDate,sTokenType);
//
//		final Claims claims = getAllClaimsFromToken(token);
//		claims.setIssuedAt(createdDate);
//		claims.setExpiration(expirationDate);
//
//		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
//	}
//
//	public Boolean validateToken(String token, ClaimsData claimsData) {
//		//JwtUserDetails user = (JwtUserDetails) userDetails;
//		final String username = getUsernameFromToken(token);
//		return (username.equals(claimsData.getUserName()) && !isTokenExpired(token));
//	}
//
//	private Date calculateExpirationDate(Date createdDate, String sTokenType) {
//		if(sTokenType.equalsIgnoreCase("access")) {
//			//System.out.println("createdDate access "+createdDate);
//			Date accessDate = new Date(createdDate.getTime() + expiration * 1000);
//			//System.out.println("test access "+accessDate );
//			return accessDate;
//		}
//		else {
//			//System.out.println("createdDate refresh "+createdDate);
//			Date refreshDate = new Date(createdDate.getTime() + refTokenExpiration * 1000);
//			//System.out.println("test refresh "+refreshDate );
//			return refreshDate;
//		}
//	}
//
//	public int getHospitalIdFromToken(String token) {
//		Claims claims = getAllClaimsFromToken(token);
//		return (int) claims.get("HospitalId");
//	}
//
//	@SuppressWarnings("unchecked")
//	public ClaimsData parseToken(String token) {
//		try {
//			Claims body = getAllClaimsFromToken(token);
//
//			ClaimsData claimsData = ClaimsData.builder()
//					.UserId(((Integer)body.get("UserId")).longValue())
//					.UserName(body.getSubject())
//					.LoginName((String) body.get("UserName"))
//					.HospitalId(((Integer)body.get("HospitalId")).longValue())
//					.HospitalName((String) body.get("HospitalName"))
//					.Roles((List<String>) body.get("Roles"))
//					.HospitalType((String) body.get("HospitalType"))
//					.build();
//
//			return claimsData;
//
//		} catch (JwtException | ClassCastException e) {
//			return null;
//		}
//	}
//
//
}
