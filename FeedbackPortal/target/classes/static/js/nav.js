document.addEventListener('DOMContentLoaded', () => {
    const header = document.getElementById('main-header');
    const token = localStorage.getItem('jwtToken');
    
    let navLinks = '';
    const currentPage = window.location.pathname;

    if (token) {
        // --- User is Logged In ---
        navLinks = ''; 
        if (currentPage !== '/courses.html') {
           
        }
        if (currentPage !== '/dashboard.html') {
            navLinks += `<a href="/dashboard.html">Dashboard</a>`;
        }
        navLinks += `<button id="logout-btn" class="btn-logout">Logout</button>`;
    } else {
        // --- User is Logged Out ---
        // ✅ NAYI LOGIC YAHAN HAI
        if (currentPage === '/' || currentPage === '/index.html') {
            // Landing page par sirf "Courses" ka link
            
        } else if (currentPage === '/register.html') {
            // Register page par sirf "Home" aur "Login"
            navLinks = `
                <a href="/">Home</a>
                <a href="/login.html">Login</a>
            `;
        } else if (currentPage === '/login.html') {
            // Login page par sirf "Home" aur "Register"
            navLinks = `
                <a href="/">Home</a>
                <a href="/register.html">Register</a>
            `;
        } else {
            // Baaki sabhi logged-out pages ke liye (jaise courses.html)
            navLinks = `
                <a href="/">Home</a>
                <a href="/login.html">Login</a>
                <a href="/register.html">Register</a>
            `;
        }
    }

    const navbarHTML = `<nav class="navbar">${navLinks}</nav>`;
    if (header) {
        header.innerHTML = navbarHTML;
    }

    const logoutBtn = document.getElementById('logout-btn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', () => {
            localStorage.clear();
            window.location.href = '/'; 
        });
    }
});