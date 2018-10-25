package com.trail.files;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.trail.files.masterRepository;

@Service

public class masterService {
	
	@Autowired
	private masterRepository masterrepo;
	
	public List<masterlist> getSOT()
	{
		List<masterlist> list = new ArrayList<>();
		masterrepo.findAll().forEach(list::add);
		
		return list;
	}
	
	public void addSOT(masterlist m)
	{
		masterrepo.save(m);
	}

}
