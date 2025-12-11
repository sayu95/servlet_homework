 document.addEventListener("DOMContentLoaded", () => {
	const btn = document.getElementById("btn");
	
	btn.addEventListener("click", (e) => {
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