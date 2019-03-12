package com.decathlon.finance.taxreport.controller;

import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.providers.ExpiringUsernameAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class IndexController {

    @RequestMapping(value="/home")
    public String home(HttpSession session, Model model, HttpServletRequest request) {
        SecurityContextImpl username = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        model.addAttribute("userProfile",username.getAuthentication().getPrincipal().toString());
        return "index";
    }

    @RequestMapping(value="/tax")
    public String taxHtml(HttpSession session, Model model, HttpServletRequest request) {
        SecurityContextImpl username = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        model.addAttribute("userProfile",username.getAuthentication().getPrincipal().toString());
        return "Tax";
    }

    @RequestMapping(value="/taxReport")
    public String taxReportHtml(HttpSession session, Model model, HttpServletRequest request) {
        SecurityContextImpl username = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        model.addAttribute("userProfile",username.getAuthentication().getPrincipal().toString());
        return "tax_report";
    }

    @RequestMapping(value="/conso")
    public String consoHtml(HttpSession session, Model model, HttpServletRequest request) {
        SecurityContextImpl username = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        model.addAttribute("userProfile",username.getAuthentication().getPrincipal().toString());
        return "Conso";
    }

    @RequestMapping(value="/gl")
    public String glHtml(HttpSession session, Model model, HttpServletRequest request) {
        SecurityContextImpl username = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        model.addAttribute("userProfile",username.getAuthentication().getPrincipal().toString());
        return "GL";
    }

    @RequestMapping(value="/rpa")
    public String rpaHtml(HttpSession session, Model model, HttpServletRequest request) {
        SecurityContextImpl username = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        model.addAttribute("userProfile",username.getAuthentication().getPrincipal().toString());
        return "RPA";
    }

    @RequestMapping(value="/datalake")
    public String datalakeHtml(HttpSession session, Model model, HttpServletRequest request) {
        SecurityContextImpl username = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        model.addAttribute("userProfile",username.getAuthentication().getPrincipal().toString());
        return "Datalake";
    }
}
