# Temel imaj
FROM openjdk:23-jdk-slim

# Çalışma dizini oluştur
WORKDIR /app

# Bağımlılıkları ve jar dosyasını kopyala
COPY ../target/*.jar app.jar

# Portu expose et
EXPOSE 8080

# Uygulamayı başlat
ENTRYPOINT ["java", "-jar", "app.jar"] 