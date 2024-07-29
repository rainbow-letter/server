package com.rainbowletter.server.data.controller;

import com.rainbowletter.server.data.controller.port.DataService;
import com.rainbowletter.server.data.dto.DataCreateRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/data")
public class DataController {

	private final DataService dataService;
	private final UserAgentAnalyzer userAgentAnalyzer;

	@PostMapping
	public ResponseEntity<Void> create(
			final HttpServletRequest request,
			@RequestBody @Valid final DataCreateRequest createRequest
	) {
		final String userAgent = request.getHeader("USER-AGENT");
		final UserAgent agent = userAgentAnalyzer.parse(userAgent);
		dataService.create(createRequest.toDomainDto(agent));
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
