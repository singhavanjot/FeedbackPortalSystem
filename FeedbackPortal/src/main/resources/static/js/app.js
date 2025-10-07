document.addEventListener('DOMContentLoaded', () => {
    // Get references to DOM elements
    const loginForm = document.getElementById('login-form');
    const loginStatus = document.getElementById('login-status');
    const coursesList = document.getElementById('courses-list');
    const fetchFeedbackBtn = document.getElementById('fetch-feedback-btn');
    const feedbackList = document.getElementById('feedback-list');
    const feedbackStatus = document.getElementById('feedback-status');

    // --- Public API Call: Fetch Courses on page load ---
    function fetchCourses() {
        fetch('/api/courses')
            .then(response => response.json())
            .then(courses => {
                coursesList.innerHTML = ''; // Clear previous list
                courses.forEach(course => {
                    const courseDiv = document.createElement('div');
                    courseDiv.className = 'list-item';
                    courseDiv.textContent = `${course.name}: ${course.description}`;
                    coursesList.appendChild(courseDiv);
                });
            })
            .catch(error => {
                coursesList.innerHTML = '<p style="color: red;">Could not fetch courses.</p>';
                console.error('Error fetching courses:', error);
            });
    }

    // --- Authentication: Handle Login ---
    loginForm.addEventListener('submit', (event) => {
        event.preventDefault(); // Prevent form from submitting the traditional way
        const username = event.target.username.value;
        const password = event.target.password.value;

        fetch('/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password }),
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Login failed!');
            }
            return response.json();
        })
        .then(data => {
            // Save the token and role to localStorage
            localStorage.setItem('jwtToken', data.token);
            localStorage.setItem('userRole', data.role);
            loginStatus.textContent = `Logged in successfully! Role: ${data.role}`;
            loginStatus.style.color = 'green';
        })
        .catch(error => {
            loginStatus.textContent = 'Login failed. Please check credentials.';
            loginStatus.style.color = 'red';
            console.error('Login error:', error);
        });
    });

    // --- Protected API Call: Fetch User Feedback ---
    fetchFeedbackBtn.addEventListener('click', () => {
        const token = localStorage.getItem('jwtToken');
        
        if (!token) {
            feedbackStatus.textContent = 'You must be logged in to fetch feedback.';
            feedbackStatus.style.color = 'red';
            return;
        }

        fetch('/api/feedback/my', {
            method: 'GET',
            headers: {
                // Include the JWT token in the Authorization header
                'Authorization': `Bearer ${token}`,
            },
        })
        .then(response => {
            if (response.status === 403) { // Forbidden
                 throw new Error('Access denied. You might not have the correct role (USER).');
            }
            if (!response.ok) {
                throw new Error('Failed to fetch feedback.');
            }
            return response.json();
        })
        .then(feedbacks => {
            feedbackList.innerHTML = ''; // Clear previous list
            feedbackStatus.textContent = '';
            if (feedbacks.length === 0) {
                 feedbackList.innerHTML = '<p>No feedback found.</p>';
            } else {
                feedbacks.forEach(fb => {
                    const fbDiv = document.createElement('div');
                    fbDiv.className = 'list-item';
                    fbDiv.textContent = `Course ID ${fb.courseId}: ${fb.comment}`;
                    feedbackList.appendChild(fbDiv);
                });
            }
        })
        .catch(error => {
            feedbackStatus.textContent = error.message;
            feedbackStatus.style.color = 'red';
            console.error('Error fetching feedback:', error);
        });
    });

    // Initial load
    fetchCourses();
});