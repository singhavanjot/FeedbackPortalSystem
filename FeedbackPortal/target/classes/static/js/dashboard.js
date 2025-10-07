document.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('jwtToken');
    const userRole = localStorage.getItem('userRole');

    if (!token) {
        window.location.href = '/login.html';
        return;
    }

    const userView = document.getElementById('user-view');
    const adminControls = document.getElementById('admin-dashboard-controls');
    const adminFeedbackView = document.getElementById('admin-feedback-view');
    const adminCoursesView = document.getElementById('admin-courses-view');

    if (userRole === 'ROLE_ADMIN') {
        userView.style.display = 'none';
        adminControls.style.display = 'block';
        adminFeedbackView.style.display = 'block';
        setupAdminDashboard();
    } else {
        userView.style.display = 'block';
        adminControls.style.display = 'none';
        setupUserDashboard();
    }

    function setupAdminDashboard() {
        const showFeedbackBtn = document.getElementById('show-feedback-btn');
        const showCoursesBtn = document.getElementById('show-courses-btn');
        const addCourseForm = document.getElementById('add-course-form-admin');

        showFeedbackBtn.addEventListener('click', () => {
            adminFeedbackView.style.display = 'block';
            adminCoursesView.style.display = 'none';
            showFeedbackBtn.classList.add('active');
            showCoursesBtn.classList.remove('active');
            fetchAllFeedback();
        });
        showCoursesBtn.addEventListener('click', () => {
            adminFeedbackView.style.display = 'none';
            adminCoursesView.style.display = 'block';
            showFeedbackBtn.classList.remove('active');
            showCoursesBtn.classList.add('active');
            fetchAdminCourses();
        });
        addCourseForm.addEventListener('submit', handleAddCourse);
        fetchAllFeedback();
    }

    function fetchAllFeedback() {
        fetch('/api/feedback', { headers: { 'Authorization': `Bearer ${token}` } }).then(res => res.json()).then(data => {
            const list = document.getElementById('all-feedback-list');
            list.innerHTML = '';
            data.forEach(fb => {
                const item = document.createElement('div');
                item.className = 'list-item';
                item.textContent = `User: ${fb.studentId} | CourseID: ${fb.courseId} | Rating: ${fb.rating}/5 | Comment: ${fb.comments}`;
                list.appendChild(item);
            });
        });
    }

    function fetchAdminCourses() {
        fetch('/api/courses', { headers: { 'Authorization': `Bearer ${token}` } }).then(res => res.json()).then(data => {
            const list = document.getElementById('courses-list-admin');
            list.innerHTML = '';
            data.forEach(course => {
                const item = document.createElement('div');
                item.className = 'list-item';
                item.textContent = `${course.courseCode} - ${course.courseName}`;
                list.appendChild(item);
            });
        });
    }

    function handleAddCourse(event) {
        event.preventDefault();
        const courseCode = document.getElementById('course-code').value;
        const courseName = document.getElementById('course-name').value;
        const courseDescription = document.getElementById('course-description').value;
        const statusDiv = document.getElementById('add-course-status');
        fetch('/api/courses', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json', 'Authorization': `Bearer ${token}` },
            body: JSON.stringify({ courseCode, courseName, description: courseDescription })
        }).then(res => {
            if (!res.ok) throw new Error('Failed to add course');
            return res.json();
        }).then(() => {
            statusDiv.textContent = 'Course added successfully!';
            statusDiv.className = 'status-message success';
            event.target.reset();
            fetchAdminCourses();
        }).catch(err => {
            statusDiv.textContent = err.message;
            statusDiv.className = 'status-message error';
        });
    }

    function setupUserDashboard() {
        const feedbackList = document.getElementById('feedback-list');
        const feedbackStatus = document.getElementById('feedback-status');
        const addFeedbackBtn = document.getElementById('add-feedback-btn');
        const addFeedbackForm = document.getElementById('add-feedback-form');
        const courseSelect = document.getElementById('course-select');
        const feedbackRating = document.getElementById('feedback-rating');
        const feedbackComments = document.getElementById('feedback-comments');
        const addFeedbackStatus = document.getElementById('add-feedback-status');

        function fetchMyFeedback() {
            fetch('/api/feedback/my', { headers: { 'Authorization': `Bearer ${token}` } }).then(response => {
                if (!response.ok) throw new Error('Could not fetch feedback.');
                return response.json();
            }).then(feedbacks => {
                feedbackList.innerHTML = '';
                if (feedbacks.length === 0) { feedbackList.innerHTML = '<p>No feedback found.</p>'; } else {
                    feedbacks.forEach(fb => {
                        const fbDiv = document.createElement('div');
                        fbDiv.className = 'list-item';
                        fbDiv.textContent = `Course ID ${fb.courseId} | Rating: ${fb.rating}/5 | Comment: ${fb.comments}`;
                        feedbackList.appendChild(fbDiv);
                    });
                }
            });
        }

        function populateCoursesDropdown() {
            fetch('/api/courses').then(response => response.json()).then(courses => {
                courses.forEach(course => {
                    const option = document.createElement('option');
                    option.value = course.id;
                    option.textContent = course.courseName;
                    courseSelect.appendChild(option);
                });
            });
        }

        addFeedbackBtn.addEventListener('click', () => { addFeedbackForm.style.display = addFeedbackForm.style.display === 'none' ? 'block' : 'none'; });

        addFeedbackForm.addEventListener('submit', (event) => {
            event.preventDefault();
            const selectedCourseId = courseSelect.value;
            const rating = feedbackRating.value;
            const comments = feedbackComments.value;
            if (!selectedCourseId || !rating) {
                addFeedbackStatus.textContent = 'Please select a course and provide a rating.';
                addFeedbackStatus.classList.add('error');
                return;
            }
            fetch('/api/feedback', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json', 'Authorization': `Bearer ${token}` },
                body: JSON.stringify({ courseId: selectedCourseId, comments: comments, rating: parseInt(rating) })
            }).then(response => {
                if (!response.ok) return response.json().then(err => { throw new Error(err.message || 'Failed to submit feedback.') });
                return response.json();
            }).then(() => {
                addFeedbackStatus.textContent = 'Feedback submitted successfully!';
                addFeedbackStatus.className = 'status-message success';
                setTimeout(() => addFeedbackStatus.textContent = '', 3000);
                addFeedbackForm.reset();
                addFeedbackForm.style.display = 'none';
                fetchMyFeedback();
            }).catch(error => {
                addFeedbackStatus.textContent = `Error: ${error.message}`;
                addFeedbackStatus.className = 'status-message error';
            });
        });

        fetchMyFeedback();
        populateCoursesDropdown();
    }
});