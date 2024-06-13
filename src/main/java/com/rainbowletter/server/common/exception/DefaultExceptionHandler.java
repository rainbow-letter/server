package com.rainbowletter.server.common.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;

import com.rainbowletter.server.common.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DefaultExceptionHandler {

	@ExceptionHandler({IllegalArgumentException.class})
	public ResponseEntity<ErrorResponse> illegalArgument(
			final IllegalArgumentException exception,
			final HttpServletRequest request
	) {
		final String errorMessage = exception.getMessage();
		ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), errorMessage);
		return ResponseEntity
				.badRequest()
				.body(ErrorResponse.of(errorMessage, BAD_REQUEST));
	}

	@ExceptionHandler({RainbowLetterException.class})
	public ResponseEntity<ErrorResponse> rainbowLetter(
			final RainbowLetterException exception,
			final HttpServletRequest request
	) {
		final String errorMessage = exception.getMessage();
		if (Objects.isNull(exception.getResource())) {
			ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), errorMessage);
		} else {
			ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), errorMessage, exception.getResource());
		}
		return ResponseEntity
				.badRequest()
				.body(ErrorResponse.of(errorMessage, BAD_REQUEST));
	}

	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ResponseEntity<ErrorResponse> methodArgumentNotValid(
			final MethodArgumentNotValidException exception,
			final HttpServletRequest request
	) {
		final FieldError fieldError = exception.getBindingResult().getFieldErrors().get(0);
		final String field = "%s : %s".formatted(fieldError.getField(), fieldError.getRejectedValue());
		final String errorMessage = fieldError.getDefaultMessage();
		ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), errorMessage, field);
		return ResponseEntity
				.badRequest()
				.body(ErrorResponse.of(errorMessage, BAD_REQUEST));
	}

	@ExceptionHandler({HttpRequestMethodNotSupportedException.class})
	public ResponseEntity<ErrorResponse> methodNotSupported(
			final HttpRequestMethodNotSupportedException exception,
			final HttpServletRequest request
	) {
		final String requestURI = request.getRequestURI();
		final String errorMessage = "%s 해당 경로는 %s 메서드를 지원하지 않습니다.".formatted(requestURI, exception.getMethod());
		ExceptionLogger.warn(METHOD_NOT_ALLOWED, requestURI, errorMessage);
		return ResponseEntity
				.status(METHOD_NOT_ALLOWED.value())
				.body(ErrorResponse.of(errorMessage, METHOD_NOT_ALLOWED));
	}

	@ExceptionHandler({HttpMessageNotReadableException.class})
	public ResponseEntity<ErrorResponse> httpMessageNotReadable(
			final HttpMessageNotReadableException exception,
			final HttpServletRequest request
	) {
		ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), exception.getMessage());
		return ResponseEntity
				.badRequest()
				.body(ErrorResponse.of("유효하지 않은 요청 페이로드입니다.", BAD_REQUEST));
	}

	@ExceptionHandler({MethodArgumentTypeMismatchException.class, ConversionFailedException.class})
	public ResponseEntity<ErrorResponse> invalidPathValue(final Exception exception, final HttpServletRequest request) {
		ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), exception.getMessage());
		return ResponseEntity
				.badRequest()
				.body(ErrorResponse.of("요청 주소에 유효하지 않은 값이 있습니다.", BAD_REQUEST));
	}

	@ExceptionHandler({MissingServletRequestPartException.class})
	public ResponseEntity<ErrorResponse> missingServletRequestPart(
			final MissingServletRequestPartException exception,
			final HttpServletRequest request
	) {
		ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), exception.getMessage());
		return ResponseEntity
				.badRequest()
				.body(ErrorResponse.of("파일을 첨부해주세요.", BAD_REQUEST));
	}

	@ExceptionHandler({MultipartException.class, FileUploadException.class})
	public ResponseEntity<ErrorResponse> multipart(final Exception exception, final HttpServletRequest request) {
		ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), exception.getMessage());
		return ResponseEntity
				.badRequest()
				.body(ErrorResponse.of("Content-Type 또는 FormData 확인이 필요합니다.", BAD_REQUEST));
	}

	@ExceptionHandler({UsernameNotFoundException.class})
	public ResponseEntity<ErrorResponse> usernameNotFound(
			final UsernameNotFoundException exception,
			final HttpServletRequest request
	) {
		final String errorMessage = exception.getMessage();
		ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), errorMessage);
		return ResponseEntity
				.badRequest()
				.body(ErrorResponse.of(errorMessage, BAD_REQUEST));
	}

	@ExceptionHandler({AccountExpiredException.class})
	public ResponseEntity<ErrorResponse> accountExpired(
			final AccountExpiredException exception,
			final HttpServletRequest request
	) {
		ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), exception.getMessage());
		return ResponseEntity
				.badRequest()
				.body(ErrorResponse.of("계정이 휴면 상태입니다.", BAD_REQUEST));
	}

	@ExceptionHandler({LockedException.class})
	public ResponseEntity<ErrorResponse> locked(
			final LockedException exception,
			final HttpServletRequest request
	) {
		ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), exception.getMessage());
		return ResponseEntity
				.badRequest()
				.body(ErrorResponse.of("계정이 잠금 상태입니다.", BAD_REQUEST));
	}

	@ExceptionHandler({CredentialsExpiredException.class})
	public ResponseEntity<ErrorResponse> credentialsExpired(
			final CredentialsExpiredException exception,
			final HttpServletRequest request
	) {
		ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), exception.getMessage());
		return ResponseEntity
				.badRequest()
				.body(ErrorResponse.of("인증이 완료되지 않은 계정입니다.", BAD_REQUEST));
	}

	@ExceptionHandler({DisabledException.class})
	public ResponseEntity<ErrorResponse> disabled(
			final DisabledException exception,
			final HttpServletRequest request
	) {
		ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), exception.getMessage());
		return ResponseEntity
				.badRequest()
				.body(ErrorResponse.of("탈퇴된 계정입니다.", BAD_REQUEST));
	}

	@ExceptionHandler({BadCredentialsException.class})
	public ResponseEntity<ErrorResponse> badCredentials(
			final BadCredentialsException exception,
			final HttpServletRequest request
	) {
		ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), exception.getMessage());
		return ResponseEntity
				.badRequest()
				.body(ErrorResponse.of("아이디와 비밀번호를 확인 해주세요.", BAD_REQUEST));
	}

	@ExceptionHandler({Exception.class})
	public ResponseEntity<ErrorResponse> exception(final Exception exception, final HttpServletRequest request) {
		final String errorMessage = exception.getMessage();
		ExceptionLogger.error(INTERNAL_SERVER_ERROR, request.getRequestURI(), errorMessage, exception);
		return ResponseEntity
				.internalServerError()
				.body(ErrorResponse.of(errorMessage, INTERNAL_SERVER_ERROR));
	}

}
