package com.spike.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Author: tangji
 * Date: 2019 07 26 16:50
 * Description: <...>
 */
@Controller
@RequestMapping("/user")
public class RegisterController {

    @RequestMapping(value = "/do_register",method = RequestMethod.GET)
    public String registerHome(){
        return "register";
    }
}
