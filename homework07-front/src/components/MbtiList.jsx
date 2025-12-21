import React, { useState } from 'react';

function MbtiList({ list, onDelete, onUpdate }) {
  // 현재 어떤 항목을 수정 중인지 저장 (mtType 저장)
  const [editTarget, setEditTarget] = useState(null);
  // 수정 중인 임시 데이터 저장
  const [editData, setEditData] = useState({ mtTitle: '', mtContent: '' });

  // 수정 시작 버튼 클릭
  const startEdit = (item) => {
    setEditTarget(item.mtType);
    setEditData({ mtTitle: item.mtTitle, mtContent: item.mtContent });
  };

  // 수정 취소
  const cancelEdit = () => {
    setEditTarget(null);
  };

  // 수정 완료 (서버 전송)
  const saveEdit = (mtType) => {
    onUpdate(mtType, editData);
    setEditTarget(null);
  };

  return (
    <table border="1" style={{ width: '100%', marginTop: '20px', borderCollapse: 'collapse' }}>
      <thead>
        <tr style={{ backgroundColor: '#f4f4f4' }}>
          <th>유형</th>
          <th>제목</th>
          <th>성격 설명</th>
          <th>관리</th>
        </tr>
      </thead>
      <tbody>
        {list.map((item) => (
          <tr key={item.mtType} style={{ textAlign: 'center' }}>
            <td>{item.mtType}</td>
            {editTarget === item.mtType ? (
              <>
                {/* 수정 모드일 때 보일 화면 */}
                <td>
                  <input 
                    value={editData.mtTitle} 
                    onChange={(e) => setEditData({...editData, mtTitle: e.target.value})} 
                  />
                </td>
                <td>
                  <input 
                    value={editData.mtContent} 
                    onChange={(e) => setEditData({...editData, mtContent: e.target.value})} 
                    style={{ width: '90%' }}
                  />
                </td>
                <td>
                  <button onClick={() => saveEdit(item.mtType)}>저장</button>
                  <button onClick={cancelEdit}>취소</button>
                </td>
              </>
            ) : (
              <>
                {/* 일반 모드일 때 보일 화면 */}
                <td>{item.mtTitle}</td>
                <td style={{ textAlign: 'left', paddingLeft: '10px' }}>{item.mtContent}</td>
                <td>
                  <button onClick={() => startEdit(item)}>수정</button>
                  <button onClick={() => onDelete(item.mtType)}>삭제</button>
                </td>
              </>
            )}
          </tr>
        ))}
      </tbody>
    </table>
  );
}

export default MbtiList;