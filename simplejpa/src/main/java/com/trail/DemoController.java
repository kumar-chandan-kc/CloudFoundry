package com.trail;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trail.files.masterService;
import com.trail.files.masterlist;

@RestController
public class DemoController {

	@Autowired
	private masterService ms;
	
	@RequestMapping("/sot")
	public List<masterlist> getSOT()
	{	
		return ms.getSOT();
	}
	
	
	@RequestMapping("/testOdataMetadata")
	public String testOdata()
	{	
		return "working";
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/sot")
	public void addSOT(@RequestBody masterlist m)
	{	
		ms.addSOT(m);
	}
	
	
}
