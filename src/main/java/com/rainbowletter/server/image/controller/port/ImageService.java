package com.rainbowletter.server.image.controller.port;

import com.rainbowletter.server.image.dto.ImageLoadResponse;
import com.rainbowletter.server.image.dto.ImageUploadResponse;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

	ImageLoadResponse load(String dirName, String fileName) throws IOException;

	ImageUploadResponse upload(MultipartFile file);

	void delete(String dirName, String fileName) throws IOException;

}
