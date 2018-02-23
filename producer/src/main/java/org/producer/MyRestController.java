package org.producer;

import org.pojo.GetUserDetailsRequest;
import org.pojo.Module;
import org.pojo.UserPrivilege;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRestController {

	@RequestMapping("/user/details")
	public UserPrivilege getUserDetails(
			@RequestBody GetUserDetailsRequest userInfo
			) { 
		
		System.out.println(userInfo.getName());
		
		System.out.println(userInfo.getPassword());
		
		if(userInfo.getName().equals(userInfo.getPassword())) {
			
			UserPrivilege details1 = new UserPrivilege();
			details1.setUserId(userInfo.getName()+"[login success]");
			
					Module module1 = new Module();
					module1.setCode("M01");
					details1.getModuleAccessCodes().add(module1);
					
					Module module2 = new Module();
					module2.setCode("M22");
					details1.getModuleAccessCodes().add(module2);
					
			return details1;
			
		}
		
		
		return null;
		
	}
}
