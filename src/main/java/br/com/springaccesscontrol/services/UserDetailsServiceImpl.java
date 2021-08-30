package br.com.springaccesscontrol.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.springaccesscontrol.domain.User;
import br.com.springaccesscontrol.repository.UserRepository;
import br.com.springaccesscontrol.security.CustomUserDetails;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByUsername(username);
		
		if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }
         
        return new CustomUserDetails(user);
		
	}

}
