package com.thekuzea.diploma.controller;

import com.thekuzea.diploma.gui.admin.prompt.AddNewWebsite;
import org.springframework.stereotype.Controller;

@Controller
public class WebsiteController {

    private AddNewWebsite addNewWebsite;

    public WebsiteController(AddNewWebsite addNewWebsite) {
        this.addNewWebsite = addNewWebsite;
    }
}
