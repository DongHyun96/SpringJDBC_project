package project;

import java.util.List;

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
			if (findOne(cd.getUserName(), cd.getCompanion()) != null) {
				System.out.println("From ComDatManager: Insertion fails");
				return null;
			}
			CompanionData result = repo.save(cd);
			return result;
		}
		catch(Exception e) {
			System.out.println("From ComDatManager: Insertion fails");
			return null;
		}
	}
	
	@Override
	public CompanionData findOne(String name, String comp) {
		return repo.findOne(name, comp); // Return null if no Comp found.
	}
	
	@Override
	public List<CompanionData> findByName(String name) {
		return repo.findByName(name);
	}
	
	@Override
	public int update(CompanionData data) {
		if (findOne(data.getUserName(), data.getCompanion()) == null) {
			return 0;
		}
		return repo.update(data);
	}

	@Override
	public int deleteOne(String name, String comp) {
		return repo.deleteOne(name, comp);
	}
	
	@Override
	public int deleteByName(String name) {
		return repo.deleteByName(name);
	}

}
