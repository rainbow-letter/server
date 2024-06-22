package com.rainbowletter.server.image.controller;

import com.rainbowletter.server.common.validation.ImageFile;
import com.rainbowletter.server.image.controller.port.ImageService;
import com.rainbowletter.server.image.dto.ImageLoadResponse;
import com.rainbowletter.server.image.dto.ImageUploadResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageController {

	private final ImageService imageService;

	@GetMapping("/resources/{dirName}/{fileName}")
	public ResponseEntity<Resource> load(
			@PathVariable("dirName") final String dirName,
			@PathVariable("fileName") final String fileName
	) throws IOException {
		final ImageLoadResponse response = imageService.load(dirName, fileName);
		return ResponseEntity.ok().contentType(response.contentType()).body(response.content());
	}

	@PostMapping
	public ResponseEntity<ImageUploadResponse> upload(
			@RequestPart("file") @Valid @ImageFile final MultipartFile file
	) {
		final ImageUploadResponse response = imageService.upload(file);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@DeleteMapping("/{dirName}/{fileName}")
	public ResponseEntity<Void> delete(
			@PathVariable("dirName") final String dirName,
			@PathVariable("fileName") final String fileName
	) throws IOException {
		imageService.delete(dirName, fileName);
		return ResponseEntity.ok().build();
	}

}
