package com.sofball.demo.web;


import com.sofball.demo.model.Team;
import com.sofball.demo.service.DivisionService;
import com.sofball.demo.service.TeamService;
import com.sofball.demo.utils.Constants;
import com.sofball.demo.validation.TeamValidator;
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
@RequestMapping("/admin/team")
public class TeamController extends BaseController {

    @Autowired
    private TeamService service;

    @Autowired
    private DivisionService divisionService;

    @Autowired
    private TeamValidator validator;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "redirect:/admin/team/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model,
           @RequestParam(value = "page", required = false) Integer page,
           @RequestParam(value = "sort", required = false) String sort,
           @RequestParam(value = "direction", required = false) String direction,
           @RequestParam(value = "filter", required = false) String filter) {
        log.debug("Page: {}", page);

        if(StringUtils.isBlank(sort)) {
            sort = "division";
            direction = "desc";
        }

        Map<String, Object> params = buildPagination(page, sort, direction, filter);

        Page<Team> p;
        PageRequest pageRequest = (PageRequest) params.get(Constants.PAGE_REQUEST);
        if(StringUtils.isNotBlank(filter)) {
            p = service.search(filter, pageRequest);
        } else {
            p = service.list(pageRequest);
        }

        paginate(model, p, params);
        model.addAttribute("team", p);
        return "/admin/team/list";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String create(Model model) {
        Team team = new Team();
        model.addAttribute("team", team);
        model.addAttribute("divisions", divisionService.all());
        return "/admin/team/new";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String create(@ModelAttribute("team") Team team, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        validator.validate(team, bindingResult);
        if(bindingResult.hasErrors()) {
            log.warn("Could not create team. {}", bindingResult.getAllErrors());
            model.addAttribute("divisions", divisionService.all());
            return "/admin/team/new";
        }

        try {
            team = service.create(team);
            return "redirect:/admin/team/show/" + team.getId();
        } catch (Exception e) {
            log.error("Could not create team.", e);
            bindingResult.reject("Could not create team. {}", e.getMessage());
            model.addAttribute("divisions", divisionService.all());
            return "/admin/team/new";
        }
    }

    @RequestMapping(value = "/show/{teamId}", method = RequestMethod.GET)
    public String show(@PathVariable("teamId") Integer teamId, Model model) {
        Team team = service.get(teamId);
        model.addAttribute("team", team);
        return "/admin/team/show";
    }

    @RequestMapping(value = "/delete/{teamId}", method = RequestMethod.GET)
    public String delete(@PathVariable("teamId") Integer teamId, RedirectAttributes redirectAttributes) {
        try {
            String name = service.delete(teamId);
            redirectAttributes.addFlashAttribute("message", "Team " + name + " deleted successfully!");
        } catch (Exception e) {
            log.error("Could not delete team " + teamId + ".", e);
            redirectAttributes.addFlashAttribute("message", "Team " + teamId + " could not be successfully!" );
            redirectAttributes.addFlashAttribute("messageStyle", "alert-danger");
        }
        return "redirect:/admin/team/list";
    }

    @RequestMapping(value = "/edit/{teamId}", method = RequestMethod.GET)
    public String update(Model model, @PathVariable("teamId") Integer teamId) {
        Team team = service.get(teamId);
        model.addAttribute("team", team);
        model.addAttribute("divisions", divisionService.all());
        return "/admin/team/edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String update(@ModelAttribute("team") Team team, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        validator.validate(team, bindingResult);
        if(bindingResult.hasErrors()) {
            log.warn("Could not update team. {}", bindingResult.getAllErrors());
            model.addAttribute("divisions", divisionService.all());
            return "/admin/team/edit";
        }

        try {
            team = service.create(team);
            return "redirect:/admin/team/show/" + team.getId();
        } catch (Exception e) {
            log.error("Could not update team.", e);
            bindingResult.reject("Could not update team. {}", e.getMessage());
            model.addAttribute("divisions", divisionService.all());
            return "/admin/team/edit";
        }
    }
}
