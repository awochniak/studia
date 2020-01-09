package pl.kti.itsec.ClientServer;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class ServerProtocolHandler {
	
	Cipher cipher;
	SecretKey key;
	byte[] cipheredMessage;
	
	private SymmetricEncryption symmetricEncryption = new SymmetricEncryption();
	private SymmetricDecryptor symmetricDecryptor = new SymmetricDecryptor();
	
	public String processMessage(String message) throws Exception {
		
		String encryptedMessage = encryptMessage(message);
		String decryptedMessage = decryptMessage(encryptedMessage);
		
		return "Encoded: " + encryptedMessage + ", Decoded: " + decryptedMessage;
	}

	private String decryptMessage(String encryptedMessage) throws Exception {
		StringBuffer inputMessage = new StringBuffer();
		
		for (int i = encryptedMessage.length() - 1; i >= 0; i--) {
			inputMessage.append(encryptedMessage.charAt(i));
		}
		
		return getSymmetricDecryptedString(inputMessage.toString());
	}

	private String encryptMessage(String message) throws Exception {
		String encryptedMessage = getSymmetricEncryptedString(message);				
		StringBuffer outputMessage = new StringBuffer();
		
		for (int i = encryptedMessage.length() - 1; i >= 0; i--) {
			outputMessage.append(encryptedMessage.charAt(i));
		}
		
		return outputMessage.toString();
	}
	
	private String getSymmetricEncryptedString(String message) throws Exception {
		key = symmetricEncryption.getKey();
		cipher = symmetricEncryption.getCipher();
		cipheredMessage = symmetricEncryption.getCipheredMessage(key, cipher, message);
		return symmetricEncryption.encodeMessage(cipheredMessage);
	}
	
	private String getSymmetricDecryptedString(String message) throws Exception {
		return symmetricDecryptor.decryptMessage(key, cipher, Base64.getDecoder().decode(message));
	}
	
}

