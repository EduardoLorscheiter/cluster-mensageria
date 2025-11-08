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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Consumer {
    public static void main(String[] args) throws Exception {
        String name = args.length > 0 ? args[0] : "Consumidor";
        Channel channel = RabbitMQConfig.createChannel();

        // Escolha aleatória inicial de preferência do consumidor
        Random random = new Random();
        // True => consome A; False => consome B
        boolean prefersA = random.nextBoolean();
        String queue = prefersA ? RabbitMQConfig.QUEUE_PRODUCT_A : RabbitMQConfig.QUEUE_PRODUCT_B;

        System.out.printf("%s aguardando mensagens na fila %s%n", name, queue);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.printf("[%s] Recebido: %s%n", name, message);

            // Extrai o tempo original da mensagem (usando regex simples)
            long originalTime = extractTime(message);
            long consumptionTime = originalTime * 2;

            System.out.printf("[%s] Consumindo mensagem por %d ms...%n", name, consumptionTime);
            try {
                Thread.sleep(consumptionTime);
            } catch (InterruptedException ignored) {
            }
            System.out.printf("[%s] Finalizou consumo de uma mensagem.%n", name);
        };

        // Consome mensagens da fila escolhida
        channel.basicConsume(queue, true, deliverCallback, consumerTag -> {
        });
    }

    /**
     * Extrai o tempo de produção da mensagem
     * 
     * @param message Mensagem recebida
     * @return Tempo de produção em milissegundos
     */
    private static long extractTime(String message) {
        Pattern pattern = Pattern.compile("\"" + RabbitMQConfig.PRODUCTION_TIME + "\":(\\d+)");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            return Long.parseLong(matcher.group(1));
        }
        // Fallback caso não ache o campo de tempo de produção
        return 3000;
    }
}
