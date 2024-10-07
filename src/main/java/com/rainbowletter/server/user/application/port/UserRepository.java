package com.rainbowletter.server.user.application.port;

import com.rainbowletter.server.user.domain.User;
import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository {

	User save(User user);

	boolean existsByEmail(String email);

	boolean existsLeaveByEmail(String email);

	boolean existsByPhoneNumber(String phoneNumber);

	User findByIdOrElseThrow(Long id);

	User findByEmailOrElseThrow(String email);

	List<User> findAllLeaveByBeforeDate(LocalDateTime beforeDate);

	void deleteAll(List<User> users);

}
