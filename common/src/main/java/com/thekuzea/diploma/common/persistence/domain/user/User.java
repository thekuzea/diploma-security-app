package com.thekuzea.diploma.common.persistence.domain.user;

import java.util.List;

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

import com.thekuzea.diploma.common.persistence.domain.app.App;
import com.thekuzea.diploma.common.persistence.domain.website.Website;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(exclude = {"id", "restrictedApps", "restrictedWebsites"})
@Builder
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String username;

    private String role;

    @DBRef
    private List<App> restrictedApps;

    @DBRef
    private List<Website> restrictedWebsites;
}
