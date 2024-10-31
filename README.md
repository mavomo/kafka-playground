# kafka-playground

Side project done with Mickaël in order to play with Kafka and other EDA stack tech

Scope [Généré par Miro.AI] : Système de gestion des stocks intégrant des alertes de stock en temps réel et une visualisation. 
Ce système vise à rationaliser la supervision des stocks, réduire les ruptures de stock et améliorer l'efficacité opérationnelle globale.

## TODO List N°1

### CI/CD
- Write Dockerfile to dockerize Spring app [X]
- Setup a basic GithubAction which run test and deploy our app [X]

### Applicative
- Bootstrap Spring App [X]
- Bootstrap a basic front []
- Setup Kafka Admin Client with ConfluentCloud configuration (TLS) [X]
- Add basic Kafka Producer to producer on specific topic [X]
- Add basic Kafka Consumer to consume on specific topic []
- Add technical implementation of kafka in order to reduce the spring kafka abstraction as internal library
  - Custom Application Properties []
  - Custom TemplateFactory []
  - Custom Consumer & Producer configuration class []
  - ConsumerErrorReplayHandler & KafkaListenerContainerFactory []

### Cloud
- Setup Kafka Cluster on ConfluentCloud with TLS [X]

### Deployment
- Setup fly.io environment to deploy Spring app [X]

## Backlog
- BDD Redis
- Observabilité/monitoring/alerting
 	- Prometheus/grafana


### How to build

> docker build -t kafka-playground-app . 

### How to run
> docker run -d --name kafka-playground-app -p 8080:8080 kafka-playground-app:latest
