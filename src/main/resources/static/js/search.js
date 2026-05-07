const API_BASE = 'http://localhost:8080/api';

// Поиск техники
async function searchEquipment(params = {}) {
    const defaultParams = {
        page: 0,
        size: 30,
        sortBy: 'name',
        sortDir: 'asc'
    };
    
    const queryParams = { ...defaultParams, ...params };
    const url = new URL(`${API_BASE}/search`);
    Object.keys(queryParams).forEach(key => {
        if (queryParams[key] !== undefined && queryParams[key] !== '') {
            url.searchParams.append(key, queryParams[key]);
        }
    });
    
    const response = await fetch(url);
    if (!response.ok) throw new Error('Ошибка загрузки данных');
    return response.json();
}

// Удаление записи
async function deleteEquipment(name) {
    const response = await fetch(`${API_BASE}/delete/${encodeURIComponent(name)}`, {
        method: 'DELETE'
    });
    if (!response.ok) throw new Error('Ошибка удаления');
    return true;
}

// Обновление записи
async function updateEquipment(oldName, data) {
    const response = await fetch(`${API_BASE}/update/${encodeURIComponent(oldName)}`, {
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    });
    if (!response.ok) throw new Error('Ошибка обновления');
    return response.json();
}
