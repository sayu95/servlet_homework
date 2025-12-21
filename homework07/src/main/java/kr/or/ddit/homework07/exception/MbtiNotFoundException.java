package kr.or.ddit.homework07.exception;

//커스텀 예외 처리
//수정할 대상이 없으면 예외 발생!
public class MbtiNotFoundException extends BusinessException {
    public MbtiNotFoundException(String message) {
        super(message);
    }
}
