package com.example.securingweb.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@PreAuthorize("hasAuthority('USER')")
public class CloseSessionController {

    @GetMapping("/closeSession")
    public String sessionList(@RequestParam Integer endAmount, Map<String, Object> model) {

        model.put("endAmount", endAmount);

        return "closeSession";
    }

}
