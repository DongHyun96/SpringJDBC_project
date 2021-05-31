package project;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.UserDataRepository;
import jsonObj.UserData;

// TODO: input validation in methods

@Service
public class UserDataManagerImpl implements UserDataManager {

	@Autowired
	private UserDataRepository repo;

	@Override
	public UserData insert(UserData u) {
		try {
		UserData result = repo.save(u);
		return result;
		} catch (Exception e) { 
			// When username(primary key) is duplicated. 
			System.out.println("From UserDataManagerImpl: Insertion fails due to duplicated username");
			return null;
		}
	}

	@Override
	public UserData findByUserName(String name) {
		return repo.findOne(name);  // Return null if no user found.
	}

	@Override
	public List<UserData> findAll() {
		return repo.findAll();
	}

	
	@Override
	public int update(UserData data) {
		// Update score, coin, knight
		
		if (findByUserName(data.getUserName()) == null) {
			return 0;
		}
		if (data.getCoin() < 0) {
			return 0;
		}
		return repo.update(data);
	}

	@Override
	public int delete(UserData data) {
		return repo.delete(data);
	}


}
