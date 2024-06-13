package com.rainbowletter.server.user.application;

import com.rainbowletter.server.common.application.port.TimeHolder;
import com.rainbowletter.server.common.dto.TokenResponse;
import com.rainbowletter.server.user.application.port.NativeLoginHandler;
import com.rainbowletter.server.user.application.port.UserRepository;
import com.rainbowletter.server.user.controller.port.UserService;
import com.rainbowletter.server.user.domain.User;
import com.rainbowletter.server.user.domain.UserValidator;
import com.rainbowletter.server.user.dto.UserChangePassword;
import com.rainbowletter.server.user.dto.UserChangePhoneNumber;
import com.rainbowletter.server.user.dto.UserCreate;
import com.rainbowletter.server.user.dto.UserFindPassword;
import com.rainbowletter.server.user.dto.UserInformationResponse;
import com.rainbowletter.server.user.dto.UserLogin;
import com.rainbowletter.server.user.dto.UserResetPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	private final TimeHolder timeHolder;
	private final UserValidator userValidator;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final NativeLoginHandler nativeLoginHandler;

	@Override
	public UserInformationResponse information(final String email) {
		final User user = userRepository.findByEmailOrElseThrow(email);
		return UserInformationResponse.from(user);
	}

	@Override
	@Transactional
	public Long create(final UserCreate userCreate) {
		final User user = new User(userCreate, passwordEncoder, timeHolder, userValidator);
		userRepository.save(user);
		return user.getId();
	}

	@Override
	@Transactional
	public TokenResponse login(final UserLogin userLogin) {
		return nativeLoginHandler.process(userLogin.email(), userLogin.password());
	}

	@Override
	public void findPassword(final UserFindPassword userFindPassword) {
		final User user = userRepository.findByEmailOrElseThrow(userFindPassword.email());
		user.findPassword();
		userRepository.save(user);
	}

	@Override
	@Transactional
	public void resetPassword(final String email, final UserResetPassword userResetPassword) {
		final User user = userRepository.findByEmailOrElseThrow(email);
		user.resetPassword(userResetPassword, passwordEncoder, timeHolder);
		userRepository.save(user);
	}

	@Override
	@Transactional
	public void changePassword(final String email, final UserChangePassword userChangePassword) {
		final User user = userRepository.findByEmailOrElseThrow(email);
		user.changePassword(userChangePassword, passwordEncoder, timeHolder);
		userRepository.save(user);
	}

	@Override
	@Transactional
	public void changePhoneNumber(final String email, final UserChangePhoneNumber userChangePhoneNumber) {
		final User user = userRepository.findByEmailOrElseThrow(email);
		user.changePhoneNumber(userChangePhoneNumber, userValidator);
		userRepository.save(user);
	}

	@Override
	@Transactional
	public void deletePhoneNumber(final String email) {
		final User user = userRepository.findByEmailOrElseThrow(email);
		user.deletePhoneNumber();
		userRepository.save(user);
	}

	@Override
	@Transactional
	public void leave(final String email) {
		final User user = userRepository.findByEmailOrElseThrow(email);
		user.leave();
		userRepository.save(user);
	}

}
