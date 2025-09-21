package com.relx.banking.customerservice.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Naveen.Sankhala
 * Sep 2, 2025
 */
public class DBConfig {

	private static final String URL = "jdbc:postgresql://ep-empty-surf-adsq0sfw-pooler.c-2.us-east-1.aws.neon.tech/bank-app-db?sslmode=require&channelBinding=require";
    private static final String USER = "neondb_owner";
    private static final String PASSWORD = "npg_I1TgwvEZ4dmD";
    

    public static void main(String[] args) {
        System.out.println("Testing connection to Neon PostgreSQL database...");

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            if (connection != null && !connection.isClosed()) {
                System.out.println("✅ Connection to Neon DB established successfully!");
            } else {
                System.out.println("❌ Failed to establish connection to Neon DB.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error while connecting to Neon DB: " + e.getMessage());
            e.printStackTrace();
        }
    }
	
}
