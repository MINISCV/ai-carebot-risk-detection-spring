package com.project.domain;

import java.time.LocalDate;

import com.project.dto.SeniorDto;

import jakarta.persistence.CascadeType;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY )
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
    
	private String guardianName;
	
	private String guardianPhone;
    
	private String relationship;
	
    private String guardianNote;
    
    private String diseases;
    
    private String medications;
    
    @Builder
    public Senior(String name, LocalDate birthDate, Sex sex, String phone, 
                  String address, String note, String guardianName, String guardianPhone, 
                  String relationship, String guardianNote, String diseases, String medications) {
        this.name = name;
        this.birthDate = birthDate;
        this.sex = sex;
        this.phone = phone;
        this.address = address;
        this.note = note;
        this.guardianName = guardianName;
        this.guardianPhone = guardianPhone;
        this.relationship = relationship;
        this.guardianNote = guardianNote;
        this.diseases = diseases;
        this.medications = medications;
    }
    
    public void update(SeniorDto dto) {
        this.name = dto.name();
        this.birthDate = dto.birthDate();
        this.sex = dto.sex();
        this.phone = dto.phone();
        this.address = dto.address();
        this.note = dto.note();
        this.guardianName = dto.guardianName();
        this.guardianPhone = dto.guardianPhone();
        this.relationship = dto.relationship();
        this.guardianNote = dto.guardianNote();
        this.diseases = dto.diseases();
        this.medications = dto.medications();
    }
    
    public void changeDoll(Doll newDoll) {
        if (this.doll != null) {
            this.doll.setSenior(null);
        }
        this.doll = newDoll;
        if (newDoll != null) {
            newDoll.setSenior(this);
        }
    }
}
