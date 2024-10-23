# kafka-playground

Side project done with Mickaël in order to play with Kafka and other EDA stack tech

Scope [Généré par Miro.AI] : Système de gestion des stocks intégrant des alertes de stock en temps réel et une visualisation. 
Ce système vise à rationaliser la supervision des stocks, réduire les ruptures de stock et améliorer l'efficacité opérationnelle globale.

## TODO List N°1

### CI/CD
- Write Dockerfile to dockerize Spring app []
- Setup a basic GithubAction which run test and deploy our app []

### Applicative
- Bootstrap Spring App []
- Bootstrap a basic front []
- Setup Kafka Admin Client with ConfluentCloud configuration (TLS) []
- Add basic Kafka Producer to producer on specific topic []
- Add basic Kafka Consumer to consume on specific topic []

### Cloud
- Setup Kafka Cluster on ConfluentCloud with TLS []

### Deployment
- Setup fly.io environment to deploy Spring app []

## Backlog
- BDD Redis
- Observabilité/monitoring/alerting
 	- Prometheus/grafana


### How to build

> docker build -t kafka-playground-app . 

### How to run
> docker run -d --name kafka-playground-app -p 8080:8080 kafka-playground-app:latest
