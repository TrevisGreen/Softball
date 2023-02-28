package com.sofball.demo.web;


import com.sofball.demo.model.Division;
import com.sofball.demo.service.DivisionService;
import com.sofball.demo.service.SeasonService;
import com.sofball.demo.utils.Constants;
import com.sofball.demo.validation.DivisionValidator;
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
@RequestMapping("/admin/division")
public class DivisionController extends BaseController {

    @Autowired
    private DivisionService service;
    @Autowired
    private SeasonService seasonService;
    @Autowired
    private DivisionValidator validator;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "redirect:/admin/division/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model,
                       @RequestParam(value = "page", required = false) Integer page,
                       @RequestParam(value = "sort", required = false) String sort,
                       @RequestParam(value = "direction", required = false) String direction,
                       @RequestParam(value = "filter", required = false) String filter) {
        log.debug("Page: {}", page);

        if (StringUtils.isBlank(sort)) {
            sort = "season";
            direction = "desc";
        }

        Map<String, Object> params = buildPagination(page, sort, direction, filter);

        Page<Division> p;
        PageRequest pageRequest = (PageRequest) params.get(Constants.PAGE_REQUEST);
        if (StringUtils.isNotBlank(filter)) {
            p = service.search(filter, pageRequest);
        } else {
            p = service.list(pageRequest);
        }

        paginate(model, p, params);

        model.addAttribute("divisions", p);

        return "/admin/division/list";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String create(Model model) {
        Division division = new Division();
        model.addAttribute("division", division);
        model.addAttribute("seasons", seasonService.all());
        return "/admin/division/new";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String create(@ModelAttribute("division") Division division, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        validator.validate(division, bindingResult);
        if (bindingResult.hasErrors()) {
            log.warn("Could not create division. {}", bindingResult.getAllErrors());
            model.addAttribute("seasons", seasonService.all());
            return "/admin/division/new";
        }

        try {
            division = service.create(division);
            return "redirect:/admin/division/show/" + division.getId();
        } catch (Exception e) {
            log.error("Could not create division.", e);
            bindingResult.reject("Could not create division. {}", e.getMessage());
            model.addAttribute("seasons", seasonService.all());
            return "/admin/division/new";
        }
    }

    @RequestMapping(value = "/show/{divisionId}", method = RequestMethod.GET)
    public String show(@PathVariable("divisionId") Integer divisionId, Model model) {
        Division division = service.get(divisionId);
        model.addAttribute("division", division);
        return "/admin/division/show";
    }

    @RequestMapping(value = "/delete/{divisionId}", method = RequestMethod.GET)
    public String delete(@PathVariable("divisionId") Integer divisionId, RedirectAttributes redirectAttributes) {
        try {
            String name = service.delete(divisionId);
            redirectAttributes.addFlashAttribute("message", "Division " + name + " deleted successfully!");
        } catch (Exception e) {
            log.error("Could not delete division " + divisionId + ".", e);
            redirectAttributes.addFlashAttribute("message", "Division " + divisionId + " could not be deleted.");
            redirectAttributes.addFlashAttribute("messageStyle", "alert-danger");
        }
        return "redirect:/admin/division/list";
    }

    @RequestMapping(value = "/edit/{divisionId}", method = RequestMethod.GET)
    public String update(Model model, @PathVariable("divisionId") Integer divisionId) {
        Division division = service.get(divisionId);
        model.addAttribute("division", division);
        model.addAttribute("seasons", seasonService.all());
        return "/admin/division/edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String update(@ModelAttribute("division") Division division, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        validator.validate(division, bindingResult);
        if (bindingResult.hasErrors()) {
            log.warn("Could not update division. {}", bindingResult.getAllErrors());
            model.addAttribute("seasons", seasonService.all());
            return "/admin/division/edit";
        }

        try {
            division = service.create(division);
            return "redirect:/admin/division/show/" + division.getId();
        } catch (Exception e) {
            log.error("Could not update division.", e);
            bindingResult.reject("Could not update division. {}", e.getMessage());
            model.addAttribute("seasons", seasonService.all());
            return "/admin/division/edit";
        }
    }
}
