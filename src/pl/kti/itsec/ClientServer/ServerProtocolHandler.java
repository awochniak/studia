
package pl.kti.itsec.ClientServer;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

import javax.crypto.Cipher;

public class ServerProtocolHandler {
	
	private Key privateKey;
	private Key publicKey;
	private Cipher cipher;
	
	private AsymetricEncoder asymetricEncoder = new AsymetricEncoder();
	private AsymetricDecoder asymetricDecoder = new AsymetricDecoder();

	
	public String processMessage(String message) throws Exception {
		
		String encryptedMessage = encryptMessage(message);
		String decryptedMessage =  decryptMessage(encryptedMessage);
		
		return "Encoded: " + encryptedMessage + ", Decoded: " + decryptedMessage;
		
	}

	private String decryptMessage(String encryptedMessage) throws Exception {
		StringBuffer inputMessage = new StringBuffer();
		
		for (int i = encryptedMessage.length() - 1; i >= 0; i--) {
			inputMessage.append(encryptedMessage.charAt(i));
		}
		
		return getDecipheredValue(inputMessage.toString());
	}

	private String encryptMessage(String message) throws Exception {
		generateKeyPair();
		
		String encryptedMessage = getEncodedValue(message);
		StringBuffer outputMessage = new StringBuffer();

		for (int i = encryptedMessage.length() - 1; i >= 0; i--) {
			outputMessage.append(encryptedMessage.charAt(i));
		}

		return outputMessage.toString();
	}
	
	private void generateKeyPair() throws Exception {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA"); 
		kpg.initialize(2048);
		
		KeyPair kp = kpg.genKeyPair();
		publicKey = kp.getPublic();
		privateKey = kp.getPrivate();
	 
		cipher = Cipher.getInstance("RSA");
	}
	
	private String getEncodedValue(String message) throws Exception {
		byte[] messageBytes = asymetricEncoder.asymetricCipher(cipher, message, publicKey);
		return asymetricEncoder.asymetricEncode(messageBytes);
	}
	
	private String getDecipheredValue(String message) throws Exception {
		byte[] decodedMessage = asymetricDecoder.asymetricDecode(message);
		return asymetricDecoder.asymetricDecipher(cipher, privateKey, decodedMessage);
	}
}