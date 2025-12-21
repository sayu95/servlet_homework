import React, { useState } from 'react';

function MbtiForm({ onAdd }) {
  const [formData, setFormData] = useState({ 
    mtType: '', 
    mtTitle: '', 
    mtContent: '' 
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if(!formData.mtType || !formData.mtTitle) {
        alert("유형과 제목은 필수입니다!");
        return;
    }

    onAdd(formData);
    setFormData({ mtType: '', mtTitle: '', mtContent: '' }); 
  };

  return (
    <form onSubmit={handleSubmit} style={{ 
        marginBottom: '20px', 
        padding: '15px', 
        border: '1px solid #ddd',
        borderRadius: '8px' 
    }}>
      <input name="mtType" value={formData.mtType} placeholder="유형 (예: ENFP)" onChange={handleChange} />
      <input name="mtTitle" value={formData.mtTitle} placeholder="제목" onChange={handleChange} />
      <input name="mtContent" value={formData.mtContent} placeholder="내용 설명" onChange={handleChange} />
      <button type="submit" style={{ marginLeft: '10px' }}>등록하기</button>
    </form>
  );
}

export default MbtiForm;