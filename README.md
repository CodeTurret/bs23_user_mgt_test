# bs23_user_mgt_test
This is a demo repository for bs23 assessment.

In this application we implement JPA with MySQL, Radis for Cacheing and Kafka for Messaging.
This application has only four basic api end points.
"/api/get-user/1",
"/api/add-user",
"/api/update/1",
"/api/delete/1"

These api will get, add, update and delete user.

Moreover when any user created, updated or deleted it will send message to the topic "user-events". Also all information will be cached.

These application also has docker files.

To run this application, you need to have docker desktop in your computer, as I am a windows user.

If all are okay, then pull this application using git command or hit "download" button at the top right corner.

You need to have maven on your workstation.

After download, please run "mvn clean install" in the "usermgt" directory.

Now open cmd, and run "docker build -t <docker_user_name>/usermgt:0.0.1 ." Make sure about the docker_user_name.

After that run "docker-compose up".

Application will be run accordingly.
App
