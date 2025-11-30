const content = document.getElementById('content');
const btn1 = document.querySelector('#btn');
const btn2 = document.querySelector('button[type="submit"]');
const uri = "../../calc/calculate";

// ID로 가져오는 데이터 소스
const idSelectors = {
    getLeft: () => document.getElementById('left').value,
    getOperator: () => document.getElementById('operator').value,
    getRight: () => document.getElementById('right').value,
};

// name으로 가져오는 데이터 소스
const nameSelectors = {
    getLeft: () => document.querySelector('input[name="left2"]').value,
    getOperator: () => document.querySelector('input[name="operator2"]').value,
    getRight: () => document.querySelector('input[name="right2"]').value,
};

//객체 구조 분해 사용으로 함수 호출부분 줄이기
function makequeryString({ left, operator, right }) {
	//여러개의 데이터를 쿼리스트링으로 보내야하니 객체화
	let params = {
		left,
		operator,
		right
	};
	
	//URL의 쿼리 스트링(Query String)을 쉽게 생성, 파싱, 수정할 수 있도록 도와주는 도구
	const searchParams = new URLSearchParams(params);

	//searchParams params을 합친 객체를 다시 스트링화
	const queryString = uri + '?' + searchParams.toString();

	return queryString;
}

function sendRequest(queryString, targetElement) {
	fetch(queryString)
		.then(resp => resp.text())
		.then(result => targetElement.innerHTML = result)
		.catch(error => targetElement.innerHTML = "오류 발생: " + error.message);
}

function handleCalculation(sourceSelectors, targetElement) {
    
    // 선택자 객체를 사용하여 입력값을 가져와 객체화
	const queryString = makequeryString({
        // sourceSelectors에 정의된 함수를 실행하여 value를 가져옴
		left: sourceSelectors.getLeft(), 
		operator: sourceSelectors.getOperator(),
		right: sourceSelectors.getRight()
	});
	
	sendRequest(queryString, targetElement);
}

function send() {
    // ID 기반 선택자 객체와 content 출력 요소를 전달
    handleCalculation(idSelectors, content); 
}

// 3. (만약 btn에 이벤트 리스너가 있다면)
btn1.addEventListener("click", send);
btn2.addEventListener("click", (e) => {
	// 1. SPA를 위한 submit 기본 기능 제한 (페이지 새로고침 방지)
	e.preventDefault();
	
	 // ID 기반 선택자 객체와 content 출력 요소를 전달
	handleCalculation(nameSelectors, content);
});

