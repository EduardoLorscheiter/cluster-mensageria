# =============================
# 1º: IMAGEM BUILDER
# =============================
# Usa uma imagem com Java 21 chamando esta fase de "builder". Aqui será compilado o código.
FROM eclipse-temurin:21-jdk AS builder

# Pasta /app dentro da imagem Docker como o diretório de trabalho
WORKDIR /app

# Copia libs (bibliotecas) do projeto
COPY libs ./libs

# Copia código fonte do projeto
COPY src ./src

# Obs.1: Lugar onde ficarão as classes compiladas
# Obs.2: Cria uma pasta chamada "classes" dentro do WORKDIR --> /app/classes
RUN mkdir classes

# Compila as classes Java e joga os .class para dentro da pasta "classes"
RUN javac -cp "libs/*" -d classes \
    src/main/java/com/feevale/common/*.java \
    src/main/java/com/feevale/producer/*.java \
    src/main/java/com/feevale/consumer/*.java

# ================================
# 2º: IMAGEM FINAL
# ================================
# Usa uma imagem com Java 21 para rodar a aplicação
FROM eclipse-temurin:21-jre

# Pasta /app dentro da imagem Docker como o diretório de trabalho
WORKDIR /app

# Pega a pasta "/app/classes" da imagem "builder" e copia para a imagem "final", no diretório atual (./classes)
COPY --from=builder /app/classes ./classes
# Pega as libs copiadas no "builder" e move para a imagem "final"
COPY --from=builder /app/libs ./libs

# Command padrão (será sobrescrito no docker-compose)
CMD ["java", "-cp", "classes:libs/*", "com.feevale.producer.Producer", "ProdutorDocker"]
