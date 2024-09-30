package com.thekuzea.diploma.common.persistence.domain.website;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.thekuzea.diploma.common.persistence.model.RestrictedEntity;

import static com.thekuzea.diploma.common.constant.GlobalConstants.UI_TEXT_DELIMITER;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Document(collection = "websites")
public class Website extends RestrictedEntity {

    @Id
    private String id;

    private String url;

    @Override
    public String toString() {
        return url + UI_TEXT_DELIMITER + super.toString();
    }
}
