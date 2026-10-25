package com.shagani.shop.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/** Saves the product photos the owner uploads. Files live in {upload-dir}/products. */
@Service
public class ImageStorage {

    private final Path dir;

    public ImageStorage(@Value("${shagani.upload-dir}") String uploadDir) throws IOException {
        this.dir = Path.of(uploadDir, "products").toAbsolutePath().normalize();
        Files.createDirectories(dir);
    }

    public Path getDir() {
        return dir;
    }

    /**
     * Checks the file really is a JPEG, PNG or WebP picture and saves it under a name we choose
     * (the name the user's device sent is never used).
     *
     * @return the saved file name
     * @throws IllegalArgumentException with a message that is safe to show to the owner
     */
    public String save(String slug, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Please choose a photo first.");
        }
        byte[] head = file.getInputStream().readNBytes(12);
        String ext = detectExtension(head);
        if (ext == null) {
            throw new IllegalArgumentException("Only JPG, PNG or WebP photos are allowed.");
        }
        String name = slug + "-" + System.currentTimeMillis() + "." + ext;
        file.transferTo(dir.resolve(name));
        return name;
    }

    public void delete(String fileName) {
        if (fileName == null || fileName.isBlank()) return;
        Path target = dir.resolve(fileName).normalize();
        if (!target.startsWith(dir)) return; // safety: never leave the uploads folder
        try {
            Files.deleteIfExists(target);
        } catch (IOException ignored) {
            // an old file we could not remove is harmless
        }
    }

    private static String detectExtension(byte[] b) {
        if (b.length >= 3 && (b[0] & 0xFF) == 0xFF && (b[1] & 0xFF) == 0xD8 && (b[2] & 0xFF) == 0xFF) {
            return "jpg";
        }
        if (b.length >= 4 && (b[0] & 0xFF) == 0x89 && b[1] == 'P' && b[2] == 'N' && b[3] == 'G') {
            return "png";
        }
        if (b.length >= 12
                && new String(b, 0, 4, StandardCharsets.US_ASCII).equals("RIFF")
                && new String(b, 8, 4, StandardCharsets.US_ASCII).equals("WEBP")) {
            return "webp";
        }
        return null;
    }
}
