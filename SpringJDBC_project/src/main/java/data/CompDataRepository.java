package data;

import java.util.List;

import jsonObj.CompanionData;

public interface CompDataRepository {
	
	//CRUD ( create, read, update, delete )

	CompanionData findOne(String userName, String comp);
	
	List<CompanionData> findByName(String userName);
	
	CompanionData save(CompanionData c);
	
	int update(CompanionData c);
	
	int deleteOne(String userName, String comp);
	
	int deleteByName(String userName);
}
