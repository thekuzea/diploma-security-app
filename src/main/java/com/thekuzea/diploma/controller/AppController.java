package com.thekuzea.diploma.controller;

import com.thekuzea.diploma.gui.prompt.AddNewApp;
import org.springframework.stereotype.Controller;

@Controller
public class AppController {

    private AddNewApp addNewApp;

    public AppController(AddNewApp addNewApp) {
        this.addNewApp = addNewApp;
    }
}
