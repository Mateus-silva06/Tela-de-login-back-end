# Estágio 1: Build (Compilar o código com Java 21)
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copia todos os arquivos do seu projeto para dentro do container
COPY . .

# Dá permissão de execução para o Maven Wrapper e faz o build do projeto
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Estágio 2: Run (Rodar a aplicação com Java 21)
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copia apenas o arquivo .jar gerado no passo anterior
COPY --from=build /app/target/*.jar app.jar

# Libera a porta 8080 (padrão do Spring Boot)
EXPOSE 8080

# Comando para iniciar o servidor, configurado para pegar a porta automática do Render
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]
