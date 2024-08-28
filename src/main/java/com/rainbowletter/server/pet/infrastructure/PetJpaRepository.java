package com.rainbowletter.server.pet.infrastructure;

import com.rainbowletter.server.pet.domain.Pet;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PetJpaRepository extends JpaRepository<Pet, Long> {

	Optional<Pet> findByIdAndUserId(Long id, Long userId);

	List<Pet> findAllByUserId(Long userId);

	@Modifying
	@Query("DELETE FROM Pet pet WHERE pet.id IN ?1")
	void deleteAllWithIds(List<Long> ids);

}
