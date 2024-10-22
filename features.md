# Inventory Management System

## 1. Product Inventory Management
    [] Product detail definition 
        - Generated products reference stored out of confluent cloud
        - Donnée froide - trouver la meilleur BDD optimisée pour de la lecture.
         {
            id: 
            nom: 
            date_configuration:
         }
    [] Product Availability monitoring

## 2. Stock Alert System
    [] Configure Product inventory alerts
         {
            id_produit: 
            qte_disponible:
            seuil_alerte: 10
            date_configuration:
           } 
    [] Automated stock notifications
        - uService de type KStream ( orders topic, inventory topic) et déclenchera l'envoie
        - il nous faut generer une data d'inventaires avec nos produits "froids"
        - à la première connexion: 
            - On va initialiser l'inventaire avec les évenements Kafka
 
## 3. Order manageement 
    [] Visualize Real-Time Order Status
        - Rocks.DB 


