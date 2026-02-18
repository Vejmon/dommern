import axios from 'axios';

export const postData = async (url = '', data = {}) => {
    return await axios.post(url, data)
        .then(response => response.data)
        .catch(e => {
            console.error("Error in postData:", e);
            throw e;
        });
}

export const getData = async (url = '', size = 20) => {
    return await axios.get(url, {
        params: {
            size: size
        },
        headers: {
            'Content-Type': 'application/json'
        },
        })
        .then(response => {
            return response.data
        })
        .catch(e => {
            console.error("Error in getData:", e);
            throw e;
        })
}

export const formatMs = (ms) => {
    if (ms == null || ms === '') return '---';
    const n = Number(ms);
    if (!Number.isFinite(n)) return 'NaN';
    const totalMs = Math.max(0, Math.floor(n));
    const seconds = Math.floor(totalMs / 1000);
    const remainderMs = totalMs % 1000;
    return `${seconds}:${String(remainderMs).padStart(3, '0')}`;
}
