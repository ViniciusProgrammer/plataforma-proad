package br.ufrn.plataformaproad.service;

import br.ufrn.plataformaproad.config.ApiConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AutenticacaoService {

    private final ApiConfig apiConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public AutenticacaoService(ApiConfig apiConfig, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.apiConfig = apiConfig;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public String obterToken() {
        String url = String.format("%s/authz-server/oauth/token?client_id=%s&client_secret=%s&grant_type=client_credentials",
                apiConfig.getUrlBaseAutenticacao(),
                apiConfig.getClientId(),
                apiConfig.getClientSecret());

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, null, String.class);

        try {
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            return rootNode.path("access_token").asText();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter token de autenticação", e);
        }
    }
}
