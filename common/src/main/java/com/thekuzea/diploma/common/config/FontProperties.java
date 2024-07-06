package com.thekuzea.diploma.common.config;

import java.awt.Font;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "local-wall.app-font")
public class FontProperties {

    private static final String DEFAULT_FONT_NAME = "DejaVu Sans Mono";

    private static final int DEFAULT_STYLE_INDEX = Font.BOLD;

    private static final int DEFAULT_FONT_SIZE = 12;

    private String name = DEFAULT_FONT_NAME;

    private int styleIndex = DEFAULT_STYLE_INDEX;

    private int size = DEFAULT_FONT_SIZE;
}
