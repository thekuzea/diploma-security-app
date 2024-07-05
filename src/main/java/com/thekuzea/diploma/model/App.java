package com.thekuzea.diploma.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString(callSuper = true, exclude = "id")
@SuperBuilder
@Document(collection = "applications")
public class App extends RestrictedEntity {

    @Id
    private String id;

    private String name;
}
