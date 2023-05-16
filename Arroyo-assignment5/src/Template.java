import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public abstract class Template {
    public HashMap<String, Double> loadCurrencies() throws Exception {
        HashMap<String, Double> currencyMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length == 2) {
                    String currencyCode = tokens[0];
                    double rate = Double.parseDouble(tokens[1]);
                    currencyMap.put(currencyCode, rate);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currencyMap;
    }

    protected abstract InputStream getInputStream() throws Exception;
}

