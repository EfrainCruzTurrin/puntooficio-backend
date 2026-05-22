package com.puntooficio.puntooficio.shared.upload;

import com.puntooficio.puntooficio.shared.exceptions.BadRequestException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

@Service
public class FileStorageService {

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png", "webp");

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${server.base-url:http://localhost:8080}")  // ← agregar
    private String baseUrl;

    private Path uploadPath;

    @PostConstruct
    public void init() {
        try {
            this.uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(this.uploadPath);
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo inicializar el directorio de uploads");
        }
    }

    public String store(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("El archivo está vacío");
        }
        String original = file.getOriginalFilename();
        if (original == null || !original.contains(".")) {
            throw new BadRequestException("Nombre de archivo inválido");
        }
        String ext = original.substring(original.lastIndexOf('.') + 1).toLowerCase();
        if (!ALLOWED_EXTENSIONS.contains(ext)) {
            throw new BadRequestException("Formato no permitido. Use JPG, PNG o WEBP");
        }

        String filename = UUID.randomUUID() + "." + ext;
        Path target = uploadPath.resolve(filename);
        try {
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new BadRequestException("No se pudo guardar el archivo");
        }
        return baseUrl + "/uploads/" + filename;
    }
}
