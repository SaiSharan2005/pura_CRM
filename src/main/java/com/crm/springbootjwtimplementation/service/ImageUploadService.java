// 1) Define a small ImageUploadService interface:
package com.crm.springbootjwtimplementation.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {
    /**
     * Uploads a single file to Cloudinary (or another store)  
     * and returns the publicly‐accessible URL.
     */
    String upload(MultipartFile file);
}
