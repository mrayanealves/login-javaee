package br.ufrn.imd.logan.security;

import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTUtil {
	private static String key = "L0G4N01T0K3NS3CR3T4T1ONNn40f4C01D314d0Qu3C0L0C4R4QU1";

	public static String create(String subject) {
		Date dateCreate = new Date();
		
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);

		SecretKeySpec key = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS512.getJcaName());

		String token = Jwts.builder()
					.setIssuedAt(dateCreate)
					.setSubject(subject)
					.signWith(key)
					.compact();
		
		return "Bearer " + token;
	}

	public static Claims decode(String token) {
		return Jwts.parser()
				.setSigningKey(key)
				.parseClaimsJws(token).getBody();
	}
}
