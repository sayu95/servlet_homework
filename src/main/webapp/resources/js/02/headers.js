/**
 * 
 */
const url = "../proxy/http-headers";
document.addEventListener("DOMContentLoaded", () => {
	test();
})

function test() {
	fetch(url, {
		method: "get"
	})
	.then(res => {
		return res.text();
	})
	.then(data => {
		document.querySelector("#content").innerHTML = data;
	});
}

