package com.sofball.demo.web;


import com.sofball.demo.model.User;
import com.sofball.demo.service.TeamService;
import com.sofball.demo.service.UserService;
import com.sofball.demo.utils.Constants;
import com.sofball.demo.validation.CoachValidator;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/admin/coach")
public class CoachController extends BaseController {

    @Autowired
    private UserService service;

    @Autowired
    private TeamService teamService;

    @Autowired
    private CoachValidator validator;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "redirect:/admin/coach/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model,
           @RequestParam(value = "page", required = false) Integer page,
           @RequestParam(value = "sort", required = false) String sort,
           @RequestParam(value = "direction", required = false) String direction,
           @RequestParam(value = "filter", required = false) String filter) {
        log.debug("Page: {}", page);

        if(StringUtils.isBlank(sort)) {
            sort = "lastName";
            direction = "desc";
        }

        Map<String, Object> params = buildPagination(page, sort, direction, filter);

        Page<User> p;
        PageRequest pageRequest = (PageRequest) params.get(Constants.PAGE_REQUEST);
        if(StringUtils.isNotBlank(filter)) {
            p = service.search(filter, pageRequest);
        } else {
            p = service.list(pageRequest);
        }

        paginate(model, p, params);
        model.addAttribute("users", p);
        return "/admin/coach/list";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String create(Model model) {
        User coach = new User();
        model.addAttribute("coach", coach);
        model.addAttribute("teams", teamService.all());
        return "/admin/coach/new";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String create(@ModelAttribute("coach") User coach, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        validator.validate(coach, bindingResult);
        if(bindingResult.hasErrors()) {
            log.warn("Could not add coach. {}", bindingResult.getAllErrors());
            model.addAttribute("coach", teamService.all());
            return "/admin/coach/new";
        }

        try {
            coach = service.create(coach);
            return "redirect:/admin/coach/show" + coach.getId();
        } catch (Exception e) {
            log.error("Could not add coach.", e);
            bindingResult.reject("Could not add coach. {}", e.getMessage());
            model.addAttribute("teams", teamService.all());
            return "/admin/coach/new";
        }
    }

    @RequestMapping(value = "/show/{coachId}", method = RequestMethod.GET)
    public String show(@PathVariable("coachId") Long coachId, Model model) {
        User coach = service.get(coachId);
        model.addAttribute("coach", coach);
        return "/admin/coach/show";
    }

    @RequestMapping(value = "/delete/{coachId}", method = RequestMethod.GET)
    public String delete(@PathVariable("coachId") Long coachId, RedirectAttributes redirectAttributes) {
        try {
            String name = service.delete(coachId);
            redirectAttributes.addFlashAttribute("message", "Coach " + name + " deleted successfully.");
        } catch (Exception e) {
            log.error("Could not delete coach " + coachId + ".", e);
            redirectAttributes.addFlashAttribute("message", "Coach " + coachId + " could not be deleted.");
            redirectAttributes.addFlashAttribute("messageStyle", "alert-danger");
        }
        return "redirect:/admin/coach/list";
    }

    @RequestMapping(value = "/edit/{coachId}", method = RequestMethod.GET)
    public String update(Model model, @PathVariable("coachId") Long coachId) {
        User coach = service.get(coachId);
        model.addAttribute("coach", coach);
        model.addAttribute("teams", teamService.all());
        return "/admin/coach/edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String update(@ModelAttribute("coach") User coach, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        validator.validate(coach, bindingResult);
        if(bindingResult.hasErrors()) {
            log.warn("Could not update coach. {}", bindingResult.getAllErrors());
            model.addAttribute("teams", teamService.all());
            return "/admin/coach/edit";
        }

        try {
            coach = service.create(coach);
            return "redirect:/admin/coach/show/" + coach.getId();
        } catch (Exception e) {
            log.error("Could not update coach.", e);
            bindingResult.reject("Could not update team. {}", e.getMessage());
            model.addAttribute("teams", teamService.all());
            return "/admin/coach/edit";
        }
    }
}
