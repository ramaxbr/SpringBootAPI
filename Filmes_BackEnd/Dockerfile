# FROM: define a imagem base do estágio de build
# maven:3.9 = Maven versão 3.9 (gerenciador de build do Java)
# eclipse-temurin-17 = JDK 17 da distribuição Temurin (OpenJDK confiável)
# AS build = dá o nome "build" a esse estágio (referenciado no estágio 2)
FROM maven:3.9-eclipse-temurin-21 AS build

# WORKDIR: cria e define o diretório de trabalho dentro do container
# Todos os comandos seguintes executam a partir de /app
WORKDIR /app

# COPY pom.xml PRIMEIRO — truque de cache do Docker:
# O Docker só reexecuta uma camada se ela mudou.
# Como o pom.xml muda raramente, as dependências são baixadas
# apenas quando ele muda — economizando minutos de build.
COPY pom.xml .

# RUN: executa um comando durante o build da imagem
# dependency:go-offline = baixa todas as dependências do Maven
# -B = modo batch (sem interação, ideal para automação)
RUN mvn dependency:go-offline -B

# Agora copia o código fonte (muda com frequência)
# Por estar depois das dependências, o cache de dependências
# é preservado mesmo quando o código muda
COPY src ./src

# Compila o projeto e gera o arquivo .jar em target/
# -DskipTests = pula os testes (rodam na CI/CD, não no build da imagem)
RUN mvn clean package -DskipTests


### ---- próxima etapa ----
# FROM: nova imagem base — MUITO menor que a de build
# eclipse-temurin:21-jre = apenas o JRE (Runtime), sem Maven
# alpine = distribuição Linux minimalista (~5 MB vs ~200 MB do Ubuntu)
FROM eclipse-temurin:21-jre-alpine

# BOA PRÁTICA DE SEGURANÇA: criar um usuário sem privilégios
# Nunca rode uma aplicação em container como root!
# Se um atacante explorar uma vulnerabilidade, ele fica limitado
# às permissões do "appuser" — sem acesso ao sistema host.
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Define o diretório de trabalho do runtime
WORKDIR /app

# COPY --from=build: copia apenas o .jar do estágio anterior
# Não copia o Maven, o código fonte, nem as dependências separadas
# Resultado: imagem final de ~180 MB em vez de ~600 MB
COPY --from=build /app/target/*.jar app.jar

# Dá ownership do diretório ao usuário não-root
RUN chown -R appuser:appgroup /app

# Muda para o usuário não-root antes de qualquer execução
USER appuser

# EXPOSE: documenta que o container escuta na porta 8080
# Não publica a porta automaticamente — isso é feito no docker run ou ECS
EXPOSE 8080

# ENTRYPOINT: comando executado quando o container inicia
# Usa formato JSON (lista) — mais seguro que formato shell
#
# -XX:+UseContainerSupport
#   Faz a JVM respeitar os limites de memória do container.
#   Sem isso, a JVM lê a RAM total do host e pode alocar mais
#   memória do que o container permite, causando OOMKilled.
#
# -XX:MaxRAMPercentage=75.0
#   Usa no máximo 75% da RAM do container (deixa 25% para o SO).
#   Com 1 GB de RAM no container: JVM usa até 750 MB.
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
