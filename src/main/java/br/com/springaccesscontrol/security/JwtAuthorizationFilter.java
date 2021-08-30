package br.com.springaccesscontrol.security;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;

import br.com.springaccesscontrol.domain.User;
import br.com.springaccesscontrol.repository.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
	
	private UserRepository userRepository;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String header = request.getHeader(JwtProperties.HEADER_STRING);

        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        Authentication authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
	}

	private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token = request.getHeader(JwtProperties.HEADER_STRING)
                .replace(JwtProperties.TOKEN_PREFIX,"");

        if (token != null) {
            String username = JWT.require(HMAC512(JwtProperties.SECRET.getBytes()))
                    .build()
                    .verify(token)
                    .getSubject();

            if (username != null) {
                User user = userRepository.findByUsername(username);
                CustomUserDetails userDetails = new CustomUserDetails(user);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
                return auth;
            }
            return null;
        }
        return null;
    }
	
}