package kr.or.ddit.dto;

import java.io.Serializable;
import java.time.LocalDate;

import org.apache.commons.lang3.builder.ToStringExclude;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 회원 관리와 인증 시스템에서 사용할 Domin Layer
 * 회원 : id, password, name
 */

@Data
@EqualsAndHashCode(of="memId") //Primary key
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class MemberDTO implements Serializable {
	private String memId;
	private String memPass;
	private String memName;
	@ToStringExclude
	private transient String memRegno1;
	@ToStringExclude
	private transient String memRegno2;
	private LocalDate memBir;
	private String memZip;
	private String memAdd1;
	private String memAdd2;
	private String memHometel;
	private String memComtel;
	private String memHp;
	private String memMail;
	private String memJob;
	private String memLike;
	private String memMemorial;
	private LocalDate memMemorialday;
	private Integer memMileage;
	private boolean memDelete;
	private String memRole;
}
