# Estágio 1: Build (Usa uma imagem que já tem o Maven e o Java 21)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copia todos os arquivos do seu projeto
COPY . .

# Faz o build usando o Maven direto (sem o ./mvnw)
RUN mvn clean package -DskipTests

# Estágio 2: Run (Rodar a aplicação com Java 21)
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copia apenas o arquivo .jar gerado no passo anterior
COPY --from=build /app/target/*.jar app.jar

# Libera a porta 8080
EXPOSE 8080

# Comando para iniciar o servidor
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]
