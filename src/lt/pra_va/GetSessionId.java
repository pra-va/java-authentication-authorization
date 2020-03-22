package lt.pra_va;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetSessionId {

	/* Base URL of SVF PDF Archiver API */
	private String BASE_URL;
	/* URL for login */
	private String LOGIN_URL;
	
	/* Session ID at time of login */
	private String sessionId;
	
	public GetSessionId(String BASE_URL) {
		this.BASE_URL = BASE_URL;
		this.LOGIN_URL = BASE_URL + "/login";
	}

	public String login(String user, String password) throws IOException {
		// Create HttpURLConnection
		URL url = new URL(LOGIN_URL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		// HttpURLConnection settings
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setAllowUserInteraction(false);
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		// Set the user ID and password that were passed in as parameters
		Writer writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
		writer.write("username=" + user);
		String encrypted = password;
		writer.write("&password=" + encrypted);
		writer.close();

		// If processing did not complete successfully
		if (conn.getResponseCode() != 200) {
			System.out.println(conn.getResponseCode());
			throw new IOException(conn.getResponseMessage());
		}

		// If processing completed successfully, the unique error code can be retrieved
		
			// Extract the session ID
			String cookie = conn.getHeaderField("Set-Cookie");
			String[] array = cookie.split(";");
			for (String str : array) {
				if (str.matches("JSESSIONID=.*")) {
					String[] kvp = str.split("=");
					this.sessionId = kvp[1];
					break;
				}
			}


		// Disconnect connection
		conn.disconnect();
		return this.sessionId;

	}
}
