package com.crm.springbootjwtimplementation.config;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = ObjectUtils.asMap(
                "cloud_name", "div9ovdhn",
                "api_key", "876176418586917",
                "api_secret", "6V5cqsjBf960eVPEI6VXFqkELBo");
        return new Cloudinary(config);
    }
}