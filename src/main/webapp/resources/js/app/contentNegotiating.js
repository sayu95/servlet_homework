document.addEventListener("change", async (e) => {
	const LocaleTag = event.target.value;
	const accept = document.querySelector('[name="accept"]:checked').value
	console.log(accept)
	console.log(LocaleTag)

	await fetch('/api/update-language', {
		method: 'GET',
		headers: {
			'Accept': 'application/json'
		}
	});
});