 document.addEventListener("DOMContentLoaded", () => {
	
	const transfer = document.getElementById("transfer")
	const upload = document.getElementById("upload");
	const content = document.getElementById("content");
	
	
	//전송
	transfer.addEventListener("click", (e) => {
		e.preventDefault();
		
		const formData = new FormData(document.forms[0]);
		const url = document.forms[0].action;
		const mbti = formData.get('mbti');
		console.log(mbti);
		fetch(`${url}?mbti=${mbti}`, {
			method : "GET"
		}).then(res => res.text())
		.then(res => content.innerHTML = res)
	}),
	
	//업로드
	upload.addEventListener("click", (e) => {
		e.preventDefault();
		
		const formData = new FormData(document.forms[1]);
		
		//data-context-path="<c:url value='/mbti/detail'/>">
		//컨택스트 패스와 url 가져오기
		const url = document.forms[1].dataset.contextPath;
				
		fetch(url, {
			method : "POST",
			body: formData
		})
	})
	
	
 })