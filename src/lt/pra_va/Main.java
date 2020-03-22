package lt.pra_va;

import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		
		String BASE_URL = "http://akademijait.vtmc.lt:8180/dvs/api";
		
		GetSessionId sessionID = new GetSessionId(BASE_URL);
		CreateUser createUser = new CreateUser(BASE_URL);
		try {
			String JSESSIONID = sessionID.login("admin", "adminadmin");
			createUser.POSTRequest("[]", "users first name", "users last name", "useruser", "user3", JSESSIONID);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
}
