/**
 * 
 */
document.addEventListener("DOMContentLoaded", () => {
	const btn = document.getElementById("btn");
	const content = document.getElementById("content");
	
	btn.addEventListener("click", (e) => {
		e.preventDefault();
		const form = document.forms[0];
		const formdata = new FormData(form);
		const url = form.action
		
		
		fetch(url, {
			method: "POST",
			body: formdata
		}).then(resp => resp.text())
		  .then(resp => content.innerHTML = resp);
	})


})