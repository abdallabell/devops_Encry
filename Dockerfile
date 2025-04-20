FROM openjdk:17
COPY . /app
WORKDIR /app
RUN javac Encryptor.java
CMD ["java", "Encryptor"]

