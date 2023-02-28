package com.sofball.demo.validation;

import com.sofball.demo.model.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CoachValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return User.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        if(StringUtils.isBlank(user.getUsername())) {
            errors.rejectValue("username", "NotBlank.user.username");
        }
        if(StringUtils.isBlank(user.getFirstName())) {
            errors.rejectValue("firstName", "NotBlank.user.firstName");
        }
        if(StringUtils.isBlank(user.getLastName())) {
            errors.rejectValue("lastName", "NotBlank.user.lastName");
        }

        if(!user.getAdmin()) {
            user.setCoach(Boolean.TRUE);
            if(user.getTeam() == null) {
                errors.rejectValue("team", "NotNull.user.team");
            }
        }
    }
}
