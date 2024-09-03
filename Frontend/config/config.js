// config.js
const API_BASE_URL = 'http://localhost:8080';

let isRefreshing = false; // To track if a token refresh is in progress
let refreshSubscribers = []; // List of functions to call once the token is refreshed

async function fetchWithToken(url, options = {}) {
    const accessToken = localStorage.getItem('authToken');

    // Set Authorization header with the access token
    options.headers = {
        ...options.headers,
        'Authorization': `Bearer ${accessToken}`
    };

    try {
        let response = await fetch(url, options);

        if (response.status === 401) { // Unauthorized
            console.log('Old Token:', accessToken);

            // If a refresh is already in progress, wait for it to complete
            if (isRefreshing) {
                return new Promise((resolve) => {
                    refreshSubscribers.push((newToken) => {
                        options.headers['Authorization'] = `Bearer ${newToken}`;
                        resolve(fetch(url, options).then(res => res.json()));
                    });
                });
            }

            isRefreshing = true;

            try {
                const refreshResponse = await fetch(`${API_BASE_URL}/v1/auth/refresh`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ token: `${accessToken}` })
                });

                if (refreshResponse.ok) {
                    const data = await refreshResponse.json();
                    // Store new tokens
                    console.log("New Token Data:", data);
                    localStorage.setItem('authToken', data.result.token);

                    // Call all subscribers waiting for the new token
                    refreshSubscribers.forEach(callback => callback(data.result.token));
                    refreshSubscribers = []; // Clear subscribers list
                    isRefreshing = false;

                    // Retry the original request with the new token
                    options.headers['Authorization'] = `Bearer ${data.result.token}`;
                    return fetch(url, options).then(res => res.json());
                } else {
                    // Handle refresh token failure (e.g., logout user)
                    console.error('Refresh token failed:', await refreshResponse.json());
                    // Redirect to login or show an error message
                    return;
                }
            } catch (refreshError) {
                console.error('Refresh error:', refreshError);
            } finally {
                isRefreshing = false;
            }
        }

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        return await response.json();
    } catch (error) {
        console.error('Fetch error:', error);
    }
}