package kr.or.ddit.homework07.exception;

//예외도 마찬가지로 결합도를 낮추기 위해 당연히 interface로 역할과 구현을 분리하려 하였으나
//추상 클래스로 분리를 한다고 한다. 왜일까?
//예외의 공통 기능(스택 트레이스, 에러 메시지 보관 등)은 이미 Throwable 클래스가 다 가지고 있기 때문에
//상위 클래스를 하나 만드는 방식을 택한다고 한다.
//내가 이해한 바로는 
//"인터페이스는 아예 없는 것을 만드는 것"과 "추상 클래스는 있는 것에 덮어씌우는(확장하는) 것" 이라고 이해했다.
public abstract class BusinessException extends RuntimeException {

	public BusinessException(String message)  {
        super(message);
    }

}
