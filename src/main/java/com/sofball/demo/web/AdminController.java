package com.sofball.demo.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "redirect:/admin/season";
    }
}
