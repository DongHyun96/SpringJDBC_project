package project;

import java.util.List;

import data.User;

public interface UserManager {
	User insert(String username, String email, String password, String userVersion);

	User findOne(long id);
	
	User findByEmail(String email);
	
	User findByUserName(String name);
	List<User> findAll();

	int update(User u);

	int delete(User u);
}
