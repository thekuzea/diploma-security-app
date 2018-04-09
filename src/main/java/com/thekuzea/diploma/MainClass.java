package com.thekuzea.diploma;

import com.thekuzea.diploma.controller.MainController;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class MainClass {

    public static String USERNAME = "kuzeadima";
//    public static String USERNAME = "simple";

    public static void main(String[] args) {
        setUIFont (new javax.swing.plaf.FontUIResource("DejaVu Sans Mono",Font.BOLD,12));

        ConfigurableApplicationContext context = new SpringApplicationBuilder(MainClass.class)
                .headless(false)
                .run(args);

        MainController mainMenuController = context.getBean(MainController.class);
        mainMenuController.prepareAndOpenFrame();
    }

    public static void setUIFont(javax.swing.plaf.FontUIResource f){
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (key, f);
        }
    }

}
