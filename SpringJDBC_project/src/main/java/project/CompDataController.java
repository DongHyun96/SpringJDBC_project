package project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jsonObj.CompanionData;
import jsonObj.Flag;

@RestController
public class CompDataController {
	
	@Autowired
	private CompDataManager mgr;
	
	@PostMapping(value = "/insert/comp", produces= "application/json", consumes = "application/json")
	@ResponseBody
	public CompanionData insertCompanion(@RequestBody CompanionData cd) {
		
		System.out.println("Received insert companion request");
		return mgr.insert(cd);
	}
	
	@PostMapping(value = "/get/comp", produces= "application/json", consumes = "application/json")
	@ResponseBody
	public CompanionData getOne(@RequestBody CompanionData cd) {
		
		System.out.println("Received getOne request = " + cd.getUserName() + ", " + cd.getCompanion());
		return mgr.findOne(cd.getUserName(), cd.getCompanion());
	}
	
	@PostMapping(value = "/get/comp/byname", produces= "application/json", consumes = "application/json")
	@ResponseBody
	public List<CompanionData> getByUserName(@RequestBody CompanionData cd) {
		System.out.println("(getByUserName)Received Name: " + cd.getUserName());
		
		// No comp found -> return null
		return mgr.findByName(cd.getUserName());
	}
	
	
	@PostMapping(value = "/update/comp", produces= "application/json", consumes = "application/json")
	@ResponseBody
	public Flag updateCompData(@RequestBody CompanionData data) {
		
		System.out.println("Received new comp data: " + data.toString());
		
		Flag f = new Flag();
		
		f.setFlag(mgr.update(data));
		
		return f;
	}
	
	@PostMapping(value = "/delete/comp", produces= "application/json", consumes = "application/json")
	@ResponseBody
	public Flag	deleteCompData(@RequestBody CompanionData cd) {
		
		System.out.println("Received deleting compdata request");
		
		Flag f = new Flag();
		f.setFlag(mgr.deleteOne(cd.getUserName(), cd.getCompanion()));
		
		return f;
	}
	
	@PostMapping(value = "/delete/comp/byname", produces= "application/json", consumes = "application/json")
	@ResponseBody
	public Flag deleteByUserName(@RequestBody CompanionData cd) {
		
		System.out.println("Received deleting companion datas by name: " + cd.getUserName());
		
		Flag f = new Flag();
		f.setFlag(mgr.deleteByName(cd.getUserName()));
		
		return f;
	}

}
