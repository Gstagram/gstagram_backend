package com.gstagram.gstagram.util.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class UrlPatternValidator implements ConstraintValidator<ValidUrl, List<String>> {

    @Override
    public void initialize(ValidUrl constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<String> urls, ConstraintValidatorContext context) {
        if (urls == null || urls.isEmpty()) {
            return true; // 또는 false, 필요에 따라
        }
        // 모든 URL이 유효한지 확인
        return urls.stream().allMatch(url -> url.matches("http(s)?://.+"));
    }
}
