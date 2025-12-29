package kr.or.ddit.dto;

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
    @NotNull(groups = {DeleteGroup.class, UpdateGroup.class}) 
    private Integer adrsNo;

    @NotNull(groups = {Default.class, DeleteGroup.class})
    private String memId;

    @NotBlank(groups = {InsertGroup.class, UpdateGroup.class})
    private String adrsName;

    // 등록 시에만 특정 패턴 검증
    @NotBlank(groups = InsertGroup.class)
    @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$", groups = InsertGroup.class)
    private String adrsTel;

    private String adrsAdd;

    @NotBlank(groups = InsertGroup.class)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", groups = InsertGroup.class)
    private String adrsMail;
}
