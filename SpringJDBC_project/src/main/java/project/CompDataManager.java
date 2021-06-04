package project;

import java.util.List;

import jsonObj.CompanionData;

public interface CompDataManager {
	
	CompanionData insert(CompanionData cd);
	
	CompanionData findOne(String name, String comp);
	
	List<CompanionData> findByName(String name);
	
	int update(CompanionData data);
	
	int deleteOne(String name, String comp);
	
	int deleteByName(String name);
}
