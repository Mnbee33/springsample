package com.example.springsample.login.controller;

import com.example.springsample.login.domain.model.GroupOrder;
import com.example.springsample.login.domain.model.SignupForm;
import com.example.springsample.login.domain.model.User;
import com.example.springsample.login.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class SignupController {
    @Autowired
    private UserService userService;

    private Map<String, String> radioMarriage;

    private Map<String, String> initRadioMarriage() {
        Map<String, String> radio = new LinkedHashMap<>();
        radio.put("既婚", "true");
        radio.put("未婚", "false");
        return radio;
    }

    @ExceptionHandler(DataAccessException.class)
    public String dataAccessExceptionHandler(DataAccessException e, Model model){
        model.addAttribute("error", "内部サーバーエラー（DB）：ExceptionHandler");
        model.addAttribute("message", "SignupControllerでDataAccessExceptionが発生しました");
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e, Model model){
        model.addAttribute("error", "内部サーバーエラー：ExceptionHandler");
        model.addAttribute("message", "SignupControllerでExceptionが発生しました");
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
        return "error";
    }

    @GetMapping("/signup")
    public String getSignUp(@ModelAttribute SignupForm form, Model model) {
        radioMarriage = initRadioMarriage();
        model.addAttribute("radioMarriage", radioMarriage);
        return "login/signup";
    }

    @PostMapping("/signup")
    public String postSignUp(@ModelAttribute @Validated(GroupOrder.class) SignupForm form,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return getSignUp(form, model);
        }
        System.out.println(form);

        User user = new User();
        user.setUserId(form.getUserId());
        user.setPassword(form.getPassword());
        user.setUserName(form.getUserName());
        user.setBirthday(form.getBirthday());
        user.setAge(form.getAge());
        user.setMarriage(form.isMarriage());
        user.setRole("ROLE_GENERAL");

        boolean isSuccessfulInsertion = userService.insert(user);
        if (isSuccessfulInsertion) {
            System.out.println("insert成功");
        } else {
            System.out.println("insert失敗");

        }

        return "redirect:/login";
    }
}
