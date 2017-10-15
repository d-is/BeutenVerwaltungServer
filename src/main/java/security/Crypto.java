package security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.io.*;

import java.util.Arrays;


public class Crypto {

	//Hashverfahren für Passwort (SHA-256)
	public static String hash(String password, String salt) {
		MessageDigest md = null;
		byte[] digest = null;
		password.concat(salt);
		try {
			md = MessageDigest.getInstance("SHA-256");

			try {
				md.update(password.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Change this to "UTF-16" if needed
			digest = md.digest();

		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return new String(digest);
	}

}
