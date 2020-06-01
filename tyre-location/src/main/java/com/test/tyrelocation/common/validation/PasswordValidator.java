package com.test.tyrelocation.common.validation;

import com.test.tyrelocation.common.validation.constraints.Password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Date: 2019/11/19 22:12
 * @Author: JackLei
 * @Description:
 * @Version:
 */
public class PasswordValidator implements ConstraintValidator<Password, String> {


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String regex = "^(?=.*[a-zA-Z])(?=.*[1-9])(?=.*[!|@|#|$|%|^|&|*])(?!' '+$).{8,14}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(value);
        boolean isMatch = m.matches();
        return isMatch;
    }
}
