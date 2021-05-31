package project;

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
	
	@PostMapping(value = "/get/comp", produces= "application/json", consumes = "application/json")
	@ResponseBody
	public CompanionData getCompByUserName(@RequestBody String name) {
		System.out.println("(getCompByUserName)Received Name: " + name);
		CompanionData result = mgr.findByUserName(name);
		
		// No comp found -> return null
		return result;
	}
	
	@PostMapping(value = "/update/comp", produces= "application/json", consumes = "application/json")
	@ResponseBody
	public Flag updateCompData(@RequestBody CompanionData data) {
		
		System.out.println("Received new comp data: " + data.toString());
		
		Flag f = new Flag();
		
		f.setFlag(mgr.update(data));
		
		return f;
	}

}
