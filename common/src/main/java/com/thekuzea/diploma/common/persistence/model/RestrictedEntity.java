package com.thekuzea.diploma.common.persistence.model;

import java.time.LocalTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import com.thekuzea.diploma.common.utils.DateTimeUtils;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
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

    @Override
    public String toString() {
        return DateTimeUtils.formatTime(restrictionStartTime) +
                ((isInfiniteRestriction) ? "always" : "-") +
                DateTimeUtils.formatTime(restrictionEndTime);
    }
}
