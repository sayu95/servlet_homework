package kr.or.ddit.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class AddressDTO {
	private Integer adrsNo;
	private String memId;
	private String adrsName;
	private String adrsTel;
	private String adrsAdd;
	private String adrsMail;
}
