package data;

import java.util.List;

import jsonObj.LoginData;

public interface LoginDataRepository {
	//CRUD ( create, read, update, delete )

		LoginData findOne(long id);

		LoginData save(LoginData loginData);
		
		List<LoginData> findAll();
		
		int update(LoginData ld);
		
		int delete(LoginData ld);
}
