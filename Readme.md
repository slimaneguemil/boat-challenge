This project is composed of a frontend spa in angular serving a backend service on springboot.
You need to be identified to use it, you can connect with you Web Identity provider.

There are 2 ways to launch the application 

1- launch the full application with docker-compose: 
docker-compose up


2- launch each service manually:

    front end:
        cd boat-frontend
        npm install
        npm start
    
    back-end:
        cd boat-backend
        launch spring-boot