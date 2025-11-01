@echo off
chcp 65001 >nul
REM ===================================================================
REM Script para compilar todas as classes do projeto cluster-mensageria
REM ===================================================================

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

REM Caminho para as pastas do código-fonte e destino das classes compiladas
set SRC_DIR=%BASE_DIR%\src\main\java

echo Compilando fontes...
javac -cp "%LIBS%" ^
  %SRC_DIR%\com\feevale\common\*.java ^
  %SRC_DIR%\com\feevale\producer\*.java ^
  %SRC_DIR%\com\feevale\consumer\*.java

if %errorlevel% neq 0 (
    echo.
    echo ERRO: Falha na compilação.
    pause
    exit /b %errorlevel%
)

echo.
echo Compilação concluída com sucesso!
pause
