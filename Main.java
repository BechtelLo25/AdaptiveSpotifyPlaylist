import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {

    // Authorization token that must have been created previously.
    // See: https://developer.spotify.com/documentation/web-api/concepts/authorization
    static String token = "BQBRIEtcws_NsTKLg8iAgI-FArWvaRLns_g9RMUmF99w8Kz33Po-R0BDSM2_UnpYzHqcCWfs8tPXAOA1GSocyhIo8aCvKP8z4Xci_wtEgp_blQ_tsxq9GrrrPPtH8EcqR-3_Ioo9Z1p8aeYcnpVvaN4mvGBGPARXTIKAjQYbPkR4uTZJEKhrOYVQQcqRT1GqLsweCxbIS6EaEX0SeLpHigVIgA";

    public static void main(String[] args) throws IOException {
        String topTracks = getTopTracks();
        System.out.println(topTracks);
    }

    public static String getTopTracks() throws IOException {
        // Endpoint reference: https://developer.spotify.com/documentation/web-api/reference/get-users-top-artists-and-tracks
        URL url = new URL("https://api.spotify.com/v1/me/top/tracks?time_range=long_term&limit=5");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.setDoOutput(true);
 
        StringBuilder response = new StringBuilder();
        Scanner scanner = new Scanner(conn.getInputStream());
        while (scanner.hasNextLine()) {
            response.append(scanner.nextLine());
        }
        scanner.close();
        return response.toString();
    }
}
