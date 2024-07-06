package com.thekuzea.diploma.controller;

import com.thekuzea.diploma.gui.admin.frame.AdminFrame;
import com.thekuzea.diploma.gui.user.frame.UserWindow;
import com.thekuzea.diploma.model.User;
import com.thekuzea.diploma.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import static com.thekuzea.diploma.MainClass.USERNAME;

@Controller
public class MainController {

    private AdminFrame adminFrame;
    private UserWindow userWindow;
    private User currentUser;

    @Autowired
    private UserRepository userRepository;

    public MainController(AdminFrame adminFrame, UserWindow userWindow) {
        this.adminFrame = adminFrame;
        this.userWindow = userWindow;
    }

    public void prepareAndOpenFrame() {
        currentUser = userRepository.findByUsername(USERNAME);

        if(currentUser.getRole().equals("admin")) {
            adminFrame.setVisible(true);
        } else {
            userWindow.setVisible(true);
        }
    }

}
