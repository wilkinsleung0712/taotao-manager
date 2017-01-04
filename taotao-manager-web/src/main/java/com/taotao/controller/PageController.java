package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 
 * @author WEIQIANG LIANG
 *
 */
@Controller
public class PageController {
    /**
     * Open main page
     */
    @RequestMapping("/")
    public String showIndex(){
        return "index";
    }
    
    /**
     * Open any page by using regex
     */
    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page){
        return page;
    }
}
