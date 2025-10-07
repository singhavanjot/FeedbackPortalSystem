document.addEventListener('DOMContentLoaded', () => {
    const coursesList = document.getElementById('courses-list');

    function fetchCourses() {
        fetch('/api/courses')
            .then(response => response.json())
            .then(courses => {
                coursesList.innerHTML = '';
                if (courses.length === 0) {
                    coursesList.innerHTML = '<p>No courses are available at this time.</p>';
                    return;
                }
                courses.forEach(course => {
                    const courseDiv = document.createElement('div');
                    courseDiv.className = 'list-item';
                    courseDiv.textContent = `${course.courseCode} - ${course.courseName}: ${course.description}`;
                    coursesList.appendChild(courseDiv);
                });
            })
            .catch(error => {
                console.error('Error fetching courses:', error);
                coursesList.innerHTML = '<p style="color: red;">Could not fetch courses.</p>';
            });
    }

    fetchCourses();
});