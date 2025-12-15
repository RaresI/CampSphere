/**
 * CampSphere - Universal Navbar Component
 * Renders role-specific navigation based on user session
 */

function renderNavbar() {
    const userRole = sessionStorage.getItem('userRole');
    const userName = sessionStorage.getItem('userName') || 'User';
    
    if (!userRole) {
        // Not logged in, redirect to home
        if (!window.location.pathname.includes('login') && 
            !window.location.pathname.includes('register') && 
            window.location.pathname !== '/' && 
            !window.location.pathname.includes('index.html')) {
            window.location.href = '/index.html';
        }
        return;
    }
    
    const navbarContainer = document.getElementById('navbar-container');
    if (!navbarContainer) return;
    
    const navLinks = getNavLinksForRole(userRole);
    
    navbarContainer.innerHTML = `
        <nav class="navbar navbar-expand-lg navbar-modern">
            <div class="container-fluid">
                <a class="navbar-brand" href="${getRoleHomePage(userRole)}">
                    <span class="brand-icon">🏕️</span>
                    <span class="brand-text">CampSphere</span>
                    <span class="role-badge">${getRoleDisplayName(userRole)}</span>
                </a>
                
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                    <span class="navbar-toggler-icon"></span>
                </button>
                
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav me-auto">
                        ${navLinks.map(link => `
                            <li class="nav-item">
                                <a class="nav-link ${isActivePage(link.url) ? 'active' : ''}" href="${link.url}">
                                    ${link.icon} ${link.label}
                                </a>
                            </li>
                        `).join('')}
                    </ul>
                    
                    <div class="navbar-user">
                        <span class="user-greeting">👋 ${userName}</span>
                        <button class="btn btn-logout" onclick="handleLogout()">
                            🚪 Logout
                        </button>
                    </div>
                </div>
            </div>
        </nav>
    `;
}

function getNavLinksForRole(role) {
    const links = {
        'PARENT': [
            { url: '/parent-dashboard.html', label: 'Dashboard', icon: '📊' },
            { url: '/parent-children.html', label: 'My Children', icon: '👶' },
            { url: '/parent-registrations.html', label: 'Register for Camp', icon: '📝' },
        ],
        'ADMIN': [
            { url: '/admin-dashboard.html', label: 'Dashboard', icon: '📊' },
            { url: '/admin-groups.html', label: 'Groups', icon: '👥' },
            { url: '/admin-locations.html', label: 'Locations', icon: '📍' },
            { url: '/admin-feedback.html', label: 'Feedback', icon: '💬' },
            { url: '/admin-summary.html', label: 'Summary', icon: '📈' },
        ],
        'OWNER': [
            { url: '/owner-dashboard.html', label: 'Dashboard', icon: '📊' },
            { url: '/owner-assign-activity.html', label: 'Assign Activities', icon: '🎯' },
            { url: '/owner-assign-score.html', label: 'Assign Scores', icon: '⭐' },
            { url: '/owner-leaderboard.html', label: 'Leaderboard', icon: '🏆' },
            { url: '/owner-view-schedule.html', label: 'View Schedule', icon: '📅' },
        ],
        'CHILD': [
            { url: '/child-feedback.html', label: 'My Feedback', icon: '�' },
        ]
    };
    
    return links[role] || [];
}

function getRoleHomePage(role) {
    const homePages = {
        'PARENT': '/parent-dashboard.html',
        'ADMIN': '/admin-dashboard.html',
        'OWNER': '/owner-dashboard.html',
        'CHILD': '/child-feedback.html'
    };
    return homePages[role] || '/index.html';
}

function getRoleDisplayName(role) {
    const names = {
        'PARENT': 'Parent',
        'ADMIN': 'Admin',
        'OWNER': 'Camp Owner',
        'CHILD': 'Camper'
    };
    return names[role] || 'User';
}

function isActivePage(url) {
    const currentPath = window.location.pathname;
    return currentPath.endsWith(url) || currentPath === url;
}

function handleLogout() {
    showLogoutModal();
}

function showLogoutModal() {
    // Create modal if it doesn't exist
    let modal = document.getElementById('logoutModal');
    if (!modal) {
        modal = document.createElement('div');
        modal.id = 'logoutModal';
        modal.className = 'logout-modal';
        modal.innerHTML = `
            <div class="logout-modal-content">
                <div class="logout-icon">🚪</div>
                <h3 class="fw-bold mb-2" style="color: #1e293b;">Logout Confirmation</h3>
                <p class="text-muted mb-4">Are you sure you want to logout?</p>
                <div class="d-flex gap-2">
                    <button onclick="confirmLogout()" class="btn btn-danger flex-fill py-2">
                        Yes, Logout
                    </button>
                    <button onclick="closeLogoutModal()" class="btn btn-secondary flex-fill py-2">
                        Cancel
                    </button>
                </div>
            </div>
        `;
        document.body.appendChild(modal);
        
        // Add styles if not already present
        if (!document.getElementById('logoutModalStyles')) {
            const style = document.createElement('style');
            style.id = 'logoutModalStyles';
            style.textContent = `
                .logout-modal {
                    display: none;
                    position: fixed;
                    top: 0;
                    left: 0;
                    width: 100%;
                    height: 100%;
                    background: rgba(0, 0, 0, 0.5);
                    backdrop-filter: blur(8px);
                    z-index: 10000;
                    animation: fadeIn 0.3s ease;
                }
                
                .logout-modal.show {
                    display: flex;
                    justify-content: center;
                    align-items: center;
                }
                
                .logout-modal-content {
                    background: white;
                    border-radius: 1.5rem;
                    padding: 2.5rem;
                    max-width: 450px;
                    width: 90%;
                    text-align: center;
                    box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
                    animation: slideUp 0.3s ease;
                }
                
                .logout-icon {
                    width: 70px;
                    height: 70px;
                    background: linear-gradient(135deg, #f59e0b 0%, #f97316 100%);
                    border-radius: 50%;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    margin: 0 auto 1.5rem;
                    font-size: 2.5rem;
                }
                
                @keyframes fadeIn {
                    from { opacity: 0; }
                    to { opacity: 1; }
                }
                
                @keyframes slideUp {
                    from { 
                        opacity: 0;
                        transform: translateY(30px);
                    }
                    to { 
                        opacity: 1;
                        transform: translateY(0);
                    }
                }
            `;
            document.head.appendChild(style);
        }
    }
    modal.classList.add('show');
}

function closeLogoutModal() {
    const modal = document.getElementById('logoutModal');
    if (modal) {
        modal.classList.remove('show');
    }
}

function confirmLogout() {
    // Clear session storage
    sessionStorage.clear();
    
    // Clear local storage (if any)
    localStorage.clear();
    
    // Redirect to home page
    window.location.href = '/index.html';
}

/**
 * Check if user has access to current page based on their role
 */
function checkPageAccess() {
    const userRole = sessionStorage.getItem('userRole');
    const currentPath = window.location.pathname;
    
    // Public pages that don't require authentication
    const publicPages = [
        '/', '/index.html', 
        '/parent-login.html', '/parent-register.html',
        '/admin-login.html', '/owner-login.html', '/login-child.html',
        '/login.html', '/register.html', '/camp_register.html', '/child_register.html'
    ];
    
    if (publicPages.some(page => currentPath.endsWith(page) || currentPath === page)) {
        return; // Allow access to public pages
    }
    
    // If not logged in, redirect to home
    if (!userRole) {
        alert('Please login to access this page.');
        window.location.href = '/index.html';
        return;
    }
    
    // Define role-based page access
    const rolePages = {
        'PARENT': ['/parent-dashboard.html', '/parent-children.html', '/parent-registrations.html'],
        'ADMIN': ['/admin-dashboard.html', '/admin-groups.html', '/admin-locations.html', 
                  '/admin-feedback.html', '/admin-summary.html'],
        'OWNER': ['/owner-dashboard.html', '/owner-assign-activity.html', '/owner-assign-score.html',
                  '/owner-leaderboard.html', '/owner-view-schedule.html', '/assign-schedule.html', '/assign-score.html'],
        'CHILD': ['/child-feedback.html']
    };
    
    const allowedPages = rolePages[userRole] || [];
    const hasAccess = allowedPages.some(page => currentPath.endsWith(page));
    
    if (!hasAccess) {
        alert(`Access denied! You are logged in as ${getRoleDisplayName(userRole)}. You don't have permission to access this page.`);
        window.location.href = getRoleHomePage(userRole);
    }
}

// Initialize navbar when DOM is ready
document.addEventListener('DOMContentLoaded', function() {
    checkPageAccess();
    renderNavbar();
});
