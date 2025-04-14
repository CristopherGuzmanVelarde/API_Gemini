package vallegrande.gemini.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";
    private static final String API_KEY = "AIzaSyBPvEaC_Em6lJYhuudjBlBi1jaoE4hXJ0g";
    
    private final RestTemplate restTemplate;
    private final List<Map<String, String>> historyList;
    
    public GeminiService() {
        this.restTemplate = new RestTemplate();
        this.historyList = new ArrayList<>();
    }
    
    public String generateContent(String prompt) {
        try {
            // Preparar headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            // Preparar el cuerpo de la solicitud según la estructura de la API de Gemini
            Map<String, Object> requestBody = new HashMap<>();
            Map<String, Object> contents = new HashMap<>();
            Map<String, Object> parts = new HashMap<>();
            
            parts.put("text", prompt);
            
            List<Map<String, Object>> partsList = new ArrayList<>();
            partsList.add(parts);
            
            contents.put("parts", partsList);
            
            List<Map<String, Object>> contentsList = new ArrayList<>();
            contentsList.add(contents);
            
            requestBody.put("contents", contentsList);
            
            // Crear la entidad HTTP con headers y body
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            
            // Realizar la solicitud POST a la API de Gemini
            String fullUrl = API_URL + "?key=" + API_KEY;
            Map<String, Object> response = restTemplate.postForObject(fullUrl, entity, Map.class);
            
            // Extraer la respuesta del modelo
            String generatedText = extractTextFromResponse(response);
            
            // Guardar en el historial
            saveToHistory(prompt, generatedText);
            
            return generatedText;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al generar contenido: " + e.getMessage();
        }
    }
    
    private String extractTextFromResponse(Map<String, Object> response) {
        try {
            if (response != null && response.containsKey("candidates")) {
                List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
                if (!candidates.isEmpty()) {
                    Map<String, Object> candidate = candidates.get(0);
                    if (candidate.containsKey("content")) {
                        Map<String, Object> content = (Map<String, Object>) candidate.get("content");
                        if (content.containsKey("parts")) {
                            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
                            if (!parts.isEmpty() && parts.get(0).containsKey("text")) {
                                return (String) parts.get(0).get("text");
                            }
                        }
                    }
                }
            }
            return "No se pudo extraer texto de la respuesta";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al procesar la respuesta: " + e.getMessage();
        }
    }
    
    private void saveToHistory(String prompt, String response) {
        Map<String, String> historyItem = new HashMap<>();
        historyItem.put("prompt", prompt);
        historyItem.put("response", response);
        historyList.add(historyItem);
    }
    
    public List<Map<String, String>> getHistory() {
        return historyList;
    }
}