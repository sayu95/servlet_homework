package kr.or.ddit.web01.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Data
public class CalculateServlet2DTO implements Serializable {
	private double left;
	private String operator;
	private double right;
	private double result;
}
