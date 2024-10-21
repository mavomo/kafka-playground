# kafka-playground

Side project done with Mickaël in order to play with Kafka and other EDA stack tech

Scope : En tant que client, je veux sélectionner des produits et les mettre dans un chariot.
Une API externe donne sur la page des commandes, le stock restant pour ce produit.

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
 	Prometheus/grafana
