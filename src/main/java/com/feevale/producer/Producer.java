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
import java.util.concurrent.atomic.AtomicInteger;

public class Producer {
    // Tempos de produção para cada tipo de produto
    private static final int PRODUCTION_TIME_A = 3000;
    private static final int PRODUCTION_TIME_B = 4000;

    // Formato de data e hora para o timestamp
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Gerador de números aleatórios
    private static final Random RANDOM = new Random();

    public static void main(String[] args) throws Exception {
        String name = args.length > 0 ? args[0] : "Produtor";
        Channel channel = RabbitMQConfig.createChannel();

        AtomicInteger counter = new AtomicInteger(0);

        System.out.println(name + " iniciado. Enviando produtos (mensagens) aleatoriamente...");

        while (true) {
            // Escolhe o tipo do produto aleatoriamente
            boolean typeA = RANDOM.nextBoolean();
            String type = typeA ? "A" : "B";
            String queue = typeA ? RabbitMQConfig.QUEUE_PRODUCT_A : RabbitMQConfig.QUEUE_PRODUCT_B;

            // Gera um ID único baseado no produtor
            String id = getPrefix(name) + "-" + counter.incrementAndGet() + "ID";

            // Define o tempo de produção conforme o produto
            long productionTime = typeA ? PRODUCTION_TIME_A : PRODUCTION_TIME_B;

            String timestamp = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).format(FORMATTER);

            String message = String.format(
                    "{\"id\":\"%s\",\"produto\":\"%s\",\"%s\":%d,\"produtor\":\"%s\",\"ts\":\"%s\"}",
                    id, type, RabbitMQConfig.PRODUCTION_TIME, productionTime, name, timestamp);

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

    /**
     * Extrai o ID
     * 
     * @param name Nome do produtor
     * @return Prefixo para o ID da mensagem
     */
    private static String getPrefix(String name) {
        if (name.equalsIgnoreCase("Produtor1")) {
            return "P1";
        } else if (name.equalsIgnoreCase("Produtor2")) {
            return "P2";
        }
        // Fallback caso apareça um nome inesperado
        return name.replace(" ", "");
    }
}
