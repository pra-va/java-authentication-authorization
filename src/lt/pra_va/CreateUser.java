package lt.pra_va;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CreateUser {
	
	private String BASE_URL;
	
	public CreateUser(String BASE_URL) {
		this.BASE_URL = BASE_URL;
	}

	// THIS post body works correctly
//	final String POST_PARAMS = "{\n" + 
//			"    \"groupList\": [],\n" +
//	        "    \"name\": \"javaUser\",\n" +
//	        "    \"password\": \"javaUser\",\n" +
//	        "    \"surname\": \"javaUser\",\n" +
//	        "    \"username\": \"javaUser\"" + 
//	        "\n}";
	
	/**
	 * 
	 * @param groupList - [ "dummy1", "dummy2"]
	 * @param firstName - someNameString
	 * @param lastName - someSurnameString
	 * @param password - somePasswordString
	 * @param username - someUsernameString
	 * @throws IOException
	 */
	public void POSTRequest( String groupList, String firstName, String lastName, String password,  String username, String JSESSIONID) throws IOException {
		final String POST_PARAMS = "{\n" + 
				"    \"groupList\": " + groupList + ",\n" +
		        "    \"name\": \""+ firstName +"\",\n" +
		        "    \"password\": \""+ password + "\",\n" +
		        "    \"surname\": \"" + lastName + "\",\n" +
		        "    \"username\": \"" + username + "\"" + 
		        "\n}";
	    System.out.println(POST_PARAMS);
	    URL obj = new URL(this.BASE_URL + "/createuser");
	    HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
	    
	    postConnection.setRequestProperty(
			    "Cookie","JSESSIONID=" + JSESSIONID);
	    postConnection.setRequestMethod("POST");
	    postConnection.setRequestProperty("Content-Type", "application/json");
	    postConnection.setDoOutput(true);
	    OutputStream os = postConnection.getOutputStream();
	    os.write(POST_PARAMS.getBytes());
	    os.flush();
	    os.close();
	    int responseCode = postConnection.getResponseCode();
	    System.out.println("POST Response Code :  " + responseCode);
	    if (responseCode == HttpURLConnection.HTTP_CREATED) { //success
	        BufferedReader in = new BufferedReader(new InputStreamReader(
	            postConnection.getInputStream()));
	        String inputLine;
	        StringBuffer response = new StringBuffer();
	        while ((inputLine = in .readLine()) != null) {
	            response.append(inputLine);
	        } in .close();
	        // print result
	        System.out.println(response.toString());
	    } else {
	        System.out.println("POST NOT WORKED");
	    }
	}
}
