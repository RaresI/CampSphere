# 🏕️ CampSphere

**Educational Camp Management Platform**

CampSphere is a full-stack web application designed to simplify the management of educational camps. It provides dedicated portals for parents, children, instructors, administrators, and camp owners with modern, role-specific dashboards.

---

## ✨ Features

### 🔐 Authentication & Roles
- Secure login system with Spring Security
- Five distinct user roles with specialized functionality:
  - **Parent**: Register and manage children, select camps, track progress
  - **Child**: Upload feedback photos, submit camp reviews
  - **Instructor**: View assigned activities, manage daily schedules
  - **Admin**: Manage camps, groups, activities, locations, and registrations
  - **Owner**: Full control with analytics, dashboards, and leaderboards

### 👨‍👩‍👧 Parent Portal
- Register as a parent (email + phone login)
- Register multiple children under one account
- Browse and register for available camps
- View child progress, scores, and milestones
- Track camp registrations and optional trips

### 👧 Child Portal
- Login with credentials created by parent
- Upload camp photos with feedback
- View personal camp schedule
- Submit feedback and reviews

### 👨‍🏫 Instructor Portal
- View assigned activities and groups
- Manage daily activity schedules
- Track activity completion

### 🛠️ Admin Portal
- Create and manage camps, activities, locations, and trips
- Assign instructors to activities
- Generate groups automatically based on age and activity slots
- View comprehensive feedback from children
- Monitor costs and summaries

### 📊 Owner Portal
- Full system oversight with analytics dashboards
- Assign scores to children for gamification
- View leaderboards across all camps
- Manage activity schedules and instructor assignments
- Track overall camp performance

---

## 🚀 Tech Stack

- **Backend**: Java 17, Spring Boot 3.x, Spring Security
- **Database**: PostgreSQL 15 (containerized with Docker)
- **Frontend**: HTML5, CSS3, JavaScript (ES6+), Bootstrap 5.3.2
- **Design**: Custom modern design system with Inter font, gradient backgrounds, animations
- **Build Tool**: Maven 3.8+
- **Containerization**: Docker & Docker Compose

---

## 📋 Prerequisites

Before running CampSphere, ensure you have the following installed:

- **Java 17+** ([Download](https://adoptium.net/))
- **Maven 3.8+** (or use included Maven Wrapper `./mvnw`)
- **Docker Desktop** ([Download](https://www.docker.com/products/docker-desktop))
- **Git** ([Download](https://git-scm.com/downloads))

---

## 🔧 Installation & Setup

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/yourusername/CampSphere.git
cd CampSphere
```

### 2️⃣ Start PostgreSQL Database

```bash
docker-compose up -d
```

This will start a PostgreSQL 15 container named `campsphere-postgres` on port 5432 with:
- Database: `campsphere`
- Username: `postgres`
- Password: `postgres`

### 3️⃣ Create Database Schema

The application will automatically create tables on first run using JPA/Hibernate. To populate sample data:

**On macOS/Linux:**
```bash
chmod +x setup-database.sh
./setup-database.sh
```

**On Windows:**
```cmd
setup-database.bat
```

This will populate the database with sample camps, locations, activities, instructors, parents, and children.

### 4️⃣ Build and Run the Application

**Using Maven Wrapper (recommended):**
```bash
./mvnw clean install
./mvnw spring-boot:run
```

**Or using Maven:**
```bash
mvn clean install
mvn spring-boot:run
```

The application will start on **http://localhost:8080**

---

## 🎯 Usage Guide

### Accessing Different Portals

Once the application is running, navigate to:

- **Home Page**: http://localhost:8080
- **Parent Portal**: http://localhost:8080/parent-login.html
- **Child Portal**: http://localhost:8080/login-child.html
- **Instructor Portal**: http://localhost:8080/login.html
- **Admin Portal**: http://localhost:8080/admin-login.html
- **Owner Portal**: http://localhost:8080/owner-login.html

### Sample Login Credentials

After running the setup script, you can use these sample accounts:

**Parent:**
- Email: `wdavis@gmail.com`
- Phone: `(080)280-6271`

**Child:**
- Email: `preeves@yahoo.com`
- Password: `password123`

**Admin/Owner:**
- Check the `init-ecamp-data.sql` file for admin and owner credentials

---

## 📂 Project Structure

```
CampSphere/
├── src/
│   ├── main/
│   │   ├── java/com/ecamp/          # Backend Java code
│   │   │   ├── controller/          # REST API endpoints
│   │   │   ├── model/               # JPA entities
│   │   │   ├── repository/          # Data access layer
│   │   │   └── service/             # Business logic
│   │   └── resources/
│   │       ├── static/              # Frontend HTML/CSS/JS
│   │       │   ├── assets/          # Images, fonts
│   │       │   └── *.html           # Portal pages
│   │       └── application.properties
│   └── test/                        # Unit tests
├── docker-compose.yml               # PostgreSQL container config
├── init-ecamp-data.sql             # Sample data population
├── setup-database.sh/bat           # Database setup scripts
└── pom.xml                         # Maven dependencies

```

---

## 🎨 Design System

CampSphere features a modern design system with:

- **Color Themes**: 
  - Blue gradient for parents 🔵
  - Green gradient for children 🟢
  - Orange gradient for admins 🟠
  - Purple gradient for owners 🟣
- **Typography**: Inter font family
- **Components**: Modern cards, animated backgrounds, gradient buttons
- **Animations**: Smooth hover effects, floating patterns, fade-in transitions
- **Accessibility**: Proper label-input associations, semantic HTML

---

## 🐳 Docker Commands

**Start database:**
```bash
docker-compose up -d
```

**Stop database:**
```bash
docker-compose down
```

**View logs:**
```bash
docker logs campsphere-postgres
```

**Access PostgreSQL CLI:**
```bash
docker exec -it campsphere-postgres psql -U postgres -d campsphere
```

---

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

---

## 📝 License

This project is licensed under the MIT License.

---

## 👨‍💻 Author

Created with ❤️ for modern camp management

---

## 🙋 Support

For questions or issues, please open an issue on GitHub.

