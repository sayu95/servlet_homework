package kr.or.ddit.homework07.mbti.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of="mtSort") //Primary key
public class MbtiDTO {
	private Integer mtSort;
	private String mtType;
	private String mtTitle;
	private String mtContent;
}
