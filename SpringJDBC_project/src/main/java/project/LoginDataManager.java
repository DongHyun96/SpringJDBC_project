package project;

import java.util.List;

import data.LoginData;

public interface LoginDataManager {
	LoginData insert(String username, String email, String password, String userVersion);

	LoginData findOne(long id);
	
	LoginData findByEmail(String email);
	
	LoginData findByUserName(String name);
	
	List<LoginData> findAll();

	int update(LoginData data);

	int delete(LoginData data);
}
