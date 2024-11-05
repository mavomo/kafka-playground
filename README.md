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
  - Custom Application Properties [X]
  - Custom TemplateFactory [X]
  - Custom Producer configuration class [X]
  - Custom Consumer configuration class []
  - ConsumerErrorReplayHandler []
  - KafkaListenerContainerFactory []
  - Add Schema Registry
    - Check ConfluentCloud schema registry access & credentials []
    - Add configuration properties []
    - Add configuration in ProducerFactory []
    - Add Avro Maven plugin []
    - Write Avro Pojo []
    - Refactor our KafkaTemplate to use Avro Pojo []
    > why use schema registry?: Technical skill up
  - Add KafkaStream in order to real time update product inventory
    - Add basic KStream code [X]
    - Add OrderConsumer [X]
    - Add Inventory Producer [X]
      - Produce inventory update on "dev.playground.inventory.updated" topic [TODO]
    - Add ProductStore []
      - Implement InventoryService#fillProduct(long quantity) which produce on "dev.playground.inventory.updated" topic []

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
