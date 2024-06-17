package com.rainbowletter.server.pet.infrastructure;

import com.rainbowletter.server.pet.domain.Pet;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetJpaRepository extends JpaRepository<Pet, Long> {

	Optional<Pet> findByIdAndUserId(Long id, Long userId);

	List<Pet> findAllByUserId(Long userId);

}
