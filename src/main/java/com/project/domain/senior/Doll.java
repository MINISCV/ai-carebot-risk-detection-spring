package com.project.domain.senior;

import java.util.ArrayList;
import java.util.List;

import com.project.domain.analysis.OverallResult;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Doll {
	@Id
	private String id;
	
	@OneToOne(mappedBy = "doll")
    private Senior senior;
	
	@OneToMany(mappedBy = "doll", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OverallResult> overallResults = new ArrayList<>();
	

	@Builder
    public Doll(String id, Senior senior, List<OverallResult> overallResults) {
        this.id = id;
        this.senior = senior;
        if (overallResults != null) {
            this.overallResults = overallResults;
        }
    }
	
	protected void setSenior(Senior senior) {
	    this.senior = senior;
	}
}
