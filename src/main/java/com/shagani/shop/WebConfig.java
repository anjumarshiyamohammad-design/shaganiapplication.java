package com.shagani.shop;

import com.shagani.shop.storage.ImageStorage;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

/** Serves uploaded product photos at /product-images/{file}. */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ImageStorage storage;

    public WebConfig(ImageStorage storage) {
        this.storage = storage;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/product-images/**")
                .addResourceLocations(storage.getDir().toUri().toString())
                // every upload gets a new file name, so long caching is safe
                .setCacheControl(CacheControl.maxAge(30, TimeUnit.DAYS).cachePublic());
    }
}
