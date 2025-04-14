package vallegrande.gemini.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vallegrande.gemini.service.GeminiService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gemini")
public class GeminiController {

    @Autowired
    private GeminiService geminiService;

    @PostMapping("/generate")
    public String generateContent(@RequestBody Map<String, String> request) {
        String prompt = request.get("text");
        return geminiService.generateContent(prompt);
    }

    // Nuevo endpoint para obtener el historial
    @GetMapping("/history")
    public List<Map<String, String>> getHistory() {
        return geminiService.getHistory();
    }
}