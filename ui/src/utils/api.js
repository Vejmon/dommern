export const postData = async (url = '', data = {}) => {
    return await fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data),
    }).then(response => response.json())
        .catch(e => {
            console.error("Error in postData:", e);
            throw e;
        });
}