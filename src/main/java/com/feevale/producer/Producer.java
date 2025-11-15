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
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Producer {
    public static void main(String[] args) throws Exception {
        String name = args.length > 0 ? args[0] : "Produtor";
        Channel channel = RabbitMQConfig.createChannel();

        Random random = new Random();
        System.out.println(name + " iniciado. Enviando produtos (mensagens) aleatoriamente...");

        while (true) {
            // Escolhe o tipo do produto aleatoriamente
            boolean typeA = random.nextBoolean();
            String type = typeA ? "A" : "B";
            String queue = typeA ? RabbitMQConfig.QUEUE_PRODUCT_A : RabbitMQConfig.QUEUE_PRODUCT_B;

            // Define o tempo de produção conforme o produto
            // Produto A => 3000ms; Produto B => 4000ms
            long productionTime = typeA ? 3000 : 4000;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).format(formatter);

            String message = String.format(
                    "{\"produto\":\"%s\",\"%s\":%d,\"produtor\":\"%s\",\"ts\":\"%s\"}",
                    type, RabbitMQConfig.PRODUCTION_TIME, productionTime, name, timestamp);

            AMQP.BasicProperties props = new AMQP.BasicProperties.Builder()
                    .deliveryMode(2) // 2 = persistente
                    .contentType("application/json")
                    .build();

            channel.basicPublish("", queue, props, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("[" + name + "] Enviado -> " + message);

            // Aguarda o tempo de produção
            Thread.sleep(productionTime);
        }
    }
}
