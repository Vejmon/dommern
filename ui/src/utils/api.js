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