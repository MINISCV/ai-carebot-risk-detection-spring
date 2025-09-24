package com.project.domain.senior;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Senior {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "doll_id", unique = true)
	private Doll doll;
	
	private String name;
	
	private LocalDate birthDate;
	
	@Enumerated(EnumType.STRING)
	private Sex sex;
	
	private String phone;
	
	private String address;
	
	private String note;
    
	@Embedded
	private Guardian guardian;
	
	@Embedded
	private MedicalInfo medicalInfo;
        
    @Builder
    public Senior(String name, LocalDate birthDate, Sex sex, String phone, 
                  String address, String note, Guardian guardian, MedicalInfo medicalInfo) {
        this.name = name;
        this.birthDate = birthDate;
        this.sex = sex;
        this.phone = phone;
        this.address = address;
        this.note = note;
        this.guardian = guardian;
        this.medicalInfo = medicalInfo;
    }
    
    public void updatePersonalInfo(String name, LocalDate birthDate, Sex sex, String phone, String address, String note) {
        this.name = name;
        this.birthDate = birthDate;
        this.sex = sex;
        this.phone = phone;
        this.address = address;
        this.note = note;
    }

    public void updateGuardianInfo(Guardian guardian) {
        this.guardian = guardian;
    }

    public void updateMedicalInfo(MedicalInfo medicalInfo) {
        this.medicalInfo = medicalInfo;
    }
    
    public void changeDoll(Doll newDoll) {
        if (this.doll != null)
            this.doll.setSenior(null);
        this.doll = newDoll;
        if (newDoll != null)
            newDoll.setSenior(this);
    }
}
