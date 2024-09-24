package com.thekuzea.diploma.common.persistence.domain.app;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.thekuzea.diploma.common.persistence.model.RestrictedEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, exclude = "id")
@SuperBuilder
@Document(collection = "applications")
public class App extends RestrictedEntity {

    @Id
    private String id;

    private String name;
}
