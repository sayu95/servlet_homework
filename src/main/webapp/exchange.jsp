<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div>
		<h1>환율 거래소입니다</h1>
		<p>환율은 1466원입니다<p>
		환전할 원화를 입력하세요 (원 생략) : <input type="text" id="amount">
		<button onclick="exchange()">환율계산</button>
		<p id="resultArea"></p> 
	</div>
</body>
<script type="text/javascript">
function exchange() {
    const amountInput = document.getElementById("amount");
    const amount = amountInput.value.trim();
    
    fetch('<%= request.getContextPath()%>/currency/exchange', {
        method: 'POST', 
        headers: {
            'Content-Type': 'text/plain' 
        },
        body: amount 
    })
    
    .then(response => {
        return response.text();
    })
    
    .then(data => {
        console.log('서버 응답:', data);
        document.getElementById('resultArea').innerText = "결과: " + data;
    })
    
    .catch(error => {
        console.error('전송 실패:', error);
        document.getElementById('resultArea').innerText = 
            "오류 발생: " + error.message;
    });
}
</script>
</html>