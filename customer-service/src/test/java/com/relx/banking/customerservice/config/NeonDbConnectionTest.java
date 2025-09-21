package com.relx.banking.customerservice.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Naveen.Sankhala
 * Sep 3, 2025
 */
public class NeonDbConnectionTest {

	public static void main(String[] args) {
		String url = "jdbc:postgresql://ep-empty-surf-adsq0sfw-pooler.c-2.us-east-1.aws.neon.tech/bank-app-db?sslmode=require&loginTimeout=30";
		String user = "neondb_owner";
		String password = "npg_w5UGmkIbOg8A"; // put your actual password

		try (Connection conn = DriverManager.getConnection(url, user, password)) {
			if (conn != null && !conn.isClosed()) {
				System.out.println("✅ Successfully connected to Neon PostgreSQL!");
			} else {
				System.out.println("❌ Failed to connect to Neon PostgreSQL.");
			}
		} catch (SQLException e) {
			System.err.println("❌ Connection error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
