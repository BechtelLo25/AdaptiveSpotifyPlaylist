import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.stream.Collectors;

public class Main2 {

    private static final String CLIENT_ID = "b1c7068c0a2243a7ba56eab5a908a5fd";
    private static final String CLIENT_SECRET = "fa7d4e0212504cfca640348d51a277a8";
    private static final String REDIRECT_URI = "http://localhost:8888/callback";

    public static void main(String[] args) throws IOException {
        String authorizationUrl = "https://accounts.spotify.com/authorize?" +
                "client_id=" + CLIENT_ID +
                "&response_type=code" +
                "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8) +
                "&scope=user-read-private%20user-read-email" +
                "&state=" + generateRandomString(16);

        System.out.println("Please go to the following URL and grant access:");
        System.out.println(authorizationUrl);

        requestAccessToken("AQBf3QL8gMQjew55-LhVcEiZtkSKx9ZCjJQx7lMJ9JSfTvFPsbi2K-Gut6JWmtIftLtuwCq2RGlWZhvt0-CY39ZSJF2E2JOcxU9Gi_k6-au5eotLwHIonQYXAa64IlXE9Y7CLwLFyHA8OnFBNo1-lZrpIhDNvKiDULq5WcJvWtfBRqQFMb8rw_kbfLOJhwrOCgaj4X9p625m17fr1p9HjXWQyV_gSg&state=FHHyxQvRJS8fpd3fiZRKsQ");
        // After user grants access and gets redirected to the callback URL, extract the authorization code from the query parameters.
        // Then, use this code to request an access token.
    }

    private static String generateRandomString(int length) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private static String requestAccessToken(String code) throws IOException {
        URL url = new URL("https://accounts.spotify.com/api/token");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        String requestBody = "grant_type=authorization_code" +
                "&code=" + code +
                "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8);

        String credentials = CLIENT_ID + ":" + CLIENT_SECRET;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        conn.setRequestProperty("Authorization", "Basic " + encodedCredentials);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        try (OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream())) {
            writer.write(requestBody);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String response = reader.lines().collect(Collectors.joining(System.lineSeparator()));

            // Parse JSON response
            // Sample response: {"access_token":"your_access_token","token_type":"Bearer","expires_in":3600,"scope":"user-read-private user-read-email"}
            String accessToken = response;

            System.out.println(accessToken);
            return accessToken;
        
    }
}
}