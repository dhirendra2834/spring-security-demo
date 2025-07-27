package com.example.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

	List<DemoModel> list= new ArrayList<>();
	
	@Autowired
	DemoRepository demoRepository;
	

	public List<DemoModel> getAllUsers(){
		return  demoRepository.findAll();
	}
	
	public Optional<DemoModel> getUserById(Integer id) {
		return demoRepository.findById(id);
	}
	
	public DemoModel addUser(DemoModel demoModel) {
		return demoRepository.save(demoModel);
	}
	
}
