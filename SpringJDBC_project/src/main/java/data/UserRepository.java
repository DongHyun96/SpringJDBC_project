package data;

import java.util.List;

public interface UserRepository {
	//CRUD ( create, read, update, delete )

		User findOne(long id);

		User save(User user);
		
		List<User> findAll();
		
		int update(User u);
		
		int delete(User u);
}
