This project is composed of a frontend spa in angular serving a backend service on springboot.
You need to be identified to use it, you can connect with you Web Identity provider.

![design_overview](https://user-images.githubusercontent.com/33250203/99196583-9fcfcd80-278d-11eb-8d08-241ecb237fcb.png)


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
