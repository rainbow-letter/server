package com.rainbowletter.server.user.controller;

import com.rainbowletter.server.common.dto.TokenResponse;
import com.rainbowletter.server.common.infrastructure.SecurityUtils;
import com.rainbowletter.server.user.controller.port.UserService;
import com.rainbowletter.server.user.dto.UserChangePasswordRequest;
import com.rainbowletter.server.user.dto.UserChangePhoneNumberRequest;
import com.rainbowletter.server.user.dto.UserCreateRequest;
import com.rainbowletter.server.user.dto.UserFindPasswordRequest;
import com.rainbowletter.server.user.dto.UserInformationResponse;
import com.rainbowletter.server.user.dto.UserLoginRequest;
import com.rainbowletter.server.user.dto.UserResetPasswordRequest;
import com.rainbowletter.server.user.dto.UserVerifyResponse;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	@GetMapping("/info")
	public ResponseEntity<UserInformationResponse> information() {
		final String email = SecurityUtils.getEmail();
		final UserInformationResponse response = userService.information(email);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/verify")
	public ResponseEntity<UserVerifyResponse> verify() {
		final String email = SecurityUtils.getEmail();
		final String role = SecurityUtils.getRole();
		return ResponseEntity.ok(UserVerifyResponse.of(email, role));
	}

	@PostMapping("/create")
	public ResponseEntity<Void> create(@RequestBody @Valid final UserCreateRequest request) {
		final Long id = userService.create(request.toDomainDto());
		return ResponseEntity.created(URI.create(id.toString())).build();
	}

	@PostMapping("/login")
	public ResponseEntity<TokenResponse> login(@RequestBody @Valid final UserLoginRequest request) {
		final TokenResponse userToken = userService.login(request.toDomainDto());
		return ResponseEntity.ok(userToken);
	}

	@PostMapping("/find-password")
	public ResponseEntity<Void> findPassword(@RequestBody @Valid final UserFindPasswordRequest request) {
		userService.findPassword(request.toDomainDto());
		return ResponseEntity.ok().build();
	}

	@PutMapping("/reset-password")
	public ResponseEntity<Void> resetPassword(@RequestBody @Valid final UserResetPasswordRequest request) {
		final String email = SecurityUtils.getEmail();
		userService.resetPassword(email, request.toDomainDto());
		return ResponseEntity.ok().build();
	}

	@PutMapping("/password")
	public ResponseEntity<Void> changePassword(@RequestBody @Valid final UserChangePasswordRequest request) {
		final String email = SecurityUtils.getEmail();
		userService.changePassword(email, request.toDomainDto());
		return ResponseEntity.ok().build();
	}

	@PutMapping("/phone-number")
	public ResponseEntity<Void> changePhoneNumber(@RequestBody @Valid final UserChangePhoneNumberRequest request) {
		final String email = SecurityUtils.getEmail();
		userService.changePhoneNumber(email, request.toDomainDto());
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/phone-number")
	public ResponseEntity<Void> deletePhoneNumber() {
		final String email = SecurityUtils.getEmail();
		userService.deletePhoneNumber(email);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping
	public ResponseEntity<Void> leave() {
		final String email = SecurityUtils.getEmail();
		userService.leave(email);
		return ResponseEntity.ok().build();
	}

}
