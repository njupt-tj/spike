package com.spike.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author: tangji
 * Date: 2019 07 26 16:51
 * Description: <...>
 */
@Controller
@RequestMapping("/user")
public class LoginController {

    @RequestMapping(value = "/login")
    public String register(){
        return "login";
    }
}
