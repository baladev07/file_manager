package com.filemanager.custom_annotations;

import com.filemanager.dto.SignUpRequestDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordValidator, SignUpRequestDTO> {
    @Override
    public boolean isValid(SignUpRequestDTO value, ConstraintValidatorContext context) {
        return value.getPassword().equals(value.getMatchingPassword());
    }
}