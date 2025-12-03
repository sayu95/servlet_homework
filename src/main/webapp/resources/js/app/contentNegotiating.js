document.getElementById('localeSelector').addEventListener('change', (e) => {
    const selectedLocaleCode = e.target.value;
    
    const params = new URLSearchParams();
    
    if (!selectedLocaleCode) {
        document.getElementById('timezoneSelector').innerHTML = '<option value="">-- 먼저 언어를 선택하세요 --</option>';
        return;
    }
    params.append('localeCode', selectedLocaleCode); 
    
    fetch('world-now', {
        method: 'POST',
        body: params
    })
});
