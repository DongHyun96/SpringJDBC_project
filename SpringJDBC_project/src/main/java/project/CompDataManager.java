package project;

import jsonObj.CompanionData;

public interface CompDataManager {
	
	CompanionData insert(CompanionData cd);
	
	CompanionData findByUserName(String name);
	
	int update(CompanionData data);
	
	int delete(CompanionData data);
}
