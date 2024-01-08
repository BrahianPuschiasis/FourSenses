### Runtimes, Engines, Tools

- **Java:** JDK 17
- **Spring Boot:** 3.2.1
- **Node.js:** v16.16.0
- **npm:** 9.8.1
- **Database:** H2 Database Engine

### Frontend Development

The frontend application is built with React and Vite. Make sure you have the following tools installed:

- [Node.js](https://nodejs.org/): v16.16.0
- [npm](https://www.npmjs.com/): 9.8.1
- [React](https://reactjs.org/) 18.2.0
- [Vite](https://vitejs.dev/) v5.0.11 

### Database Configuration

The application uses H2 Database Engine as the database. No additional configuration is required, as the application will automatically generate the database and tables on startup.

### Instructions to Run the Application

1. Clone this repository.
2. Run the startup script: `./run-app.sh` (ensure you have execution permissions on the script 'chmod +x run-app.sh').
3. Access the application from your browser at the link the console provide you.

### Notes

- If you encounter any issues during execution, refer to the documentation of each mentioned technology for additional assistance.
- The project was originally uploaded to AWS service and deployed 100% on the cloud, was adapted to H2 for work as local.
- Credentials for login as admin: email: admin@gmail.com, 12345678

---

**Catering FourSenses Project**

It's a full-stack project that manages a database with both backend and frontend developed in harmony. It's a modern SPA that allows user login with security (Spring Security), registration, and filters by roles depending on whether you are an administrator or a regular user, allowing or restricting certain actions.

**Features:**

- User login/registration
- Permission control
- Allows changing roles for users if you are an administrator
- Add/Delete/Modify meals
- Meal reservation with history and controls
- Filtering meals by categories/available dates/names/favorites
- Image gallery for meals, allows adding as many as necessary
- Already tested on AWS, 100% functional, implementing MySQL instead of H2
