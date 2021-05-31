package project;

import java.util.List;

import jsonObj.UserData;

public interface UserDataManager {
	UserData insert(UserData u);
	
	UserData findByUserName(String name);

	List<UserData> findAll();

	int update(UserData data);

	int delete(UserData data);
	
}
