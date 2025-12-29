package kr.or.ddit.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import kr.or.ddit.validate.groups.DeleteGroup;
import kr.or.ddit.validate.groups.InsertGroup;
import kr.or.ddit.validate.groups.UpdateGroup;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class AddressDTO {
	
	@NotNull(groups = UpdateGroup.class, message = "수정할 번호가 없습니다.")
    private Integer adrsNo;

    @NotNull(groups = {Default.class, DeleteGroup.class})
    private String memId;

    @NotBlank(groups = {Default.class}, message = "이름은 필수입니다.")
    private String adrsName;

    @NotBlank(groups = Default.class, message = "전화번호 필수!")
    @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$", groups = Default.class, message = "전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)")
    private String adrsTel;

    private String adrsAdd;

    @Email(groups = Default.class)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", groups = Default.class, message = "올바른 이메일 형식이 아닙니다. (예: example@domain.com)")
    private String adrsMail;
}
