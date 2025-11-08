# 🧩 Projeto: Sistema Produtor-Consumidor com RabbitMQ

## 🎯 OBJETIVO
Executar localmente um sistema simples de **Produtores e Consumidores em Java**, que se comunicam via **RabbitMQ**, simulando o consumo de produtos **A** e **B**.

---

## ⚙️ 1) Pré-requisitos

Antes de qualquer comando, confirme a instalação e configuração do ambiente.
### 🧠 1. Java JDK (versão 20 recomendada)
- Verifique a instalação:
  ```bash
  java -version
  javac -version
  ```
- Se aparecer algo como java version "20.x", ótimo.
- Se aparecer 1.8.0_…, você ainda está com o Java 8 - instale o JDK 20.
- Depois atualize o PATH para o novo Java.
<br>

### 🐇 2. RabbitMQ e Erlang
- Instale **RabbitMQ** e o **Erlang** localmente.
- Verifique se o **RabbitMQ** está rodando:
  ```bash
  rabbitmqctl status
  ```
- Se o **RabbitMQ** não estiver rodando, execute:
  ```bash
  rabbitmq-server
  ```
- Após tudo Ok, abra o **Painel de Gerenciamento**:
  > - http://localhost:15672/
  > - Usuário padrão: guest
  > - Senha padrão: guest
- Se o painel abrir, o servidor RabbitMQ está rodando corretamente.
<br>

### 📁 3. Projeto Baixado
- Baixe o projeto "cluster-mensageria" do GitHub.
  > ⚠️ **Grave o local onde ele foi baixado! É importante para execução!**
<br>

### 📦 4. Estrutura de Pastas
- Seu projeto deve estar assim: 
```
📂 cluster-mensageria/
│
├── 📂bat/
│    └── 📄compile_and_run.bat
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
└── 📂src/
     └── 📂main/
          └── 📂java/
               └── 📂com/
                    └── 📂feevale/
                         ├── 📂common/
                         │    ├── 📄RabbitMQConfig.class
                         │    └── 📄RabbitMQConfig.java
                         ├── 📂producer/
                         │    ├── 📄Producer.class
                         │    └── 📄Producer.java
                         └── 📂consumer/
                              ├── 📄Consumer.class
                              └── 📄Consumer.java
```

## 🔧 2) Compile e Execute o Projeto
- Ajuste as configurações **(Host, Usuário e Senha)** do seu **RabbitMQ** no arquivo **`RabbitMQConfig.java`**.
- Dentro do projeto **`cluster-mensageria`** haverá uma pasta **`bat`**:
  - Abra o arquivo **`compile_and_run.bat`** e altere o valor de **`BASE_DIR`** para o local onde você baixou o projeto. Salve o arquivo **`.BAT`**.
  - Após alterado, abra o **`CMD`**, entre na pasta **`bat`** do projeto e execute:
    - **`compile_and_run.bat`**
    - Isso vai regerar os arquivos **`.class`** nas pastas dos aquivos **`.java`** do projeto.
    - Se não aparecer nenhum erro nem mensagem, está tudo certo (Java é silencioso ao compilar com sucesso).
    - Nesse caso, a execução seguirá automaticamente:
      - Serão abertas várias janelas CMD - **2 produtores + 4 consumidores**, simulando o cenário descrito.
      - É possível monitorar as filas também no painel do **RabbitMQ**.

## 🧠 3) O que Esperar ao Executar
- Cada **Produtor** vai:
  - Enviar mensagens para uma fila (**`queue_product_A`** ou **`queue_product_B`**).
  - Imprimir mensagens como:
    - ... 
- Cada **Consumidor** vai:
  - Escolher aleatoriamente uma fila para consumir.
  - Imprimir mensagens como:
    - ... 

## 🚀 4) Encerrando
- Para parar toda a execução: **feche as janelas de produtores e consumidores CMD**.
- Elimine as filas do **RabbitMQ**:
  - Para consultar as filas do **RabbitMQ**:
    ```bash
    rabbitmqctl list_queues
    ```
  - Para deletar uma fila específica:
    ```bash
    rabbitmqctl delete_queue NOME_DA_FILA
    ```
    **Exemplo:**
    ```bash
    rabbitmqctl delete_queue queue_product_A
    rabbitmqctl delete_queue queue_product_B
    ```
