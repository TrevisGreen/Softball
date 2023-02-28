package com.sofball.demo.web;


import com.sofball.demo.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController extends BaseController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        log.debug("Showing login page");
        User user = new User();
        model.addAttribute("user", user);
        return "/login/login";
    }
}
