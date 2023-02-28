package com.sofball.demo.validation;

import com.sofball.demo.model.Division;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DivisionValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return Division.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Division division = (Division) o;
        if(StringUtils.isBlank(division.getName())) {
            errors.rejectValue("name", "NotBlank.division.name");
        }
    }
}
