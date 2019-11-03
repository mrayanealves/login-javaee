package br.ufrn.imd.logan.security;

import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTUtil {
	private static String key = "L0G4N01@_T0K3N#_S3CR3T4T1ON*_Nn40&_f4C0%_1D314@_d0_Qu3_C0L0C4R_4QU1@#*$";

	public static final String TOKEN_HEADER = "Authentication";

	public static String create(String subject) {
		Date dateCreate = new Date();
		
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);

		SecretKeySpec key = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS512.getJcaName());

		return Jwts.builder()
				.setIssuedAt(dateCreate)
				.setSubject(subject)
				.signWith(key)
				.compact();
	}

	public static Jws<Claims> decode(String token) {
		return Jwts.parser()
				.setSigningKey(key)
				.parseClaimsJws(token);
	}
}
