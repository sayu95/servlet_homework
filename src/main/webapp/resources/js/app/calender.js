document.addEventListener("DOMContentLoaded", () => {
	const formElement = document.forms[0];
    
	document.addEventListener("change", (e) => {
        if (e.target && e.target.name !== 'month' && e.target.name !== 'year') {
		    formElement.requestSubmit();
        }
	})
 })