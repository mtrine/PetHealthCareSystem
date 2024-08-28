const API_BASE_URL = 'http://localhost:8080';

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
            // Attempt to refresh token
            const refreshResponse = await fetch(`${API_BASE_URL}/v1/auth/refresh`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ token: accessToken })
            });
            
            if (refreshResponse.ok) {
                const data = await refreshResponse.json();
                // Store new tokens
                localStorage.setItem('authToken', data.result.token);
                options.headers['Authorization'] = `Bearer ${data.result.token}`;
                response = await fetch(url, options);
            } else {
                // Handle refresh token failure (e.g., logout user)
                console.error('Refresh token failed');
                localStorage.removeItem('authToken');
                // Redirect to login or show an error message
                return;
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
