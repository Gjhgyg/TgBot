# Используем Java 21 (можно заменить на твою версию)
FROM eclipse-temurin:21-jdk

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем pom.xml и зависимости (для Maven)
COPY pom.xml .
COPY src ./src

# Собираем проект
RUN ./mvnw clean package -DskipTests || mvn clean package -DskipTests

# Запускаем JAR (замени название на своё)
CMD ["java", "-jar", "target/TGbot-0.0.1-SNAPSHOT.jar"]