import React, { useEffect, useState } from 'react';
import MbtiList from './components/MbtiList';
import MbtiForm from './components/MbtiForm';

function App() {
  const [mbtiList, setMbtiList] = useState([]);
  const API_URL = 'http://localhost/hw07/mbti';

  // 목록 로딩
  const fetchList = () => {
    fetch(API_URL)
      .then(res => res.json())
      .then(data => setMbtiList(data));
  };

  useEffect(() => { fetchList(); }, []);

  // 삭제 로직
  const handleDelete = (type) => {
    if (!window.confirm("정말 삭제할까요?")) return;
    fetch(`${API_URL}/${type}`, { method: 'DELETE' })
      .then(() => fetchList()); // 삭제 후 목록 새로고침
  };

  // 추가 로직
  const handleAdd = (newData) => {
    fetch(API_URL, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(newData)
    }).then(() => fetchList());
  };

  const handleUpdate = (type, updatedData) => {
  fetch(`${API_URL}/${type}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      mtType: type, 
      mtTitle: updatedData.mtTitle,
      mtContent: updatedData.mtContent
    })
  })
  .then(res => res.json())
  .then(result => {
    if(result > 0) {
      alert("수정되었습니다!");
      fetchList(); 
    }
  })
  .catch(err => console.error("수정 실패:", err));
};

  return (
    <div style={{ padding: '20px' }}>
      <h1>MBTI 관리 시스템</h1>
      <MbtiForm onAdd={handleAdd} />
      <MbtiList list={mbtiList} onDelete={handleDelete} onUpdate={handleUpdate} />
    </div>
  );
}

export default App;