package com.thekuzea.diploma.controller;

import com.thekuzea.diploma.gui.admin.frame.AdminFrame;
import com.thekuzea.diploma.gui.user.frame.UserFrame;
import com.thekuzea.diploma.model.User;
import com.thekuzea.diploma.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import static com.thekuzea.diploma.MainClass.USERNAME;

@Controller
public class MainController {

    private AdminFrame adminFrame;
    private UserFrame userFrame;
    private User currentUser;

    @Autowired
    private UserRepository userRepository;

    public MainController(AdminFrame adminFrame, UserFrame userFrame) {
        this.adminFrame = adminFrame;
        this.userFrame = userFrame;
    }

    public void prepareAndOpenFrame() {
        currentUser = userRepository.findByUsername(USERNAME);

        if(currentUser.getRole().equals("admin")) {
            adminFrame.setVisible(true);
        } else {
            userFrame.setVisible(true);
        }
    }

}
