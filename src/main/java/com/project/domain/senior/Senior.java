package com.project.domain.senior;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.project.domain.analysis.OverallResult;
import com.project.domain.analysis.Risk;

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
import jakarta.persistence.OneToMany;
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

	@Enumerated(EnumType.STRING)
	private Risk state;
	
	@Enumerated(EnumType.STRING)
	private Residence residence;
	
	private String phone;
	
	@Embedded
	private Address address;
	
	private String note;
    
	@Embedded
	private Guardian guardian;
	
	@Embedded
	private MedicalInfo medicalInfo;

	@CreationTimestamp
    private LocalDateTime createdAt;
	
	@OneToMany(mappedBy = "senior", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OverallResult> overallResults = new ArrayList<>();
        
    @Builder
    public Senior(String name, LocalDate birthDate, Sex sex, Residence residence, String phone, 
    		Address address, String note, Guardian guardian, MedicalInfo medicalInfo, List<OverallResult> overallResults) {
        this.name = name;
        this.birthDate = birthDate;
        this.sex = sex;
        this.state = Risk.POSITIVE;
        this.residence = residence;
        this.phone = phone;
        this.address = address;
        this.note = note;
        this.guardian = guardian;
        this.medicalInfo = medicalInfo;
        if(overallResults == null)
        	this.overallResults = new ArrayList<>();
    }
    
    public void updatePersonalInfo(String name, LocalDate birthDate, Sex sex, Residence residence, String phone, Address address, String note) {
        this.name = name;
        this.birthDate = birthDate;
        this.sex = sex;
        this.residence = residence;
        this.phone = phone;
        this.address = address;
        this.note = note;
    }

    public void updateState(Risk state) {
        this.state = state;
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
