package pl.kti.itsec.ClientServer;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;

public class AsymetricDecoder {
	public byte[] asymetricDecode(String value) {
		return Base64.getDecoder().decode(value);
	}
	
	public String asymetricDecipher(Cipher cipher, Key privateKey, byte[] cipherTextBytes) throws Exception {
		cipher.init(Cipher.DECRYPT_MODE, privateKey, cipher
				.getParameters());
		byte[] byteDecryptedText = cipher.doFinal(cipherTextBytes);
		return new String(byteDecryptedText);
	}
}
