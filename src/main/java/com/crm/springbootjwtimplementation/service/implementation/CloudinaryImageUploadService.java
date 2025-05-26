// 2) Implement it once, injecting your Cloudinary bean:
package com.crm.springbootjwtimplementation.service.implementation;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.crm.springbootjwtimplementation.exceptions.security.CustomSecurityException;
import com.crm.springbootjwtimplementation.service.ImageUploadService;
import com.crm.springbootjwtimplementation.util.Constants.ApiMessages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryImageUploadService implements ImageUploadService {
  
    private final Cloudinary cloudinary;

    @Autowired
    public CloudinaryImageUploadService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String upload(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new CustomSecurityException(
                ApiMessages.INVALID_INPUT_DATA,
                HttpStatus.BAD_REQUEST);
        }

        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> result = cloudinary
                .uploader()
                .upload(file.getBytes(), ObjectUtils.emptyMap());

            return (String) result.get("secure_url");
        } catch (IOException e) {
            throw new CustomSecurityException(
                ApiMessages.IMAGE_UPLOAD_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
