package com.rainbowletter.server.letter.infrastructure;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rainbowletter.server.common.exception.RainbowLetterException;
import com.rainbowletter.server.letter.application.port.LetterRepository;
import com.rainbowletter.server.letter.domain.Letter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LetterRepositoryImpl implements LetterRepository {

	private final JPAQueryFactory queryFactory;
	private final LetterJpaRepository letterJpaRepository;

	@Override
	public Letter findByIdAndUserIdOrElseThrow(final Long id, final Long userId) {
		return letterJpaRepository.findByIdAndUserId(id, userId)
				.orElseThrow(() -> new RainbowLetterException(
						"편지를 찾을 수 없습니다.", "id: [%d] userId: [%d]".formatted(id, userId)));
	}

	@Override
	public List<Letter> findAllByPetId(final Long petId) {
		return letterJpaRepository.findAllByPetId(petId);
	}

	@Override
	public Letter save(final Letter letter) {
		return letterJpaRepository.save(letter);
	}

	@Override
	public void delete(final Letter letter) {
		letterJpaRepository.delete(letter);
	}

	@Override
	public void deleteAll(final List<Letter> letters) {
		final List<Long> ids = letters.stream()
				.map(Letter::getId)
				.toList();
		letterJpaRepository.deleteAllWithIds(ids);
	}

}
