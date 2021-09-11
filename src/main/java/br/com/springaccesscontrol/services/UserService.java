package br.com.springaccesscontrol.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.springaccesscontrol.domain.User;
import br.com.springaccesscontrol.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
		
	public Page<User> findAll(Integer page, Integer linesPerPage, String orderBy, String directions) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(directions), orderBy);
		return userRepository.findAll(pageRequest);
	}
	
}
