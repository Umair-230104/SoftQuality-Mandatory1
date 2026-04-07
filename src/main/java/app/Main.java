package app;

import app.config.AppConfig;

import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        AppConfig.startServer();
    }
}

// Jeg har ændret kode til postgres fra 123456 til postgres og port fra 5433 til 5432 så jeg kan køre
// projektet. Alt er ændret i Hibernate klassen i den her metode:

//private static Properties setDevProperties(Properties props, String DBName)
//{
//  props.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/" + DBName);
// postgres som kode, hvis ikke man har ændret koden
//props.put("hibernate.connection.username", "postgres");
//props.put("hibernate.connection.password", "postgres");
//return props;
//}
// Hilsen Masih
