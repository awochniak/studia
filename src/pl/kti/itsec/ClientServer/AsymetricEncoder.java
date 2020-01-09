package pl.kti.itsec.ClientServer;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;


public class AsymetricEncoder {
	public byte [] asymetricCipher(Cipher cipher, String message, Key publicKey) throws Exception {
		byte[] messageBytes = message.getBytes();
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(messageBytes);
	}
	
	public String asymetricEncode(byte[] cipherTextBytes) {
		return Base64.getEncoder().encodeToString(cipherTextBytes);
	}
}
