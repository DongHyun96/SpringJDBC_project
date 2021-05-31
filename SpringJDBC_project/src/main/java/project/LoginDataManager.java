package project;

import java.util.List;

import jsonObj.LoginData;

public interface LoginDataManager {
	LoginData insert(LoginData ld);

	LoginData findOne(long id);
	
	LoginData findByEmail(String email);
	
	LoginData findByUserName(String name);
	
	List<LoginData> findAll();

	int update(LoginData data);

	int delete(LoginData data);
}
