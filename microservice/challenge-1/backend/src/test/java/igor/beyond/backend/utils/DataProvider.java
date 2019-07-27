package igor.beyond.backend.utils;

import igor.beyond.backend.entities.User;

public class DataProvider {

	public static User getDbUser() {
		User dbUser = new User();
		
		dbUser.setId(1L);
		dbUser.setName("Igor");
		dbUser.setEmail("igor@gmail.com");
		dbUser.setDescription("first user");
		
	    return dbUser;
	  }
	
	public static User userToSave() {
		User newUser = new User();
		
		newUser.setId(2L);
		newUser.setName("Jack");
		newUser.setEmail("jack@gmail.com");
		newUser.setDescription("second user");
		
	    return newUser;
	  }

}
