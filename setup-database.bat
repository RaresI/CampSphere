@echo off
REM ==========================================
REM Database Setup Script - E-Camp Project (Windows)
REM ==========================================
REM This script sets up the complete database with all data including
REM the new children and fixed feedback system

setlocal enabledelayedexpansion

echo ========================================
echo 🚀 E-Camp Database Setup Script
echo ========================================
echo.

REM Database configuration
if "%DB_HOST%"=="" set DB_HOST=localhost
if "%DB_PORT%"=="" set DB_PORT=5432
if "%DB_NAME%"=="" set DB_NAME=ecamp_db
if "%DB_USER%"=="" set DB_USER=ecamp_user
if "%DB_PASSWORD%"=="" set DB_PASSWORD=ecamp_password

set PGPASSWORD=%DB_PASSWORD%

REM Check if psql is available
where psql >nul 2>nul
if %ERRORLEVEL% neq 0 (
    echo ❌ Error: psql command not found.
    echo Please install PostgreSQL and add it to your PATH.
    pause
    exit /b 1
)

echo 📡 Checking PostgreSQL connection...
psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d postgres -c "\q" >nul 2>nul
if %ERRORLEVEL% neq 0 (
    echo ❌ Error: Cannot connect to PostgreSQL server.
    echo Please check your database configuration:
    echo   Host: %DB_HOST%
    echo   Port: %DB_PORT%
    echo   User: %DB_USER%
    pause
    exit /b 1
)

echo ✅ PostgreSQL connection successful
echo.

echo Choose setup option:
echo   1) Fresh setup (drop and recreate database)
echo   2) Update existing database (add children ^& fix feedback)
echo   3) Verify database only (run diagnostics)
echo.
set /p SETUP_OPTION="Enter option (1-3): "

if "%SETUP_OPTION%"=="1" (
    echo.
    echo ⚠️  WARNING: This will DROP and RECREATE the database!
    set /p CONFIRM="Are you sure? (yes/no): "
    
    if not "!CONFIRM!"=="yes" (
        echo ❌ Setup cancelled.
        pause
        exit /b 0
    )
    
    echo.
    echo 🗑️  Dropping existing database...
    psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d postgres -c "DROP DATABASE IF EXISTS %DB_NAME%;" 2>nul
    
    echo 📦 Creating fresh database...
    psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d postgres -c "CREATE DATABASE %DB_NAME%;"
    
    echo 📊 Running main populate script...
    psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% -f populate-campsphere-data.sql
    
    echo 👥 Adding children and fixing feedback...
    psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% -f add-children-fix-feedback.sql
    
    echo.
    echo ✅ Fresh database setup complete!
    
) else if "%SETUP_OPTION%"=="2" (
    echo.
    echo 🔄 Updating existing database...
    echo This will:
    echo   - Add 10 new parents
    echo   - Add 20 new children
    echo   - Delete and recreate all feedback (fixing duplicates)
    echo   - Add corresponding photos
    echo.
    set /p CONFIRM="Continue? (yes/no): "
    
    if not "!CONFIRM!"=="yes" (
        echo ❌ Update cancelled.
        pause
        exit /b 0
    )
    
    echo.
    echo 👥 Running update script...
    psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% -f add-children-fix-feedback.sql
    
    echo.
    echo ✅ Database update complete!
    
) else if "%SETUP_OPTION%"=="3" (
    echo.
    echo 🔍 Running database verification...
    psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% -f verify-database.sql
    
    echo.
    echo ✅ Verification complete!
    pause
    exit /b 0
    
) else (
    echo ❌ Invalid option. Please choose 1, 2, or 3.
    pause
    exit /b 1
)

REM Always run verification after setup/update
echo.
echo 🔍 Running verification checks...
echo.
psql -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% -f verify-database.sql

echo.
echo ========================================
echo ✅ All done!
echo.
echo Database Summary:
echo   📍 Host: %DB_HOST%:%DB_PORT%
echo   📦 Database: %DB_NAME%
echo   👤 User: %DB_USER%
echo.
echo Next steps:
echo   - Start your Spring Boot application
echo   - Access the web interface
echo   - Test with the new children and feedback data
echo.
echo Useful commands:
echo   psql -h %DB_HOST% -U %DB_USER% -d %DB_NAME%  # Connect to database
echo   setup-database.bat  # Run this script again
echo.

pause
