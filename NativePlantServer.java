/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package nativeplantserver;

import java.sql.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author bingg
 */
public class NativePlantServer {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/plantdb"; // ADD ACTUAL
    private static final String DB_USER = "root"; // ADD ACTUAL
    private static final String DB_PASSWORD = "passwordPASSWORD"; // ADD ACTUAL
    private static final int SERVER_PORT = 8080;
    
    
    
    public static void main(String[] args) {
        
        
        try {
            // 1. Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish connection to the MySQL database
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected to the database.");

            // Create a ServerSocket that listens on port 8080
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server started. Waiting for clients...");

            while (true) {
                // Accept incoming client connections
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                // Create input stream to receive data from the client
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                // Read the state name sent by the client
                String stateName = in.readLine();
                System.out.println("Received state name from client: " + stateName);

                // Construct SQL query
                String query = "SELECT * FROM " + stateName;

                // Execute query
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                // Create output stream to send data to the client
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                // Process retrieved data and send it to the client
                StringBuilder responseData = new StringBuilder();
                while (resultSet.next()) {
                    // Retrieve data from columns
                    String symbol = resultSet.getString("symbol");
                    String synonymSymbol = resultSet.getString("synonym symbol");
                    String scientificName = resultSet.getString("scientific name with author");
                    String stateCommonName = resultSet.getString("state common name");
                    String family = resultSet.getString("family");

                    // Append data to response string
                    responseData.append("Symbol: ").append(symbol).append(", Scientific Name: ").append(scientificName).append("\n");
                }

                // Send data to the client
                out.println(responseData.toString());

                // Close resources
                resultSet.close();
                statement.close();

                // Close the connection with the client
                clientSocket.close();
            }
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Failed to connect to database!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
