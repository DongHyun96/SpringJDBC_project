package data;

import jsonObj.CompanionData;

public interface CompDataRepository {
	
	//CRUD ( create, read, update, delete )

	CompanionData findOne(String userName);
	
	CompanionData save(CompanionData c);
	
	int update(CompanionData c);
	
	int delete(CompanionData c);
}
