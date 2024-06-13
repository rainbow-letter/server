package com.rainbowletter.server.user.application;

import com.rainbowletter.server.common.application.port.TimeHolder;
import com.rainbowletter.server.common.exception.RainbowLetterException;
import com.rainbowletter.server.user.application.port.UserRepository;
import com.rainbowletter.server.user.domain.NativeUserDetails;
import com.rainbowletter.server.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NativeUserService implements UserDetailsService {

	private final TimeHolder timeHolder;
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		try {
			final User user = userRepository.findByEmailOrElseThrow(username);
			user.login(timeHolder);
			userRepository.save(user);
			return new NativeUserDetails(user);
		} catch (final RainbowLetterException exception) {
			throw new UsernameNotFoundException(exception.getMessage());
		}
	}

}
