package pl.kti.itsec.ClientServer;

public class ServerProtocolHandler {
	public String processMessage(String message) {
		StringBuffer outputMessage = new StringBuffer();
		
		for (int i = message.length() - 1; i >= 0; i--) {
			outputMessage.append(message.charAt(i));
		}
		
		return outputMessage.toString();
	}
}

