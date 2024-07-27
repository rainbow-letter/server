package com.rainbowletter.server.pet.infrastructure;

import static com.rainbowletter.server.letter.domain.QLetter.letter;
import static com.rainbowletter.server.pet.domain.QPet.pet;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rainbowletter.server.common.exception.RainbowLetterException;
import com.rainbowletter.server.pet.application.port.PetRepository;
import com.rainbowletter.server.pet.domain.Pet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PetRepositoryImpl implements PetRepository {

	private final JPAQueryFactory queryFactory;
	private final PetJpaRepository petJpaRepository;

	@Override
	public Pet save(final Pet pet) {
		return petJpaRepository.save(pet);
	}

	@Override
	public Pet findByIdAndUserIdOrElseThrow(final Long id, final Long userId) {
		return petJpaRepository.findByIdAndUserId(id, userId)
				.orElseThrow(() -> new RainbowLetterException(
						"반려동물 정보를 찾을 수 없습니다.", "id: [%d] userId: [%d]".formatted(id, userId)));
	}

	@Override
	public Pet findByLetterIdOrElseThrow(final Long letterId) {
		return Optional.ofNullable(
						queryFactory.selectFrom(pet)
								.join(letter).on(pet.id.eq(letter.petId))
								.where(letter.id.eq(letterId))
								.fetchOne()
				)
				.orElseThrow(() -> new RainbowLetterException("반려동물을 찾을 수 없습니다.", "letter: [%d]".formatted(letterId)));
	}

	@Override
	public Pet findByShareLinkOrElseThrow(final UUID shareLink) {
		return Optional.ofNullable(
						queryFactory.selectFrom(pet)
								.join(letter).on(pet.id.eq(letter.petId))
								.where(letter.shareLink.eq(shareLink))
								.fetchOne()
				)
				.orElseThrow(() -> new RainbowLetterException("반려동물을 찾을 수 없습니다.", "share: [%s]".formatted(shareLink)));
	}

	@Override
	public List<Pet> findAllByUserId(final Long userId) {
		return petJpaRepository.findAllByUserId(userId);
	}

	@Override
	public void delete(final Pet pet) {
		petJpaRepository.delete(pet);
	}

	@Override
	public void resetFavorite() {
		queryFactory.update(pet)
				.set(pet.favorite.canIncrease, true)
				.set(pet.favorite.dayIncreaseCount, 0)
				.execute();
	}

}
