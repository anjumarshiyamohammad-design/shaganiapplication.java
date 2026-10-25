package com.shagani.shop.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** Friendly message when a photo is bigger than the allowed size. */
@ControllerAdvice
public class UploadErrors {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String tooBig(RedirectAttributes ra) {
        ra.addFlashAttribute("error", "That photo is too large. Please use one under 5 MB.");
        return "redirect:/admin/products";
    }
}
