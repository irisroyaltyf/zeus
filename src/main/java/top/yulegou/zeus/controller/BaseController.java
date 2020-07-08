package top.yulegou.zeus.controller;

import org.springframework.ui.Model;

public class BaseController {
    protected String errorPage(final Model model, String error) {
        model.addAttribute("error", error);
        return "error/zeus-error";
    }
}
