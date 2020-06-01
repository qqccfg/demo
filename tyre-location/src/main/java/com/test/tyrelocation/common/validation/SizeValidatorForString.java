package com.test.tyrelocation.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Size;

/**
 * @Date: 2019/10/29 15:15
 * @Author: JackLei
 * @Description:
 * @Version:
 */
public class SizeValidatorForString implements ConstraintValidator<Size, String> {

    private int min;

    private int max;

    @Override
    public void initialize(Size constraintAnnotation) {
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        int length = value.length();
        System.out.println("大小："+length);
        return min<=length&&length<=max;
    }
}
