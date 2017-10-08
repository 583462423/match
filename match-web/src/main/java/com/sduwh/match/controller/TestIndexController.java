package com.sduwh.match.controller;

import com.sduwh.match.controller.base.BaseController;
import com.sduwh.match.exception.base.BaseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestIndexController extends BaseController {

    @GetMapping("/error")
    public String error(){
        return "/error";
    }
}
