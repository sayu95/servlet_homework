/**
 * 주소록 관리
 */

// RESTful 요청 및 결과 처리 함수
async function sendRequest(url, method, successMsg) {
    try {
        const resp = await fetch(url, { method: method });

        if (resp.ok) {
            alert(successMsg);
            location.reload();
        } else {
            const errorMsg = await resp.text();
            alert("요청 실패: " + (errorMsg || resp.statusText));
        }
    } catch (error) {
        console.error("Error:", error);
        alert("통신 오류가 발생했습니다.");
    }
}

// 1. 수정 모드 전환
function toggleEdit(btn) {
    const row = btn.closest('tr');
    row.querySelectorAll('.view-mode').forEach(el => el.style.display = 'none');
    row.querySelectorAll('.edit-mode').forEach(el => el.style.display = 'inline-block');
}

// 2. 수정 취소
function cancelEdit(btn) {
    const row = btn.closest('tr');
    row.querySelectorAll('.view-mode').forEach(el => el.style.display = 'inline-block');
    row.querySelectorAll('.edit-mode').forEach(el => el.style.display = 'none');
}

// 3. PUT 수정 처리
async function saveEdit(btn) {
    // 1. 프로젝트 경로(bpath) 가져오기
    const bpath = document.body.dataset.bpath;
    // 2. 가장 가까운 tr에서 주소 번호(adrsNo) 가져오기
    const row = btn.closest('tr');
    const adrsNo = row.dataset.adrsNo;
    const mail = row.querySelector('.in-mail').value;
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    if (mail && !emailRegex.test(mail)) {
        alert("올바른 이메일 형식이 아닙니다. (예: example@test.com)");
        row.querySelector('.in-mail').focus(); // 이메일 입력창으로 포커스 이동
        return; // 함수 종료 (서버 전송 방지)
    }

    const params = new URLSearchParams();
    params.append("adrsNo", adrsNo);
    params.append("adrsName", row.querySelector('.in-name').value);
    params.append("adrsTel", row.querySelector('.in-tel').value);
    params.append("adrsAdd", row.querySelector('.in-add').value);
    params.append("adrsMail", row.querySelector('.in-mail').value);

    const url = bpath + "/address?" + params.toString();
    await sendRequest(url, 'PUT', "수정되었습니다.");
}

// 4. [DELETE] 삭제 처리
async function deleteAddress(btn) {
    // 1. 프로젝트 경로 가져오기
    const bpath = document.body.dataset.bpath;
    // 2. 가장 가까운 tr에서 주소 번호 가져오기
    const row = btn.closest('tr');
    const adrsNo = row.dataset.adrsNo;

    if (!adrsNo) {
        alert("삭제할 번호를 찾을 수 없습니다.");
        return;
    }

    if (!confirm(adrsNo + "번 주소를 정말 삭제하시겠습니까?")) return;

    const url = bpath + "/address?adrsNo=" + adrsNo;
    await sendRequest(url, 'DELETE', "삭제되었습니다.");
}