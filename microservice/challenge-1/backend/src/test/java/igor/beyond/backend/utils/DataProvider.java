package igor.beyond.backend.utils;

import igor.beyond.backend.entities.User;

public class DataProvider {
	
	public final static String VALID_ID_URI = "/users/1";
	public final static String INVALID_ID_URI = "/users/500";
	

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
	public static User realDbUser() {
		User realDbUser = new User();
		
		realDbUser.setId(1l);
		realDbUser.setName("Igor");
		realDbUser.setEmail("igor@gmail.com");
		realDbUser.setDescription("Do not delete or modify! Used for integration testing");
		
	    return realDbUser;
	  }
	
	public static User userToUpdate() {
		User userToUpdate = new User();
		
		userToUpdate.setId(10l);
		userToUpdate.setName("test10");
		userToUpdate.setEmail("test10@gmail.com");
		userToUpdate.setDescription("to update");
		
	    return userToUpdate;
	  }
}
