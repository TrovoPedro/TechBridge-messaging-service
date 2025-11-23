# Equilibrium Messaging Service 📨  
Serviço de mensageria assíncrona baseado em RabbitMQ para integração entre microsserviços da plataforma **Equilibrium Adventure**.

---

## 📌 Visão Geral

O **Equilibrium Messaging Service** é responsável por gerenciar o tráfego de mensagens internas entre microsserviços, garantindo:
- Processamento assíncrono eficiente  
- Desacoplamento das aplicações  
- Alta disponibilidade e escalabilidade  
- Retentativas automáticas e Dead Letter Queue  
- Observabilidade via logs e métricas  

Atualmente, utiliza **RabbitMQ** como broker principal.

---

## 🐇 Arquitetura de Mensageria


### Componentes:
- **Exchange (direct/topic/fanout)** — roteamento das mensagens  
- **Queue** — fila principal  
- **DLQ (Dead Letter Queue)** — mensagens falhas  
- **Bindings** — regras de roteamento  
- **Consumers** — serviços que processam mensagens  
- **Producers** — serviços que enviam mensagens  

---

## 🚀 Tecnologias Utilizadas

- **RabbitMQ 3**
- **Spring Boot
