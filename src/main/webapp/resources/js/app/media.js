/** 
 *  1. 이제 서블릿에서 dirName을 보냈으니 해당 값을 받아야함
 *  2. fetch를 사용해 값을 받으려고 함
 *  3. option을 통해 해당 dirName을 선택할 수 있어야함
 *  4. 선택했다면 선택한 dirName을 보내줘야함
 */

//JSP만든 해당 CONTEXT_PATH를 불러옴
const APP_CONTEXT_PATH = window.App.CONFIG.contextPath;

//익명함수기 때문에 접근이 불가함 따라서 함수끝에 ();를 써서 바로 호출
(async () => {
	//교수님의 방식대로 MediaSel async, await를 이용한 fetch방식
	// why ? async, await를 쓰는가?
	// 1. Promise의 .then() 복잡한 체인구조 방지
	// 2. 에러 처리의 간소화
	// 3. 3. 조건부 비동기 로직의 용이성 (중요)
	const mediaSel = document.querySelector(`[name="media"]`);
	//바로 서블릿으로 요청을 보냄 미디어 목록을 가져오기 위해
	const resp = await fetch(`${APP_CONTEXT_PATH}/media/list`)
	const fileNames = await resp.json();
	let options = `<option value="">미디어 선택</option>`;
	options += fileNames.map((name) => `<option value="${name}">${name}</option>`).join("\n");
	mediaSel.innerHTML = options;

	//mediaSel에 이벤트리스너 생성
	//'change'이벤트 발생 시 해당 로직 실행
	mediaSel.addEventListener('change', function() {

		//this 명령을 이용하여 mediasel의 값을 바로 가져온다.
		//또한 this를 사용하기 때문에 화살표 함수 즉, 익명함수를 사용할 수 없다.
		const selectedmedia = this.value;

		//값이 존재한다면
		if (selectedmedia) {
			//change된 값을 다시 보내야 함으로 여기서 함수를 만든다.
			//이곳에 익명함수로 바로 callback 함수로 할수도 있을거같다 (나중에 해보기)
			//뭐가 이상적인지 교수님에게 물어보기
			sendSelectionMedia(selectedmedia);
		} 
	});
})();



//마찬가지로 fetch (async, wait)
async function sendSelectionMedia(dirName) {

	//다시 서블릿으로 보낼 주소
	const MEDIAURL = APP_CONTEXT_PATH + '/media/list';

	const resp = await fetch(MEDIAURL, {
		//POST형식은 method를 줘야하기때문에 위의 방식과는 살짝 다르다
		//POST형식
		method: 'POST',
		headers: {
			//JSON으로 보낸다.
			'Content-Type': 'application/json'
		},
		//역직렬화
		body: JSON.stringify({ directory: dirName })
	});

	const dataSel = document.querySelector(`[name="data"]`);

	//fetch가 정상적으로 통신 됐다면 데이터를 불러온다.
	const datas = await resp.json();
	let options = `<option value="">data 선택</option>`;
	options += datas.map((name) => `<option value="${name}">${name}</option>`).join("\n");
	dataSel.innerHTML = options;
}

const viewButton = document.getElementById('view-button');

viewButton.addEventListener('click', (e) => {
	e.preventDefault();

	const mediaInput = document.querySelector(`[name="media"]`).value;
	const dataInput = document.querySelector(`[name="data"]`).value;

	//MediaStreamingServlet로 보낼 데이터
	const requestData = {
		media: mediaInput,
		data: dataInput
	};

	const MEDIASTREAMING_URI = `${APP_CONTEXT_PATH}/media/stream`;

	//익명함수 바로 실행
	(async () => {
		try {
			const response = await fetch(MEDIASTREAMING_URI, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				//직렬화
				body: JSON.stringify(requestData)
			});

			if (!response.ok) {
				// response.text()로 서버 오류 메시지를 가져와서 throw
				// JSON을 역직렬화
				const errorText = await response.text().catch(() => '응답 본문 없음');
				throw new Error(`HTTP 요청 실패! 상태: ${response.status}. ${errorText.substring(0, 100)}`);
			}

			//다양한 컨탠츠 타입이 올 수 있으므로 타입에 따라 출력을 다르게 한다.
			const contentType = response.headers.get('content-type');

			//만약 응답의 컨탠츠 타입이 "text"라면
			if (contentType.includes('text/plain')) {
				//text니까 text()함수로 역직렬화
				const resultText = await response.text();
				//view에 뿌려주기
				const content = document.querySelector("#content");
				content.innerHTML = `<div>${resultText}</div>`;
			}

			//만약 응답의 컨탠츠 타입이 "jpeg"라면
			else if (contentType.includes('image/jpeg')) {
				//image니까 blob()함수로 역직렬화
				const resultimage = await response.blob();
				//보안으로 로컬위치를 보여줄 수 없으니 createObjectURL함수로 이미지 경로 생성
				const imageUrl = URL.createObjectURL(resultimage);
				//view에 뿌려주기
				const content = document.querySelector("#content");
				content.innerHTML = `<img src=${imageUrl}>`
			}

			//만약 응답의 컨탠츠 타입이 "png"라면
			else if (contentType.includes('image/png')) {
				//image니까 blob()함수로 역직렬화
				const resultimage = await response.blob();
				//보안으로 로컬위치를 보여줄 수 없으니 createObjectURL함수로 이미지 경로 생성
				const imageUrl = URL.createObjectURL(resultimage);
				//view에 뿌려주기
				const content = document.querySelector("#content");
				content.innerHTML = `<img src=${imageUrl}>`
			}

			//만약 응답의 컨탠츠 타입이 "mp4"라면
			else if (contentType.includes('video/mp4')) {
				//video니까 blob()함수로 역직렬화
				const resultVideo = await response.blob();
				//보안으로 로컬위치를 보여줄 수 없으니 createObjectURL함수로 이미지 경로 생성
				const videoUrl = URL.createObjectURL(resultVideo);
				//view에 뿌려주기
				const content = document.querySelector("#content");
				content.innerHTML = `<video controls src=${videoUrl}>`
			}

			else if (contentType.includes('image/x-icon')) {
				//image니까 blob()함수로 역직렬화
				const resulticon = await response.blob();
				//보안으로 로컬위치를 보여줄 수 없으니 createObjectURL함수로 이미지 경로 생성
				const imageUrl = URL.createObjectURL(resulticon);
				//view에 뿌려주기
				const content = document.querySelector("#content");
				content.innerHTML = `<img src=${imageUrl}>`;
			}
		}
		//서블릿에 옳지 않은 값을 보내서 비동기 통신이 실패했을때
		catch (error) {
			const outputElement = document.querySelector("#content");
			//서블릿에서 받아온 오류 뿌려주기
			if (outputElement) {
				outputElement.innerHTML = `<p style="color: red;">요청 실패: ${error.message}</p>`;
			}
		}
	})();
})


