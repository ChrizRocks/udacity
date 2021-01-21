package com.udacity.course3;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestRequest {


    @GetMapping
    public String test(){
        return "Data Structures and Persistence are pretty good.";
    }
}
