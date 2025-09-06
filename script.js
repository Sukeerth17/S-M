// Global Application Data
const AppData = {
    currentUser: null,
    cart: [],
    products: [],
    users: [],
    categories: [
        { value: 'all', label: 'All Categories' },
        { value: 'electronics', label: 'Electronics' },
        { value: 'fashion', label: 'Fashion' },
        { value: 'home', label: 'Home & Garden' },
        { value: 'sports', label: 'Sports' }
    ]
};

// Sample Products Data
const sampleProducts = [
    {
        id: 1,
        title: "Premium Wireless Headphones",
        description: "High-quality wireless headphones with noise cancellation technology. Perfect for music lovers and professionals.",
        price: 299.99,
        category: "electronics",
        image: "placeholder",
        seller: "tech_store",
        createdAt: new Date('2025-01-01')
    },
    {
        id: 2,
        title: "Designer Cotton T-Shirt",
        description: "Comfortable premium cotton t-shirt with modern design. Available in multiple colors and sizes.",
        price: 49.99,
        category: "fashion",
        image: "placeholder",
        seller: "fashion_hub",
        createdAt: new Date('2025-01-02')
    },
    {
        id: 3,
        title: "Smart Home Speaker",
        description: "Voice-controlled smart speaker with AI assistant. Control your smart home devices with ease.",
        price: 199.99,
        category: "home",
        image: "placeholder",
        seller: "smart_home",
        createdAt: new Date('2025-01-03')
    },
    {
        id: 4,
        title: "Professional Running Shoes",
        description: "Lightweight running shoes designed for athletes. Superior comfort and performance guaranteed.",
        price: 159.99,
        category: "sports",
        image: "placeholder",
        seller: "sports_world",
        createdAt: new Date('2025-01-04')
    },
    {
        id: 5,
        title: "Automatic Coffee Maker",
        description: "Premium coffee maker with programmable timer and multiple brewing options for coffee enthusiasts.",
        price: 89.99,
        category: "home",
        image: "placeholder",
        seller: "kitchen_pro",
        createdAt: new Date('2025-01-05')
    },
    {
        id: 6,
        title: "Smartphone Protective Case",
        description: "Durable protective case for latest smartphone models. Drop protection with stylish design.",
        price: 24.99,
        category: "electronics",
        image: "placeholder",
        seller: "mobile_accessories",
        createdAt: new Date('2025-01-06')
    },
    {
        id: 7,
        title: "Yoga Mat Premium",
        description: "Non-slip premium yoga mat perfect for all types of yoga practice. Eco-friendly materials.",
        price: 45.99,
        category: "sports",
        image: "placeholder",
        seller: "fitness_gear",
        createdAt: new Date('2025-01-07')
    },
    {
        id: 8,
        title: "Wireless Gaming Mouse",
        description: "High-precision wireless gaming mouse with customizable RGB lighting and programmable buttons.",
        price: 79.99,
        category: "electronics",
        image: "placeholder",
        seller: "gaming_world",
        createdAt: new Date('2025-01-08')
    }
];

// Initialize Application
document.addEventListener('DOMContentLoaded', function() {
    console.log('App initializing...');
    initializeApp();
});

function initializeApp() {
    // Initialize sample data if none exists
    if (AppData.products.length === 0) {
        AppData.products = [...sampleProducts];
    }
    
    // Create demo user if none exists
    if (AppData.users.length === 0) {
        AppData.users.push({
            id: 1,
            username: 'demo_user',
            email: 'demo@EcoFinds.com',
            password: 'demo123',
            fullName: 'Demo User',
            createdAt: new Date(),
            purchases: []
        });
    }
    
    // Setup event listeners
    setupNavigation();
    setupSearch();
    setupUserMenu();
    
    // Update UI
    updateCartCount();
    updateUserInterface();
    
    // Load page-specific content
    loadPageContent();
    
    console.log('App initialized successfully');
}

// Navigation Functions
function setupNavigation() {
    // Mobile menu toggle
    const hamburger = document.getElementById('hamburger');
    const navMenu = document.querySelector('.nav-menu');
    
    if (hamburger && navMenu) {
        hamburger.addEventListener('click', function() {
            navMenu.classList.toggle('active');
            console.log('Mobile menu toggled');
        });
    }
    
    // Category card clicks
    const categoryCards = document.querySelectorAll('.category-card');
    categoryCards.forEach(card => {
        card.addEventListener('click', function() {
            const category = this.getAttribute('data-category');
            if (category) {
                window.location.href = `pages/shop.html?category=${category}`;
            }
        });
    });
}

function setupSearch() {
    const searchInput = document.querySelector('.search-input');
    const searchBtn = document.querySelector('.search-btn');
    
    if (searchInput) {
        searchInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                performSearch(this.value);
            }
        });
    }
    
    if (searchBtn) {
        searchBtn.addEventListener('click', function() {
            const query = searchInput ? searchInput.value : '';
            performSearch(query);
        });
    }
}

function setupUserMenu() {
    const userBtn = document.getElementById('userMenuBtn');
    const userDropdown = document.getElementById('userDropdown');
    const logoutBtn = document.getElementById('logoutBtn');
    
    if (userBtn && userDropdown) {
        userBtn.addEventListener('click', function(e) {
            e.stopPropagation();
            userDropdown.classList.toggle('active');
        });
        
        // Close dropdown when clicking outside
        document.addEventListener('click', function() {
            userDropdown.classList.remove('active');
        });
    }
    
    if (logoutBtn) {
        logoutBtn.addEventListener('click', function(e) {
            e.preventDefault();
            logout();
        });
    }
}

// Search Functions
function performSearch(query) {
    if (query.trim()) {
        const encodedQuery = encodeURIComponent(query.trim());
        window.location.href = `pages/shop.html?search=${encodedQuery}`;
    }
}

// User Authentication Functions
function login(email, password) {
    console.log('Attempting login for:', email);
    
    const user = AppData.users.find(u => u.email === email && u.password === password);
    
    if (user) {
        AppData.currentUser = {
            id: user.id,
            username: user.username,
            email: user.email,
            fullName: user.fullName,
            createdAt: user.createdAt
        };
        
        updateUserInterface();
        console.log('Login successful for:', email);
        return { success: true, message: 'Login successful!' };
    }
    
    console.log('Login failed for:', email);
    return { success: false, message: 'Invalid email or password' };
}

function register(userData) {
    console.log('Attempting registration for:', userData.email);
    
    // Check if email already exists
    if (AppData.users.find(u => u.email === userData.email)) {
        return { success: false, message: 'Email already registered' };
    }
    
    // Check if username already exists
    if (AppData.users.find(u => u.username === userData.username)) {
        return { success: false, message: 'Username already taken' };
    }
    
    // Create new user
    const newUser = {
        id: Date.now(),
        username: userData.username,
        email: userData.email,
        password: userData.password,
        fullName: userData.fullName,
        createdAt: new Date(),
        purchases: []
    };
    
    AppData.users.push(newUser);
    
    // Auto login after registration
    AppData.currentUser = {
        id: newUser.id,
        username: newUser.username,
        email: newUser.email,
        fullName: newUser.fullName,
        createdAt: newUser.createdAt
    };
    
    updateUserInterface();
    console.log('Registration successful for:', userData.email);
    return { success: true, message: 'Account created successfully!' };
}

function logout() {
    console.log('User logged out');
    AppData.currentUser = null;
    AppData.cart = []; // Clear cart on logout
    updateUserInterface();
    updateCartCount();
    showNotification('Logged out successfully!', 'success');
    
    // Redirect if on protected page
    const currentPage = window.location.pathname.split('/').pop();
    const protectedPages = ['profile.html', 'my-listings.html', 'previous-purchases.html'];
    
    if (protectedPages.includes(currentPage)) {
        window.location.href = '../index.html';
    }
}

function updateUserInterface() {
    const userLoggedIn = document.querySelector('.user-logged-in');
    const loginLinks = document.querySelectorAll('.user-dropdown > a');
    
    if (AppData.currentUser) {
        // Show logged-in menu items
        if (userLoggedIn) {
            userLoggedIn.style.display = 'block';
        }
        
        // Hide login/signup links
        loginLinks.forEach(link => {
            if (link.href.includes('login.html') || link.href.includes('signup.html')) {
                link.style.display = 'none';
            }
        });
    } else {
        // Hide logged-in menu items
        if (userLoggedIn) {
            userLoggedIn.style.display = 'none';
        }
        
        // Show login/signup links
        loginLinks.forEach(link => {
            if (link.href.includes('login.html') || link.href.includes('signup.html')) {
                link.style.display = 'block';
            }
        });
    }
}

// Cart Functions
function addToCart(productId) {
    if (!AppData.currentUser) {
        showNotification('Please login to add items to cart', 'error');
        return false;
    }
    
    const product = AppData.products.find(p => p.id === parseInt(productId));
    if (!product) {
        showNotification('Product not found', 'error');
        return false;
    }
    
    const existingItem = AppData.cart.find(item => item.productId === parseInt(productId));
    
    if (existingItem) {
        existingItem.quantity += 1;
    } else {
        AppData.cart.push({
            productId: parseInt(productId),
            quantity: 1,
            addedAt: new Date()
        });
    }
    
    updateCartCount();
    showNotification(`${product.title} added to cart!`, 'success');
    console.log('Product added to cart:', product.title);
    return true;
}

function removeFromCart(productId) {
    const initialLength = AppData.cart.length;
    AppData.cart = AppData.cart.filter(item => item.productId !== parseInt(productId));
    
    if (AppData.cart.length < initialLength) {
        updateCartCount();
        showNotification('Product removed from cart', 'success');
        return true;
    }
    return false;
}

function updateCartQuantity(productId, quantity) {
    const item = AppData.cart.find(item => item.productId === parseInt(productId));
    
    if (item) {
        if (quantity <= 0) {
            removeFromCart(productId);
        } else {
            item.quantity = parseInt(quantity);
            updateCartCount();
        }
        return true;
    }
    return false;
}

function getCartItems() {
    return AppData.cart.map(cartItem => {
        const product = AppData.products.find(p => p.id === cartItem.productId);
        return {
            ...cartItem,
            product: product
        };
    });
}

function getCartTotal() {
    return AppData.cart.reduce((total, item) => {
        const product = AppData.products.find(p => p.id === item.productId);
        return total + (product ? product.price * item.quantity : 0);
    }, 0);
}

function updateCartCount() {
    const cartCountElement = document.querySelector('.cart-count');
    if (cartCountElement) {
        const totalItems = AppData.cart.reduce((sum, item) => sum + item.quantity, 0);
        cartCountElement.textContent = totalItems;
    }
}

// Product Functions
function createProductCard(product) {
    return `
        <div class="product-card" data-product-id="${product.id}">
            <div class="product-image">
                <i class="fas fa-image"></i>
            </div>
            <div class="product-info">
                <h3 class="product-title">${escapeHtml(product.title)}</h3>
                <p class="product-price">$${product.price.toFixed(2)}</p>
                <p class="product-category">${getCategoryLabel(product.category)}</p>
                <div class="product-actions">
                    <button class="btn btn-primary add-to-cart-btn" data-product-id="${product.id}">
                        <i class="fas fa-shopping-cart"></i> Add to Cart
                    </button>
                    <button class="btn btn-secondary view-details-btn" onclick="viewProduct(${product.id})">
                        <i class="fas fa-eye"></i> View
                    </button>
                </div>
            </div>
        </div>
    `;
}

function loadFeaturedProducts() {
    const grid = document.getElementById('featuredProductsGrid');
    if (!grid) return;
    
    const featuredProducts = AppData.products.slice(0, 6);
    grid.innerHTML = featuredProducts.map(product => createProductCard(product)).join('');
    
    // Add event listeners
    setupProductCardListeners(grid);
}

function setupProductCardListeners(container) {
    // Product card clicks
    container.querySelectorAll('.product-card').forEach(card => {
        card.addEventListener('click', function(e) {
            if (!e.target.closest('.btn')) {
                const productId = this.getAttribute('data-product-id');
                viewProduct(productId);
            }
        });
    });
    
    // Add to cart buttons
    container.querySelectorAll('.add-to-cart-btn').forEach(btn => {
        btn.addEventListener('click', function(e) {
            e.stopPropagation();
            const productId = this.getAttribute('data-product-id');
            addToCart(productId);
        });
    });
}

function viewProduct(productId) {
    const basePath = window.location.pathname.includes('/pages/') ? '' : 'pages/';
    window.location.href = `${basePath}product-detail.html?id=${productId}`;
}

function getCategoryLabel(categoryValue) {
    const category = AppData.categories.find(c => c.value === categoryValue);
    return category ? category.label : categoryValue.charAt(0).toUpperCase() + categoryValue.slice(1);
}

// Page Content Loading
function loadPageContent() {
    // Check current page and load appropriate content
    const currentPage = window.location.pathname.split('/').pop();
    
    switch(currentPage) {
        case 'index.html':
        case '':
            loadFeaturedProducts();
            setupNewsletterForm();
            break;
        case 'shop.html':
            // Shop page specific content will be handled by shop page script
            break;
        default:
            // Other pages will handle their own content
            break;
    }
}

function setupNewsletterForm() {
    const newsletterForm = document.querySelector('.newsletter-form');
    if (newsletterForm) {
        newsletterForm.addEventListener('submit', function(e) {
            e.preventDefault();
            const email = this.querySelector('input[type="email"]').value;
            if (email) {
                showNotification('Thank you for subscribing to our newsletter!', 'success');
                this.reset();
            }
        });
    }
}

// Utility Functions
function showNotification(message, type = 'info') {
    // Remove existing notifications
    const existing = document.querySelectorAll('.notification');
    existing.forEach(notif => notif.remove());
    
    // Create notification element
    const notification = document.createElement('div');
    notification.className = `notification notification-${type}`;
    
    const colors = {
        success: '#10b981',
        error: '#ef4444',
        info: '#6366f1',
        warning: '#f59e0b'
    };
    
    notification.style.cssText = `
        position: fixed;
        top: 100px;
        right: 20px;
        background: ${colors[type] || colors.info};
        color: white;
        padding: 1rem 1.5rem;
        border-radius: 8px;
        box-shadow: 0 4px 15px rgba(0,0,0,0.2);
        z-index: 9999;
        transform: translateX(400px);
        transition: transform 0.3s ease;
        max-width: 300px;
        font-size: 0.9rem;
        display: flex;
        align-items: center;
        gap: 1rem;
    `;
    
    notification.innerHTML = `
        <span>${escapeHtml(message)}</span>
        <button onclick="this.parentElement.remove()" style="
            background: none;
            border: none;
            color: white;
            cursor: pointer;
            font-size: 1.2rem;
            padding: 0;
            margin-left: auto;
        ">&times;</button>
    `;
    
    document.body.appendChild(notification);
    
    // Animate in
    setTimeout(() => {
        notification.style.transform = 'translateX(0)';
    }, 100);
    
    // Auto remove after 5 seconds
    setTimeout(() => {
        if (notification.parentElement) {
            notification.style.transform = 'translateX(400px)';
            setTimeout(() => notification.remove(), 300);
        }
    }, 5000);
}

function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

function formatDate(date) {
    return new Date(date).toLocaleDateString('en-US', {
        year: 'numeric',
        month: 'short',
        day: 'numeric'
    });
}

function formatPrice(price) {
    return new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD'
    }).format(price);
}

// Global API for other pages
window.EcoFindsAPI = {
    // Data access
    getCurrentUser: () => AppData.currentUser,
    getProducts: () => AppData.products,
    getCart: () => AppData.cart,
    getCategories: () => AppData.categories,
    
    // Authentication
    login: login,
    register: register,
    logout: logout,
    
    // Cart operations
    addToCart: addToCart,
    removeFromCart: removeFromCart,
    updateCartQuantity: updateCartQuantity,
    getCartItems: getCartItems,
    getCartTotal: getCartTotal,
    updateCartCount: updateCartCount,
    
    // Product operations
    createProductCard: createProductCard,
    getCategoryLabel: getCategoryLabel,
    viewProduct: viewProduct,
    
    // Utilities
    showNotification: showNotification,
    formatDate: formatDate,
    formatPrice: formatPrice,
    escapeHtml: escapeHtml
};

// Legacy support for existing code
window.appFunctions = window.EcoFindsAPI;

console.log('EcoFinds JavaScript loaded successfully');