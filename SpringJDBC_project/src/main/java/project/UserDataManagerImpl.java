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
	public UserData insert(String username, int score, int coin, String companion) {
		try {
		UserData player = repo.save(new UserData(username, score, coin, companion));
		return player;
		} catch (Exception e) { 
			// When username(primary key) is duplicated. 
			System.out.println("From PlayerManagerImpl: Insertion fails due to duplicated username");
			return null;
		}
	}

	@Override
	public UserData findOne(String userName) {
		return repo.findOne(userName);
	}
	
	// a custom service method that uses findAll()
	@Override
	public UserData findByPlayerName(String name) {
		for (UserData p : findAll()) {
			if (p.getUserName().equalsIgnoreCase(name)) {
				return p;
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
		// Update score, coin, companion
		return repo.update(data);
	}

	@Override
	public int delete(UserData data) {
		return repo.delete(data);
	}


}
