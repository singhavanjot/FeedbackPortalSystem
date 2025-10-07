document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('login-form');
    const loginStatus = document.getElementById('login-status');

    loginForm.addEventListener('submit', (event) => {
        event.preventDefault();
        const username = event.target.username.value;
        const password = event.target.password.value;

        fetch('/auth/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password }),
        })
		// ...
		.then(response => {
		    if (!response.ok) throw new Error('Login failed!');
		    return response.json();
		})
		.then(data => {
		    // ✅ ADD THIS LINE to see the response in the console
		    console.log('Data received from backend:', data); 

		    localStorage.setItem('jwtToken', data.token);
		    localStorage.setItem('userRole', data.role);
		    window.location.href = '/dashboard.html'; 
		})
		// ...
        .catch(error => {
            loginStatus.textContent = 'Login failed. Please check credentials.';
            loginStatus.style.color = 'red';
        });
    });
});