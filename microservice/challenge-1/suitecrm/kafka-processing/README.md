Suite Crm Kafka streams processors App
=====================
This module is a Kafka Streams apps that process incoming data {customers,orders,products and its relations}.

It consist of 2 topologies:
 1. Product topology: aggregate products relationship 
 1. Customer topology: calculate recommandation (suggestion that can interest a customer) 

These topologies can be run in the same container instance or separatly (better for scaling).
Every topology can scale based on the number of kafka topics partitions.