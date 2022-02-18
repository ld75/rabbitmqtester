<h1>Testeur de connection à RabbitMQ</h1>

Utilise les librairies:

amqp-client-5.7.1.jar

slf4j-api-1.7.26.jar

slf4j-simple-1.7.26.jar


Démarrer un rabbit MQ sur container docker

```docker run -p 5671:5671 -d --hostname monhostrabbit --name monrabbit rabbitmq:latest```

Envoyer un message: 
le jar est sous /rabbitmqclient/out/artifacts/rabbitmqclient_jar/rabbitmqclient.jar

(paramètres: port, hostname,queue,message)

```java -jar rabbitmqclient.jar 5671 localhost maqueue bonjour```

MODE STANDALONE
javac -cp ../../../lib/amqp-client-5.7.1.jar TestStandaloneClass.java
java -cp ../../../lib/amqp-client-5.7.1.jar:. TestStandaloneClass