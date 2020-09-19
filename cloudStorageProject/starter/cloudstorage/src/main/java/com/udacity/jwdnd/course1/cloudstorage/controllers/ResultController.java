package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResultController {
    @GetMapping("/result")
    public String viewResult() {
        return "result";
    }

    @GetMapping("/result/success")
    public String viewSuccessResult(Model model) {
        model.addAttribute("messageType", "success");
        return "result";
    }

    @GetMapping("/result/error")
    public String viewErrorResult(Model model) {
        model.addAttribute("messageType", "error");
        return "result";
    }

    @GetMapping("/result/validation/file/error")
    public String viewValidationErrorResult(Model model) {
        model.addAttribute("messageType", "validationError");
        model.addAttribute("validationText", "the File is Empty or a File with the same name is already exist, please consider to choose a valid file with unique Name.");
        return "result";
    }
}
