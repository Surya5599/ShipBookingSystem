
# CS166 Final Project - Cruise Management System

## Table of Contents
- [Project Overview](#project-overview)
- [Folder Structure](#folder-structure)
- [Prerequisites](#prerequisites)
- [Setup Instructions](#setup-instructions)
  - [1. PostgreSQL Setup](#1-postgresql-setup)
  - [2. Compile and Run Java Code](#2-compile-and-run-java-code)
- [Usage](#usage)
- [Color Scheme](#color-scheme)
- [Credits](#credits)

## Project Overview
This project is a Cruise Management System developed for managing ship schedules, reservations, and maintenance. It consists of:
- A PostgreSQL database for backend data management.
- A Java-based GUI for user interaction.
- CSV files for storing pre-existing data about ships, captains, cruises, customers, and more.

## Folder Structure

```
CS166_Final_Project/
│
├── code/
│   ├── data/                 # CSV data files
│   ├── java/                 # Java code and related scripts
│   ├── postgresql/           # PostgreSQL setup scripts
│   └── sql/                  # SQL database schema
├── Phase-3.pdf               # Project documentation
└── README.md                 # This README file
```

## Prerequisites
Before running the project, make sure you have the following installed:
- **Java 8 or later**: Required for compiling and running the Java code.
- **PostgreSQL**: Required for setting up the database.
- **Maven** (Optional): For managing Java dependencies.

## Setup Instructions

### 1. PostgreSQL Setup
1. **Install PostgreSQL**: If you don't already have it, install PostgreSQL from [here](https://www.postgresql.org/download/).

2. **Start PostgreSQL**: Open a terminal and navigate to the `code/postgresql/` folder. Use the script to start the PostgreSQL server:
   ```bash
   ./startPostgreSQL.sh
   ```

3. **Create the Database**: Run the provided script to create the database:
   ```bash
   ./createPostgreDB.sh
   ```

4. **Apply the Schema**: Use the SQL script to set up the required tables.
   ```bash
   psql -d your_database_name -f ../sql/create.sql
   ```

5. **Stop PostgreSQL (Optional)**: You can stop the PostgreSQL server using the script:
   ```bash
   ./stopPostgreDB.sh
   ```

### 2. Compile and Run Java Code

1. **Navigate to the Java Code**: Open a terminal and move to the `code/java/` folder.
   ```bash
   cd code/java/
   ```

2. **Compile the Code**: Use the provided `compile.sh` script to compile the Java files.
   ```bash
   ./compile.sh
   ```

3. **Run the Code**: After compilation, run the project with:
   ```bash
   ./run.sh
   ```

This will launch the Cruise Management System GUI.

## Usage
- The system allows you to manage cruises, schedules, reservations, and customer information via the graphical interface.
- Data for ships, captains, cruises, and reservations are preloaded from CSV files located in the `data/` directory.

## Color Scheme
This section describes the color palette used (optional, for future development):
- **Primary Color**: `#2E86C1` (Deep Sky Blue) for navigation bars and buttons.
- **Secondary Color**: `#D4E6F1` (Light Blue) for the background and UI elements.
- **Accent Color**: `#F1C40F` (Bright Yellow) for highlights and important action buttons.
- **Text Color**: `#34495E` (Dark Grey) for main text.
- **Error Color**: `#E74C3C` (Red) for error messages and alerts.

## Credits
This project was developed by Surya Singh as part of the CS166 course project.
