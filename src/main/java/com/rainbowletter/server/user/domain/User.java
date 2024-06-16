package com.rainbowletter.server.user.domain;

import static lombok.AccessLevel.PROTECTED;

import com.rainbowletter.server.common.application.port.TimeHolder;
import com.rainbowletter.server.common.domain.TimeEntity;
import com.rainbowletter.server.common.exception.RainbowLetterException;
import com.rainbowletter.server.user.dto.UserChangePassword;
import com.rainbowletter.server.user.dto.UserChangePhoneNumber;
import com.rainbowletter.server.user.dto.UserCreate;
import com.rainbowletter.server.user.dto.UserResetPassword;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor(access = PROTECTED)
public class User extends AbstractAggregateRoot<User> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(length = 50, unique = true)
	private String email;

	@NotNull
	private String password;

	@Column(length = 20)
	private String phoneNumber;

	@NotNull
	@Enumerated(EnumType.STRING)
	private UserRole role;

	@NotNull
	@Enumerated(EnumType.STRING)
	private UserStatus status;

	@NotNull
	@Enumerated(EnumType.STRING)
	private OAuthProvider provider;

	@NotNull
	private String providerId;

	private LocalDateTime lastLoggedIn;

	@NotNull
	private LocalDateTime lastChangedPassword;

	@Embedded
	private TimeEntity timeEntity;

	public User(final UserCreate userCreate, final PasswordEncoder passwordEncoder, final TimeHolder timeHolder) {
		initialize(null, userCreate, passwordEncoder, null, timeHolder);
	}

	public User(
			final UserCreate userCreate,
			final PasswordEncoder passwordEncoder,
			final TimeHolder timeHolder,
			final UserValidator userValidator
	) {
		userValidator.validateEmail(userCreate.email());
		initialize(null, userCreate, passwordEncoder, null, timeHolder);
	}

	@Builder
	public User(
			final Long id,
			final UserCreate userCreate,
			final PasswordEncoder passwordEncoder,
			final LocalDateTime lastLoggedIn,
			final TimeHolder timeHolder
	) {
		initialize(id, userCreate, passwordEncoder, lastLoggedIn, timeHolder);
	}

	private void initialize(
			final Long id,
			final UserCreate userCreate,
			final PasswordEncoder passwordEncoder,
			final LocalDateTime lastLoggedIn,
			final TimeHolder timeHolder
	) {
		final LocalDateTime currentTime = timeHolder.currentTime();
		this.id = id;
		this.email = userCreate.email();
		this.password = passwordEncoder.encode(userCreate.password());
		this.phoneNumber = userCreate.phoneNumber();
		this.role = userCreate.role();
		this.status = userCreate.status();
		this.provider = userCreate.provider();
		this.providerId = userCreate.providerId();
		this.lastLoggedIn = lastLoggedIn;
		this.lastChangedPassword = currentTime;
		this.timeEntity = new TimeEntity(timeHolder);
	}

	public boolean isStatusMismatch(final UserStatus status) {
		return this.status != status;
	}

	public void login(final OAuthProvider provider, final String providerId, final TimeHolder timeHolder) {
		this.provider = provider;
		this.providerId = providerId;
		login(timeHolder);
	}

	public void login(final TimeHolder timeHolder) {
		this.lastLoggedIn = timeHolder.currentTime();
	}

	public void findPassword() {
		registerEvent(new UserFindPasswordEmailEvent(this));
	}

	public void changePassword(
			final UserChangePassword userChangePassword,
			final PasswordEncoder passwordEncoder,
			final TimeHolder timeHolder
	) {
		if (!passwordEncoder.matches(userChangePassword.password(), this.password)) {
			throw new RainbowLetterException("비밀번호가 일치하지 않습니다.");
		}
		updatePassword(userChangePassword.newPassword(), passwordEncoder, timeHolder);
	}

	public void resetPassword(
			final UserResetPassword userResetPassword,
			final PasswordEncoder passwordEncoder,
			final TimeHolder timeHolder
	) {
		updatePassword(userResetPassword.newPassword(), passwordEncoder, timeHolder);
	}

	private void updatePassword(
			final String password,
			final PasswordEncoder passwordEncoder,
			final TimeHolder timeHolder
	) {
		this.password = passwordEncoder.encode(password);
		this.lastChangedPassword = timeHolder.currentTime();
	}

	public void changePhoneNumber(
			final UserChangePhoneNumber userChangePhoneNumber,
			final UserValidator userValidator
	) {
		userValidator.validatePhoneNumber(userChangePhoneNumber.phoneNumber());
		updatePhoneNumber(userChangePhoneNumber.phoneNumber());
	}

	public void deletePhoneNumber() {
		updatePhoneNumber(null);
	}

	private void updatePhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void leave() {
		this.status = UserStatus.LEAVE;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof final User user)) {
			return false;
		}
		return Objects.equals(id, user.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

}
