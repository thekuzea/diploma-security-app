package com.thekuzea.diploma.common.persistence.model;

import java.time.LocalTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
@SuperBuilder
public class RestrictedEntity {

    protected boolean isInfiniteRestriction;

    protected LocalTime restrictionStartTime;

    protected LocalTime restrictionEndTime;

    public boolean isRestricted() {
        if (isInfiniteRestriction) {
            return true;
        }

        final LocalTime currentTime = LocalTime.now();
        return currentTime.isAfter(restrictionStartTime) && currentTime.isBefore(restrictionEndTime);
    }
}
