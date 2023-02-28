package com.sofball.demo.web;

import com.sofball.demo.utils.Constants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseController {

    protected final transient Logger log = LoggerFactory.getLogger(getClass());

    protected Map<String, Object> buildPagination(Integer page, String sort, String direction, String filter) {
        Map<String, Object> params = new HashMap<>();
        String opposite;
        if(page == null) {
            page = 0;
        }
        if(StringUtils.isBlank(sort)) {
            sort = "lastUpdated";
            direction = "desc";
        }
        if(StringUtils.isBlank(direction)) {
            direction = "asc";
        }
        Sort s;
        switch (direction) {
            case "desc":
                s = new Sort(Sort.Direction.DESC, sort);
                opposite = "asc";
                break;
            default:
                s = new Sort(Sort.Direction.ASC, sort);
                opposite = "desc";
        }
        params.put(Constants.PAGE, page);
        params.put(Constants.SORT, sort);
        log.debug("{} : {}", direction, opposite);
        params.put(Constants.DIRECTION, direction);
        params.put(Constants.OPPOSITE_DIRECTION, opposite);
        params.put(Constants.FILTER, filter);
        PageRequest pageRequest = new PageRequest(page, 20, s);
        params.put(Constants.PAGE_REQUEST, pageRequest);
        return params;
    }
    protected void paginate(Model model, Page page, Map<String, Object> params) {
        Integer pageNumber = (Integer) params.get(Constants.PAGE);
        String sort = (String) params.get(Constants.SORT);
        String direction = (String) params.get(Constants.DIRECTION);
        String opposite = (String) params.get(Constants.OPPOSITE_DIRECTION);
        String filter = (String) params.get(Constants.FILTER);
        if(pageNumber == null) {
            pageNumber = 0;
        }
        List<Integer> pages = new ArrayList<>();

        int current = page.getNumber() + 1;
        int begin = Math.max(0, current - 6);
        int end = Math.min(begin + 11, page.getTotalPages());

        if(begin > 0) {
            pages.add(0);
            if(begin > 1) {
                pages.add(-1);
            }
        }
        for(int i = begin; i < end; i++) {
            pages.add(i);
        }
        if(end < page.getTotalPages()) {
            pages.add(-1);
            pages.add(page.getTotalPages() - 1);
        }
        model.addAttribute(Constants.SORT, sort);
        log.debug("{} : {}", direction, opposite);
        model.addAttribute(Constants.DIRECTION, direction);
        model.addAttribute(Constants.OPPOSITE_DIRECTION, opposite);
        model.addAttribute(Constants.CURRENT_PAGE, pageNumber);
        model.addAttribute(Constants.TOTAL_PAGES, page.getTotalPages() - 1);
        model.addAttribute(Constants.PAGES, pages);
        model.addAttribute(Constants.FILTER, filter);
    }
}
