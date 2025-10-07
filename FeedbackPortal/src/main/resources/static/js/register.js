document.addEventListener('DOMContentLoaded', () => {
    const registerForm = document.getElementById('register-form');
    const registerStatus = document.getElementById('register-status');

    registerForm.addEventListener('submit', (event) => {
        event.preventDefault();
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const role = document.getElementById('role').value;

        // Clear previous status messages
        registerStatus.textContent = '';
        registerStatus.className = 'status-message';

        fetch('/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password, role }),
        })
        .then(response => {
            if (!response.ok) {
                // If the server responds with an error (e.g., 400 Bad Request)
                return response.json().then(err => { throw new Error(err.error || 'Registration failed') });
            }
            return response.json();
        })
        .then(data => {
            registerStatus.textContent = 'Registration successful! You can now log in.';
            registerStatus.classList.add('success');
            registerForm.reset(); // Clear the form fields
        })
        .catch(error => {
            registerStatus.textContent = `Error: ${error.message}`;
            registerStatus.classList.add('error');
        });
    });
});