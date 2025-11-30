const content = document.getElementById('content');
const uri = "../../calc/calculate2";
let accept = 'html';
const nameSelectors = {
	getLeft: () => document.querySelector('input[name="left"]').value,
	getOperator: () => document.querySelector('input[name="operator"]').value,
	getRight: () => document.querySelector('input[name="right"]').value,
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

function sendRequest(Data, targetElement) {
	//만약 text/html이라면 GET으로 쿼리스트링을 보낸다.
	if (accept == "text/html") {
		fetch(Data, {
			method: 'GET',
			headers: {
				//헤더에 Accept를 담아 보낸다.
				'Accept': accept,
				'Content-Type': accept
			}
		}).then(resp => resp.text())
			.then(resp => targetElement.innerHTML = resp)
			.catch(error => targetElement.innerHTML = error.message);
	}
	else {
		//만약 text/html이라면 POST으로 JSON을 보낸다.
		fetch(uri, {
			method: 'POST',
			headers: {
				//헤더에 Accept를 담아 보낸다.
				'Accept': accept,
				'Content-Type': accept
			},
			//POST는 body가 존재하기에 바디에 JSON 데이터를 담아보낸다.
			body: JSON.stringify(Data)
		}).then(resp => {
			if (!resp.ok) {
				return resp.json().then(errorData => {
					return Promise.reject(errorData);
				});
			}
			return resp.json();
		}).then(resp => {
			const htmlOutput = `<p>계산식: ${resp.left} ${resp.operator} ${resp.right}</p> <p style='font-weight: bold; color: blue;'> JSON 결과: ${resp.result}</p>`;
			targetElement.innerHTML = htmlOutput;
		}).catch(error => {
			if (error.message) {
				targetElement.innerHTML = `<p style='color:red;'> JSON 오류(${error.error}) : ${error.message} </p>`;
				}
		});
	}
}

function handleCalculation(sourceSelectors, targetElement) {
	//text/html이라면 쿼리스트링을 만든다.
	if (accept == "text/html") {
		// 선택자 객체를 사용하여 입력값을 가져와 객체화
		const queryString = makequeryString({
			// sourceSelectors에 정의된 함수를 실행하여 value를 가져옴
			left: sourceSelectors.getLeft(),
			operator: sourceSelectors.getOperator(),
			right: sourceSelectors.getRight()
		});
		//fetch 함수
		sendRequest(queryString, targetElement);
	}

	//application/json이라면 그냥 객체화만 진행한다.
	else {
		const Data = {
			// sourceSelectors에 정의된 함수를 실행하여 value를 가져옴
			left: sourceSelectors.getLeft(),
			operator: sourceSelectors.getOperator(),
			right: sourceSelectors.getRight()
		};
		//fetch 함수
		sendRequest(Data, targetElement);
	}


}

//모든 버튼에 대해 change가 발생하면 실행되는 함수
document.addEventListener("change", (e) => {

	if (e.target.type === 'radio') accept = e.target.value;
});


document.addEventListener("submit", (e) => {
	//SPA를 위한 Submit 이벤트를 막음
	e.preventDefault();

	//radio에 따라 ContentType 지정
	if (accept == "html" || accept == 'text/html') accept = 'text/html';
	else accept = 'application/json';

	//데이터 전송의 일괄작업 함수
	handleCalculation(nameSelectors, content);
});