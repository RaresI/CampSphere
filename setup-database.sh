#!/bin/bash
# ==========================================
# Database Setup Script - E-Camp Project
# ==========================================
# This script sets up the complete database with all data including
# the new children and fixed feedback system

set -e  # Exit on any error

echo "🚀 E-Camp Database Setup Script"
echo "================================"
echo ""

# Database configuration
DB_HOST="${DB_HOST:-localhost}"
DB_PORT="${DB_PORT:-5432}"
DB_NAME="${DB_NAME:-ecamp_db}"
DB_USER="${DB_USER:-ecamp_user}"
DB_PASSWORD="${DB_PASSWORD:-ecamp_password}"

# Check if PostgreSQL is available
echo "📡 Checking PostgreSQL connection..."
if ! command -v psql &> /dev/null; then
    echo "❌ Error: psql command not found. Please install PostgreSQL client."
    exit 1
fi

# Test database connection
export PGPASSWORD=$DB_PASSWORD
if ! psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d postgres -c '\q' 2>/dev/null; then
    echo "❌ Error: Cannot connect to PostgreSQL server."
    echo "Please check your database configuration:"
    echo "  Host: $DB_HOST"
    echo "  Port: $DB_PORT"
    echo "  User: $DB_USER"
    exit 1
fi

echo "✅ PostgreSQL connection successful"
echo ""

# Prompt user for setup option
echo "Choose setup option:"
echo "  1) Fresh setup (drop and recreate database)"
echo "  2) Update existing database (add children & fix feedback)"
echo "  3) Verify database only (run diagnostics)"
echo ""
read -p "Enter option (1-3): " SETUP_OPTION

case $SETUP_OPTION in
    1)
        echo ""
        echo "⚠️  WARNING: This will DROP and RECREATE the database!"
        read -p "Are you sure? (yes/no): " CONFIRM
        
        if [ "$CONFIRM" != "yes" ]; then
            echo "❌ Setup cancelled."
            exit 0
        fi
        
        echo ""
        echo "🗑️  Dropping existing database..."
        psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d postgres -c "DROP DATABASE IF EXISTS $DB_NAME;" 2>/dev/null || true
        
        echo "📦 Creating fresh database..."
        psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d postgres -c "CREATE DATABASE $DB_NAME;"
        
        echo "📊 Running main populate script..."
        psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME -f populate-campsphere-data.sql
        
        echo "👥 Adding children and fixing feedback..."
        psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME -f add-children-fix-feedback.sql
        
        echo ""
        echo "✅ Fresh database setup complete!"
        ;;
        
    2)
        echo ""
        echo "🔄 Updating existing database..."
        echo "This will:"
        echo "  - Add 10 new parents"
        echo "  - Add 20 new children"
        echo "  - Delete and recreate all feedback (fixing duplicates)"
        echo "  - Add corresponding photos"
        echo ""
        read -p "Continue? (yes/no): " CONFIRM
        
        if [ "$CONFIRM" != "yes" ]; then
            echo "❌ Update cancelled."
            exit 0
        fi
        
        echo ""
        echo "👥 Running update script..."
        psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME -f add-children-fix-feedback.sql
        
        echo ""
        echo "✅ Database update complete!"
        ;;
        
    3)
        echo ""
        echo "🔍 Running database verification..."
        psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME -f verify-database.sql
        
        echo ""
        echo "✅ Verification complete!"
        exit 0
        ;;
        
    *)
        echo "❌ Invalid option. Please choose 1, 2, or 3."
        exit 1
        ;;
esac

# Always run verification after setup/update
echo ""
echo "🔍 Running verification checks..."
echo ""
psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME -f verify-database.sql

echo ""
echo "================================"
echo "✅ All done!"
echo ""
echo "Database Summary:"
echo "  📍 Host: $DB_HOST:$DB_PORT"
echo "  📦 Database: $DB_NAME"
echo "  👤 User: $DB_USER"
echo ""
echo "Next steps:"
echo "  - Start your Spring Boot application"
echo "  - Access the web interface"
echo "  - Test with the new children and feedback data"
echo ""
echo "Useful commands:"
echo "  psql -h $DB_HOST -U $DB_USER -d $DB_NAME  # Connect to database"
echo "  ./setup-database.sh  # Run this script again"
echo ""
