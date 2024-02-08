package com.gstagram.gstagram.util.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UrlPatternValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUrl {
    String message() default "Invalid URL pattern";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}