package com.sofball.demo.validation;

import com.sofball.demo.model.Team;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TeamValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return Team.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Team team = (Team) o;

        if(StringUtils.isBlank(team.getName())) {
            errors.rejectValue("name", "NotBlank.team.name");
        }
        if(team.getDivision() == null) {
            errors.rejectValue("division", "NotNull.team.division");
        }
    }
}
