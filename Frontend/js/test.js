const API_BASE_URL = 'http://localhost:8080';


const refreshResponse = fetch(`${API_BASE_URL}/v1/auth/refresh`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    
                },
                body: JSON.stringify({ token: "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkBncm91cDA3LmNvbSIsInNjb3BlIjoiQURNSU4iLCJpc3MiOiJsb2NhbGhvc3Q6ODA4MCIsImV4cCI6MTcyNTEyNTA1MSwiaWF0IjoxNzI1MTI1MDIxLCJ1c2VySWQiOiJlZTU3NGE4YS02M2M5LTRmNWItOWE3NC0wZjI0NjIzNjUzNzYiLCJqdGkiOiIwNWI4M2MwYi0zYWI3LTQyYWMtYjM3NS0zYTYyNTIzY2M5MTEifQ.Gf7fdTFJNkxiGQQeKnYm_LEB-KtNraAjcBfflHCHlPjC-u1et0yqJybh86DBTCvTsm6AdA8jDXtCVk43LCBLIw" })
            });
            
            if (refreshResponse.ok) {
                const data =  refreshResponse.json();
                // Store new tokens
                console.log("New Token Data:", data);
            } else {
                // Handle refresh token failure (e.g., logout user)
                console.error('Refresh token failed:',  );
                // Redirect to login or show an error message
               
            }
        
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
            
        
