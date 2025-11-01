@echo off
REM ====================================================
REM Script para rodar todos os produtores e consumidores
REM ====================================================

REM Caminho base do projeto (ajuste para o local do projeto)
set BASE_DIR=C:\Projects\GitProjects\cluster-mensageria

REM Caminho para as bibliotecas necessárias (RabbitMQ + SLF4J + Netty)
set LIBS=^
%BASE_DIR%\libs\amqp-client-5.27.0.jar;^
%BASE_DIR%\libs\slf4j-api-2.0.17.jar;^
%BASE_DIR%\libs\slf4j-simple-2.0.17.jar;^
%BASE_DIR%\libs\netty-buffer-4.1.128.Final.jar;^
%BASE_DIR%\libs\netty-codec-4.1.128.Final.jar;^
%BASE_DIR%\libs\netty-common-4.1.128.Final.jar;^
%BASE_DIR%\libs\netty-handler-4.1.128.Final.jar;^
%BASE_DIR%\libs\netty-resolver-4.1.128.Final.jar;^
%BASE_DIR%\libs\netty-transport-4.1.128.Final.jar

REM Caminho para as classes compiladas
set CLASSES_DIR=%BASE_DIR%\src\main\java

REM =====================================================
REM PRODUTORES
REM =====================================================
start cmd /k java -cp "%CLASSES_DIR%;%LIBS%" com.feevale.producer.Producer Produtor1
start cmd /k java -cp "%CLASSES_DIR%;%LIBS%" com.feevale.producer.Producer Produtor2

REM =====================================================
REM CONSUMIDORES
REM =====================================================
start cmd /k java -cp "%CLASSES_DIR%;%LIBS%" com.feevale.consumer.Consumer Consumidor1
start cmd /k java -cp "%CLASSES_DIR%;%LIBS%" com.feevale.consumer.Consumer Consumidor2
start cmd /k java -cp "%CLASSES_DIR%;%LIBS%" com.feevale.consumer.Consumer Consumidor3
start cmd /k java -cp "%CLASSES_DIR%;%LIBS%" com.feevale.consumer.Consumer Consumidor4

echo Todos os produtores e consumidores iniciados!
pause
