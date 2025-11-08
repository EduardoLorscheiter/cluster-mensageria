/*
**===========================================================================
**  @file    RabbitMQConfig.java
**  @author  Eduardo Lorscheiter e Loreno Enrique Ribeiro
**  @class   Processamento Paralelo
**  @date    Novembro/2025
**  @version 1.0
**  @brief   Trabalho Prático 2 - Múltiplos Processadores
**===========================================================================
*/

package com.feevale.common;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQConfig {
    // Filas utilizadas no RabbitMQ
    public static final String QUEUE_PRODUCT_A = "queue_product_A";
    public static final String QUEUE_PRODUCT_B = "queue_product_B";

    // Nome padronizado do campo de tempo de produção
    public static final String PRODUCTION_TIME = "tempo_producao";

    // Dados para conexão com o RabbitMQ
    private static final String HOST = "localhost";
    private static final String USER = "guest";
    private static final String PASS = "guest";

    /**
     * Cria uma conexão e um canal com as filas já configuradas no RabbitMQ
     */
    public static Channel createChannel() throws Exception {
        // Cria o canal com a conexão com o RabbitMQ
        Connection connection = createConnection();
        Channel channel = connection.createChannel();

        // Cria as filas se não existirem
        // Obs.1: Filas duráveis não são necessárias para teste
        // Obs.2: Seriam úteis se fosse desejada persistência
        channel.queueDeclare(QUEUE_PRODUCT_A, false, false, false, null);
        channel.queueDeclare(QUEUE_PRODUCT_B, false, false, false, null);

        return channel;
    }

    /**
     * Cria uma conexão com o RabbitMQ
     */
    private static Connection createConnection() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setUsername(USER);
        factory.setPassword(PASS);
        factory.setAutomaticRecoveryEnabled(true);
        return factory.newConnection();
    }
}
