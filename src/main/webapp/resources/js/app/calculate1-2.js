const btn2 = document.querySelector('button[type="submit"]');
const content2 = document.getElementById('content2');


btn.addEventListener("click", (e) => {
	e.preventDefault();
	
	const left = document.querySelector('input[name="left2"]').value;
	const operator = document.querySelector('input[name="operator2"]').value;
	const right = document.querySelector('input[name="right2"]').value;

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

	//쿼리스트링 비동기로 보내기
	fetch(queryString)
		.then(resp => resp.text())
		.then(result => {
			content.innerHTML = result;
		});
});