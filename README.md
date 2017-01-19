## PlayPin

----------------------------------------

PlayPin is a distributed image aggregator web application. It is developed in Scala, using the Play Framework. Cassandra is used for persistent storage. The application runs in Docker containers and the deployment assured by Vagrant. Vagrant files for both AWS and local deployment are provided. Amazon S3 is used for image storage. Nginx is used as a load balancer between three virtual machines running both the PlayPin and Cassandra. The system withstands a virtual machine crash without affecting uptime or data consistency. 

Documentation present in [*Documentation PDF*](Documentation_CiobicaEA_IsacFA.pdf)
