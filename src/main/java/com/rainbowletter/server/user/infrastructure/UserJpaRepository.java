package com.rainbowletter.server.user.infrastructure;

import com.rainbowletter.server.user.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserJpaRepository extends JpaRepository<User, Long> {

	boolean existsByEmail(String email);

	boolean existsByPhoneNumber(String phoneNumber);

	Optional<User> findByEmail(String email);

	@Modifying
	@Query("DELETE FROM User user WHERE user.id IN ?1")
	void deleteAllWithIds(List<Long> ids);

}
