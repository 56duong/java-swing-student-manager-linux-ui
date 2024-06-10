# JAVA SWING STUDENT MANAGER LINUX UI

For my class assignment, I wanted to make the task of building a management app more fun. Instead of making apps with a dull design for managing students, users, employees, and libraries,... I decided to mix student management with a Linux-style user interface. 

This approach made the project more exciting and gave me a chance to be creative with different ideas.


# Table of Contents
1. [Demo](#demo)
2. [Features](#features)
3. [Tech Stack Versions](#tech-stack-versions)
4. [Configuration](#configuration)
5. [Getting Started](#getting-started)
6. [Who i am?](#who-i-am)
7. [Contact](#contact)


## 🎥 Demo
![thumbnail-2](https://github.com/56duong/java-swing-student-manager-linux-ui/assets/77065902/b8c65ec4-a770-4d8e-89f0-6e1b78bf4692)

## 🌟 Features

- **Student Management**: Easily add, edit, update, delete, and view student records.

- **User Management**: Manage user accounts with simple controls.

- **Test Management**: Organize and track student tests efficiently.
  
- **Mark Management**: Handle and update student marks with ease.
  
- **Create New Text Document**: Quickly create and edit text files within the app.
  
- **Change Desktop Background**: Customize the desktop background to your liking.

## 📌 Tech Stack Versions
Please note that the versions listed below are the ones used during the development of this application. The application may work with other versions as well, but these are the ones that have been tested and confirmed to work.

- **JDK**: 17
  
- **IDE**: Apache NetBeans IDE 15

## ⚙️ Configuration

The application can be configured by modifying the [Config.java](https://github.com/56duong/java-swing-student-manager-linux-ui/blob/master/src/ps20250nguyenngocthuyduong/config/Config.java) file. Here are some of the configurations you might need to change:

- **Run SQL Script**: To set up the database, you need to run the SQL script provided in FPOLY_DaoTao.sql.

1. Open your SQL client tool.
2. Connect to your SQL Server database.
3. Execute the SQL script FPOLY_DaoTao.sql to create the necessary database tables and schema.


- **Configure Database Connection**: Navigate to the [Config.java](https://github.com/56duong/java-swing-student-manager-linux-ui/blob/master/src/ps20250nguyenngocthuyduong/config/Config.java) and update the configuration settings with your database credentials. Example:

```bash package ps20250ngocthuyduong.config;

public class Config {
    public static final String SQLSERVER_USERNAME = "sa";
    public static final String SQLSERVER_PASSWORD = "yourPassword"; // Update with your password
    public static final String SQLSERVER_DATABASE_NAME = "FPOLY_DaoTao";
    public static final Integer SQLSERVER_DATABASE_PORT = 1433;
}
```


## 🚀 Running the Application

Start the application and log in with the default credentials:
```bash
username: admin
password: admin
```

## :wave: Who i am?

A young developer, who dedicated to developing usable apps, not just 'homework apps'. 

I delight in sharing my knowledge and experiences, hoping to inspire others to embark on their own journey in application development. Also, I have a fondness for sour soup :bowl_with_spoon:.

## :incoming_envelope: Contact

If you want to contact me you can reach me at <56duong@gmail.com>. If you'd like to contribute, please fork the repository and use a pull request for changes.
