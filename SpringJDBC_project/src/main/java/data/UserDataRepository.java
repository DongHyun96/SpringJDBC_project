package data;

import java.util.List;

public interface UserDataRepository {
	//CRUD ( create, read, update, delete )

	UserData findOne(String userName);

	UserData save(UserData u);
	
	List<UserData> findAll();
	
	int update(UserData u);
	
	int delete(UserData u);
	
}
