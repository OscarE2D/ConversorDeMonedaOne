import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class ConversorMonedas {

    private String apiKey = "8279fbb5a94fac6c2b6ece95"; // Reemplaza con tu API Key real
    private String apiUrl = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/";

    public String[] obtenerMonedas() throws IOException {
        URL url = new URL(apiUrl + "COP"); // Puedes usar cualquier moneda base para obtener la lista
        Map<String, Double> tasas = obtenerTasasDeCambio(url);
        return tasas.keySet().toArray(new String[0]);
    }

    public double convertir(String monedaDesde, String monedaHacia, double cantidad) throws IOException {
        URL url = new URL(apiUrl + monedaDesde);
        Map<String, Double> tasas = obtenerTasasDeCambio(url);
        double tasa = tasas.get(monedaHacia);
        return cantidad * tasa;
    }

    private Map<String, Double> obtenerTasasDeCambio(URL url) throws IOException {
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        conexion.setRequestMethod("GET");

        BufferedReader lector = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
        StringBuilder respuesta = new StringBuilder();
        String linea;
        while ((linea = lector.readLine()) != null) {
            respuesta.append(linea);
        }
        lector.close();

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(respuesta.toString(), JsonObject.class);
        JsonObject conversionRates = jsonObject.getAsJsonObject("conversion_rates");

        return gson.fromJson(conversionRates, Map.class);
    }
}