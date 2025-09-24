package com.project.domain.senior;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Guardian {
    private String guardianName;
    private String guardianPhone;
    private String relationship;
    private String guardianNote;
    
    @Builder
    public Guardian(String guardianName, String guardianPhone, String relationship, String guardianNote) {
        this.guardianName = guardianName;
        this.guardianPhone = guardianPhone;
        this.relationship = relationship;
        this.guardianNote = guardianNote;
    }
}