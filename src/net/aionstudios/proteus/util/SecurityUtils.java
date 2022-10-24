package net.aionstudios.proteus.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * A class providing utilities for security.
 * @author Winter Roberts
 */
public class SecurityUtils {
	
	private static final String tokenChars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static SecureRandom rnd = new SecureRandom();

	/**
	 * Generates a token of the given length.
	 * @param length The length of token to generate.
	 * @return A token.
	 */
	public static String genToken(int length){
	   StringBuilder sb = new StringBuilder(length);
	   for( int i = 0; i < length; i++ ) 
	      sb.append( tokenChars.charAt( rnd.nextInt(tokenChars.length()) ) );
	   return sb.toString();
	}
	
	/**
	 * Uses SHA-512 to hash a string with a salt.
	 * @param passwordToHash The password which should be hashed.
	 * @param salt The salt which should be used to hash.
	 * @return The result of this hash.
	 */
	public static String sha512PasswordHash(String passwordToHash, String salt){
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt.getBytes(StandardCharsets.UTF_8));
			byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++){
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e){
			e.printStackTrace();
		}
		return generatedPassword;
	}
	
	/**
	 * Uses SHA-512 to hash a string.
	 * @param s The password which should be hashed.
	 * @return The result of this hash.
	 */
	public static String sha512Hash(String s){
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] bytes = md.digest(s.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++){
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e){
			e.printStackTrace();
		}
		return generatedPassword;
	}

}