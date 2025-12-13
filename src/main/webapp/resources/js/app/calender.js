/**
 * 
 */

 document.addEventListener("DOMContentLoaded", () => {
	const year = document.querySelector("[name=year]");
	const month = document.querySelector("[name=month]");
	const locale = document.querySelector("[name=locale]");
	const zoneId = document.querySelector("[name=zoneId]");
	const formElement = document.forms[0];
	locale.addEventListener("change", () => {
		formElement.requestSubmit();
	})
 })