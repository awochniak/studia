package pl.kti.itsec.ClientServer;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class SymmetricEncryption {

	public SecretKey getKey() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(128);
		return keyGenerator.generateKey();
	}
	
	public Cipher getCipher() throws Exception {
		return Cipher.getInstance("AES");
	}

	public byte[] getCipheredMessage(SecretKey key, Cipher cipher, String message) throws Exception {
		byte[] messageBytes = message.getBytes();
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(messageBytes);
	}

	public String encodeMessage(byte[] cipheredText) {
		return Base64.getEncoder().encodeToString(cipheredText);
	}
	
}
