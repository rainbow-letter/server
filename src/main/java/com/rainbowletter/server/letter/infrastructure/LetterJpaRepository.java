package com.rainbowletter.server.letter.infrastructure;

import com.rainbowletter.server.letter.domain.Letter;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LetterJpaRepository extends JpaRepository<Letter, Long> {

	Optional<Letter> findByIdAndUserId(Long id, Long userId);

	List<Letter> findAllByPetId(Long petId);

	@Modifying
	@Query("DELETE FROM Letter letter WHERE letter.id IN ?1")
	void deleteAllWithIds(List<Long> ids);

}
