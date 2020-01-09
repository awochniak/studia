package pl.kti.itsec.ClientServer;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class SymmetricDecryptor {
	
	public byte[] decodeMessage(String message) {
		return Base64.getDecoder().decode(message);
	}

	public String decryptMessage(SecretKey key, Cipher cipher, byte[] message) throws Exception {
		cipher.init(Cipher.DECRYPT_MODE, key, cipher.getParameters());
		return new String(cipher.doFinal(message));
	}
}
