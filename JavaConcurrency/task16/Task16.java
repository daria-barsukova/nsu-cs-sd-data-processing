package task16;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


public class Task16 {
    private static final int PAGE_SIZE = 25;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Task16 <URL>");
            System.exit(1);
        }

        String urlString = args[0];

        try {
            fetchAndDisplayContent(urlString);
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static void fetchAndDisplayContent(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                displayContent(reader);
            }
        } else {
            System.out.println("HTTP GET request failed with error code: " + responseCode);
        }
        connection.disconnect();
    }

    private static void displayContent(BufferedReader reader) throws IOException {
        String line;
        int linesPrinted = 0;
        Scanner scanner = new Scanner(System.in);

        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            linesPrinted++;

            if (linesPrinted % PAGE_SIZE == 0) {
                System.out.println("Press enter to scroll down");
                scanner.nextLine();
            }
        }
        scanner.close();
    }
}
