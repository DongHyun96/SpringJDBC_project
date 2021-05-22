package project;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.UserData;
import data.UserDataRepository;

// TODO: input validation in methods

@Service
public class UserDataManagerImpl implements UserDataManager {

	@Autowired
	private UserDataRepository repo;

	@Override
	public UserData insert(String username, int score, int coin, String knight) {
		try {
		UserData user = repo.save(new UserData(username, score, coin, knight));
		return user;
		} catch (Exception e) { 
			// When username(primary key) is duplicated. 
			System.out.println("From UserDataManagerImpl: Insertion fails due to duplicated username");
			return null;
		}
	}

	@Override
	public UserData findOne(String userName) {
		return repo.findOne(userName);
	}
	
	// a custom service method that uses findAll()
	@Override
	public UserData findByUserName(String name) {
		for (UserData u : findAll()) {
			if (u.getUserName().equals(name)) {  // initial -> u.getUserName().equalsIgnoreCase(name)
				return u;
			}
		}
		// No Player found...
		return null;
	}

	@Override
	public List<UserData> findAll() {
		return repo.findAll();
	}

	
	@Override
	public int update(UserData data) {
		// Update score, coin, knight
		return repo.update(data);
	}

	@Override
	public int delete(UserData data) {
		return repo.delete(data);
	}


}
