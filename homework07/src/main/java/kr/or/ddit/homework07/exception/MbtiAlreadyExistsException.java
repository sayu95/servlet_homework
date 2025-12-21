package kr.or.ddit.homework07.exception;

//커스텀 예외 처리
//중복된 MBTI가 있을 때 던질 예외
public class MbtiAlreadyExistsException extends BusinessException {
    public MbtiAlreadyExistsException(String message) {
        super(message);
    }
}