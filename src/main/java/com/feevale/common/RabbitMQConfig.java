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

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQConfig {
    // Filas utilizadas no RabbitMQ
    public static final String QUEUE_PRODUCT_A = "queue_product_A";
    public static final String QUEUE_PRODUCT_B = "queue_product_B";

    // Dados para conexão com o RabbitMQ
    private static final String HOST = "localhost";
    private static final String USER = "guest";
    private static final String PASS = "guest";

    public static Connection getConnection() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setUsername(USER);
        factory.setPassword(PASS);
        factory.setAutomaticRecoveryEnabled(true);
        return factory.newConnection();
    }
}
