
Steps to run project

1) in root directory run command (docker-compose up)
2) Create 2 databases Bank and Bank1 manually in db
3) Run command (gradle build)
4) Run the project using nohup

5) Import the Accoint-Endpoints directory in Postman for testing purpose.


Choices I made:

I have use gradle as a build, mysql as databse, RabbitMq for storing transaction messages.


Scaling


Horizontal scaling can be acheived by using AWS auto-scaling service that can create multiple instances of that app. 



