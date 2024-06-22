package com.rainbowletter.server.image.domain;

import com.rainbowletter.server.common.application.port.TimeHolder;
import com.rainbowletter.server.common.application.port.UuidHolder;
import com.rainbowletter.server.common.domain.AbstractFileManager;
import com.rainbowletter.server.common.exception.RainbowLetterException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class ImageFileManager extends AbstractFileManager {

	private static final String DEFAULT_IMAGE_DIR_NAME = "images";

	private final UuidHolder uuidHolder;
	private final TimeHolder timeHolder;

	public String save(final MultipartFile file) {
		final String fileName = createFileName(file);
		final String dirName = createDirName();
		final Path filePath = createAbsolutePath(DEFAULT_IMAGE_DIR_NAME, dirName, fileName);

		try (final InputStream inputStream = file.getInputStream()) {
			createBaseDir(DEFAULT_IMAGE_DIR_NAME, dirName);
			Files.copy(inputStream, filePath);
		} catch (final IOException exception) {
			throw new RainbowLetterException("파일 저장에 실패하였습니다.", exception.getMessage());
		}
		return "%s/%s".formatted(dirName, fileName);
	}

	private String createFileName(final MultipartFile file) {
		final String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
		return uuidHolder.generate() + "." + extension;
	}

	private String createDirName() {
		final LocalDateTime currentTime = timeHolder.currentTime();
		return currentTime.format(DateTimeFormatter.ofPattern("yyyyMM"));
	}

	public Path load(final String dirName, final String fileName) {
		final Path searchDirPath = createAbsolutePath(DEFAULT_IMAGE_DIR_NAME, dirName);
		final File[] files = searchFiles(searchDirPath, fileName);
		return getFirstFile(files).toPath();
	}

	public void delete(final String dirName, final String fileName) throws IOException {
		final Path searchDirPath = createAbsolutePath(DEFAULT_IMAGE_DIR_NAME, dirName);
		final File[] files = searchFiles(searchDirPath, fileName);
		final Path filePath = getFirstFile(files).toPath();
		Files.delete(filePath);
	}

}
