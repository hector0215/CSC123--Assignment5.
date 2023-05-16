import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HTTPHook extends Template {
    private String url;

    public HTTPHook(String url) {
        this.url = url;
    }

    @Override
    protected InputStream getInputStream() {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<InputStream> response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());
            if (response.statusCode() == 200) {
                return response.body();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
