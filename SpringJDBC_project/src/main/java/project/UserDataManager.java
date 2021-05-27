package project;

import java.util.List;

import data.UserData;

public interface UserDataManager {
	UserData insert(String username, int score, int coin, String knight);
	
	UserData findByUserName(String name);

	List<UserData> findAll();

	int update(UserData data);

	int delete(UserData data);
	
}
