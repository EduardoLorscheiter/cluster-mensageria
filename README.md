# 🧩 Projeto: Sistema Produtor-Consumidor com RabbitMQ (Docker)

## 🎯 OBJETIVO
Executar, via **Docker**, um sistema de **Produtores e Consumidores em Java** que trocam mensagens usando **RabbitMQ**, simulando o processamento de produtos **A** e **B**, cada um com tempos distintos de produção e consumo.

---

## ⚙️ 1) Pré-requisitos

Antes de executar o projeto, você precisa ter instalado o **Docker**:
### 🔗 Download oficial: https://www.docker.com/products/docker-desktop/
### ✔️ Docker
- Verifique a instalação:
  ```bash
  docker --version
  ```
### ✔️ Docker Compose
- Verifique a instalação:
  ```bash
  docker compose version
  ```
- Se ambos responderem a versão, você está pronto.
<br>

## 📦 2) Estrutura do Projeto
- Seu repositório deve parecer com isto: 
```
📂 cluster-mensageria/
 │
 ├── 📂 libs/
 │    ├── 📄amqp-client-5.27.0.jar
 │    ├── 📄netty-buffer-4.1.128.Final.jar
 │    ├── 📄netty-codec-4.1.128.Final.jar
 │    ├── 📄netty-common-4.1.128.Final.jar
 │    ├── 📄netty-handler-4.1.128.Final.jar
 │    ├── 📄netty-resolver-4.1.128.Final.jar
 │    ├── 📄netty-transport-4.1.128.Final.jar
 │    ├── 📄slf4j-api-2.0.17.jar
 │    └── 📄slf4j-simple-2.0.17.jar
 │
 ├── 📂src/
 │    └── 📂main/
 │         └── 📂java/
 │              └── 📂com/
 │                   └── 📂feevale/
 │                        ├── 📂common/
 │                        │    └── 📄RabbitMQConfig.java
 │                        ├── 📂producer/
 │                        │    └── 📄Producer.java
 │                        └── 📂consumer/
 │                             └── 📄Consumer.java
 │
 ├── 📄 .cspell.json
 ├── 📄 docker-compose.yml
 ├── 📄 Dockerfile
 ├── 📄 pom.xml
 └── 📄 README.md
```
- O **`Dockerfile`** é responsável por compilar o código Java, e o **`docker-compose.yml`** sobe:
  - 1 instância do **RabbitMQ**
  - 2 **Produtores**
  - 4 **Consumidores**
- Todos rodando **automaticamente** e conectados na mesma rede **Docker**.
<br>

## ▶️ 3) Como Executar
- **1º:** Abra um terminal (CMD) dentro do projeto. Algo como:
  ```bash
  C:\Projects\GitProjects\cluster-mensageria
  ```
- **2º:** Suba todo o ambiente:
  ```bash
  docker compose up --build
  ```
- Também é possível visualizar os logs de cada container individualmente:
  - Em algum terminal (CMD) dentro do projeto, execute:
    - **Produtor 1:**
      ```bash
      docker logs -f cluster-mensageria-producer1-1
      ```
    - **Produtor 2:**
      ```bash
      docker logs -f cluster-mensageria-producer2-1
      ```
    - **Consumidor 1:**
      ```bash
      docker logs -f cluster-mensageria-consumer1-1
      ```
    - **Consumidor 2:**
      ```bash
      docker logs -f cluster-mensageria-consumer2-1
      ```
    - **Consumidor 3:**
      ```bash
      docker logs -f cluster-mensageria-consumer3-1
      ```
    - **Consumidor 4:**
      ```bash
      docker logs -f cluster-mensageria-consumer4-1
      ```
<br>

## 📊 4) Acessando o Painel do RabbitMQ
- O **RabbitMQ** sobe com a versão **management** configurada dentro do **`docker-compose.yml`**, permitindo acessar o painel:
  > - 👉 http://localhost:15672/
  > - Usuário: guest
  > - Senha: guest
- No painel você pode monitorar:
  - Filas (**`queue_product_A`** e **`queue_product_B`**)
  - Entradas dos produtores
  - Consumo realizado pelos consumidores
  - Mensagens pendentes, taxas, gráficos, etc.
<br>

## 🧠 5) Como o Sistema Funciona (basicamente)
- **Produtores**:
  - Escolhem aleatoriamente um produto:
    - Produto **A** → tempo **3000ms**
    - Produto **B** → tempo **4000ms**
  - Publicam na fila correspondente:
    - **`queue_product_A`**
    - **`queue_product_B`**
- **Consumidores**:
  - A cada iteração, escolhem aleatoriamente uma fila para tentar consumir.
  - Ao receber uma mensagem:
    - Extraem o campo **"tempo_producao"**.
    - Consomem com tempo = **2x** o original.
    - Confirmam o processamento via **`basicAck`**.
<br>

## 🛑 6) Encerrando o Sistema
- Para desligar tudo:
  ```bash
  docker compose down
  ```
  - Isso para todos os **produtores**, **consumidores** e o **RabbitMQ**.
- Se quiser remover as filas do **RabbitMQ** (opcional), use o painel ou:
  ```bash
  docker exec -it rabbitmq rabbitmqctl delete_queue queue_product_A
  docker exec -it rabbitmq rabbitmqctl delete_queue queue_product_B
  ```
<br>

## 🧹 7) Limpar Imagens (opcional)
  ```bash
  docker system prune -a
  ```
<br>

## 🚀 8) Pronto!
- Você tem agora um sistema **Produtor–Consumidor** totalmente automatizado via Docker, sem precisar instalar **Java** localmente e sem instalar **RabbitMQ** manualmente.
