package com.thekuzea.diploma.controller;

import com.thekuzea.diploma.gui.prompt.AddNewApp;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    private AddNewApp addNewUser;

    public UserController(AddNewApp addNewUser) {
        this.addNewUser = addNewUser;
    }
}
