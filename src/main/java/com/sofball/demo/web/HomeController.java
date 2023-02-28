package com.sofball.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomeController extends BaseController {

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        log.debug("Home");
        return "/home/index";
    }
}
