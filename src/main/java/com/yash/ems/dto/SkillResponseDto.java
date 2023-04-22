package com.yash.ems.dto;

import lombok.*;

import java.io.Serializable;
/**
 * This class is used to store and transfer user skill information for report module.
 * @author prachi.kurhe
 * @since 01--3-2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SkillResponseDto implements Serializable {

	private static final long serialVersionUID = 6230314933898099903L;
	private Integer skillId;
    private String skillName;
    private String remarks;
    private String ratingReceived;
	public Integer getSkillId() {
		return skillId;
	}
	public void setSkillId(Integer skillId) {
		this.skillId = skillId;
	}
	public String getSkillName() {
		return skillName;
	}
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRatingReceived() {
		return ratingReceived;
	}
	public void setRatingReceived(String ratingReceived) {
		this.ratingReceived = ratingReceived;
	}
}
