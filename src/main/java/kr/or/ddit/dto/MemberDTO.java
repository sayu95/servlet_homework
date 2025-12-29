package kr.or.ddit.dto;

import java.io.Serializable;
import java.time.LocalDate;

import org.apache.commons.lang3.builder.ToStringExclude;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 회원 관리와 인증 시스템에서 사용할 Domin Layer
 * 회원 : memId, memPass, memName, memHp, memMail
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
	private String memHp;
	private String memMail;
}
