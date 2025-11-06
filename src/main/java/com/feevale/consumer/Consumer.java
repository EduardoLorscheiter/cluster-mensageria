/*
**===========================================================================
**  @file    Consumer.java
**  @author  Eduardo Lorscheiter e Loreno Enrique Ribeiro
**  @class   Processamento Paralelo
**  @date    Novembro/2025
**  @version 1.0
**  @brief   Trabalho Prático 2 - Múltiplos Processadores
**===========================================================================
*/

package com.feevale.consumer;

import com.feevale.common.RabbitMQConfig;
import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Consumer {
    public static void main(String[] args) throws Exception {
        String name = args.length > 0 ? args[0] : "Consumidor";
        Connection connection = RabbitMQConfig.getConnection();
        Channel channel = connection.createChannel();

        // Cria as filas se não existirem
        // Obs.: Filas duráveis não são necessárias para teste, seriam úteis se fosse desejada persistência
        channel.queueDeclare(RabbitMQConfig.QUEUE_PRODUCT_A, false, false, false, null);
        channel.queueDeclare(RabbitMQConfig.QUEUE_PRODUCT_B, false, false, false, null);

        // Escolha aleatória inicial de preferência do consumidor
        Random random = new Random();
        // True => consome A; False => consome B
        boolean prefersA = random.nextBoolean(); 
        String queue = prefersA ? RabbitMQConfig.QUEUE_PRODUCT_A : RabbitMQConfig.QUEUE_PRODUCT_B;
        // Tempo de consumo = 2x produção (3s)
        long consumptionTime = 6000;

        System.out.printf("%s aguardando mensagens na fila %s (consumo %d ms)%n", name, queue, consumptionTime);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String msg = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.printf("[%s] Recebido: %s%n", name, msg);
            try {
                Thread.sleep(consumptionTime);
            } catch (InterruptedException ignored) {}
            System.out.printf("[%s] Finalizou consumo de uma mensagem.%n", name);
        };

        // Consome mensagens da fila escolhida
        channel.basicConsume(queue, true, deliverCallback, consumerTag -> {});
    }
}
