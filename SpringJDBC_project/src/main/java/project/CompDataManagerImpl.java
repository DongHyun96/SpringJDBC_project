package project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.CompDataRepository;
import jsonObj.CompanionData;

@Service
public class CompDataManagerImpl implements CompDataManager {
	
	@Autowired
	private CompDataRepository repo;

	@Override
	public CompanionData insert(CompanionData cd) {
		try {
			CompanionData result = repo.save(cd);
			return result;
		}
		catch(Exception e) {
			System.out.println("From ComDatManager: Insertion fails");
			return null;
		}
	}

	@Override
	public CompanionData findByUserName(String name) {
		return repo.findOne(name); // Return null if no Comp found.
	}

	@Override
	public int update(CompanionData data) {
		if (findByUserName(data.getUserName()) == null) {
			return 0;
		}
		return repo.update(data);
	}

	@Override
	public int delete(CompanionData data) {
		return repo.delete(data);
	}

}
