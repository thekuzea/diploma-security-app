package com.thekuzea.diploma.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(exclude = {"id", "forbiddenApps", "forbiddenWebsites"})
@Builder
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String username;

    private String role;

    @DBRef
    private List<App> forbiddenApps;

    @DBRef
    private List<Website> forbiddenWebsites;
}
