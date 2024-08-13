package com.rainbowletter.server.user.application.port;

import com.rainbowletter.server.user.domain.User;

public interface UserRepository {

	User save(User user);

	boolean existsByEmail(String email);

	boolean existsLeaveByEmail(String email);

	boolean existsByPhoneNumber(String phoneNumber);

	User findByIdOrElseThrow(Long id);

	User findByEmailOrElseThrow(String email);

}
