package br.com.springaccesscontrol.resource;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.springaccesscontrol.domain.User;
import br.com.springaccesscontrol.services.UserService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserResource {

	private UserService userService;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping
	public Page<User> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "20") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "id") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC") String directions){
		return userService.findAll(page, linesPerPage, orderBy, directions);
	}
	
	
}
