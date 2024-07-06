package com.thekuzea.diploma.common.config;

import java.util.Enumeration;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FontConfig {

    @Bean
    public FontUIResource fontUIResource(final FontProperties fontProperties) {
        final FontUIResource fontUiResource =
                new FontUIResource(fontProperties.getName(), fontProperties.getStyleIndex(), fontProperties.getSize());

        final Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            final Object key = keys.nextElement();
            final Object value = UIManager.get(key);

            if (value instanceof FontUIResource) {
                UIManager.put(key, fontUiResource);
            }
        }

        return fontUiResource;
    }
}
