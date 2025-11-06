/*
**===========================================================================
**  @file    Producer.java
**  @author  Eduardo Lorscheiter e Loreno Enrique Ribeiro
**  @class   Processamento Paralelo
**  @date    Novembro/2025
**  @version 1.0
**  @brief   Trabalho Prático 2 - Múltiplos Processadores
**===========================================================================
*/

package com.feevale.producer;

import com.feevale.common.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Random;

public class Producer {
    public static void main(String[] args) throws Exception {
        String name = args.length > 0 ? args[0] : "Produtor";
        Connection connection = RabbitMQConfig.getConnection();
        Channel channel = connection.createChannel();

        // Cria as filas se não existirem
        // Obs.: Filas duráveis não são necessárias para teste, seriam úteis se fosse desejada persistência
        channel.queueDeclare(RabbitMQConfig.QUEUE_PRODUCT_A, false, false, false, null);
        channel.queueDeclare(RabbitMQConfig.QUEUE_PRODUCT_B, false, false, false, null);

        Random random = new Random();
        System.out.println(name + " iniciado. Enviando mensagens...");

        while (true) {
            // Escolhe o tipo do produto aleatoriamente
            boolean typeA = random.nextBoolean();
            String type = typeA ? "A" : "B";
            String queue = typeA ? RabbitMQConfig.QUEUE_PRODUCT_A : RabbitMQConfig.QUEUE_PRODUCT_B;
            String message = String.format("{\"produto\":\"%s\",\"produtor\":\"%s\",\"ts\":\"%s\"}",
                    type, name, Instant.now().toString());

            channel.basicPublish("", queue, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("[" + name + "] Enviado -> " + message);

            // Aguarda o tempo de produção: 3s para qualquer tipo
            Thread.sleep(3000);
        }
    }
}
