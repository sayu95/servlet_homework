document.addEventListener("DOMContentLoaded", () => {
	const nowElement = document.querySelector("#now");

	function updateTime() {
		const nowDate = new Date();
		nowElement.textContent = nowDate;
	}
	updateTime();
	setInterval(updateTime, 1000);
	
	document.addEventListener("click", (e) => {
		e.preventDefault();
		ServerTime();
	})
});

function ServerTime() {
	axios.get("/hw02/01/server-now")
		.then(response => {
			const serverDate = new Date(response.data.now);
			const serverElement = document.querySelector("#serverNow");
			serverElement.textContent = serverDate
		})
		.catch(error => {
			console.error('에러 발생:', error);
		})
}