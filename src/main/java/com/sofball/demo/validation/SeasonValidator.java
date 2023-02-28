package com.sofball.demo.validation;


import com.sofball.demo.model.Season;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SeasonValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return Season.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Season season = (Season) o;

        if(season.getId() == null) {
            errors.rejectValue("id", "NotNull.season.id");
        }
    }
}
