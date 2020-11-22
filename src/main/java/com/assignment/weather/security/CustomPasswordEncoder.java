package com.assignment.weather.security;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomPasswordEncoder implements PasswordEncoder{

	private static final String HASHING_ALGORITHM = "SHA-256";

	@Override
	public String encode(CharSequence rawPassword) {
		// Hash the string
		byte[] encodedhash = null;
		try {
			encodedhash = MessageDigest.getInstance(HASHING_ALGORITHM).digest(rawPassword.toString().getBytes(StandardCharsets.UTF_8));
		} catch (NoSuchAlgorithmException e) {
			
		}
		return new String(Hex.encode(encodedhash));
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		// Encode(Hash) the password provided by the user
		String providedPassword = encode(rawPassword);

		// Compare the hashes
		return providedPassword.equals(encodedPassword);
	}
}
