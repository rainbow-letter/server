package com.rainbowletter.server.image.application;

import com.rainbowletter.server.image.controller.port.ImageService;
import com.rainbowletter.server.image.domain.ImageFileManager;
import com.rainbowletter.server.image.dto.ImageLoadResponse;
import com.rainbowletter.server.image.dto.ImageUploadResponse;
import java.io.IOException;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

	private final ImageFileManager imageFileManager;

	@Override
	public ImageLoadResponse load(final String dirName, final String fileName) throws IOException {
		final Path path = imageFileManager.load(dirName, fileName);
		final MediaType mediaType = MediaType.parseMediaType(new Tika().detect(path));
		final Resource resource = new FileSystemResource(path);
		return ImageLoadResponse.of(mediaType, resource);
	}

	@Override
	public ImageUploadResponse upload(final MultipartFile file) {
		final String objectKey = imageFileManager.save(file);
		return ImageUploadResponse.from(objectKey);
	}

	@Override
	public void delete(final String dirName, final String fileName) throws IOException {
		imageFileManager.delete(dirName, fileName);
	}

}
