package br.ufrn.plataformaproad.service;

import br.ufrn.plataformaproad.config.ApiConfig;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApiUfrnService {

    private final ApiConfig apiConfig;
    private final RestTemplate restTemplate;
    private final AutenticacaoService autenticacaoService;

    public ApiUfrnService(ApiConfig apiConfig, RestTemplate restTemplate, AutenticacaoService autenticacaoService) {
        this.apiConfig = apiConfig;
        this.restTemplate = restTemplate;
        this.autenticacaoService = autenticacaoService;
    }

    public String obterDados(String path) {
        return obterDados(path, null);
    }

    public String obterDados(String path, Map<String, Object> queryParams) {
        try {
            String token = autenticacaoService.obterToken();
            if (token == null) {
                throw new RuntimeException("Não foi possível obter o token de autenticação");
            }

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiConfig.getUrlBase() + path);

            if (queryParams != null) {
                queryParams.forEach((key, value) -> {
                    if (value != null) {
                        builder.queryParam(key, value);
                    }
                });
            }

            String url = builder.build().toString();
            System.out.println("URL da requisição: " + url);
            System.out.println("Token usado (primeiros 15 caracteres): " + token.substring(0, Math.min(15, token.length())) + "...");
            System.out.println("x-api-key usado: " + apiConfig.getXApiKey().substring(0, Math.min(10, apiConfig.getXApiKey().length())) + "...");

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "bearer " + token);
            headers.set("x-api-key", apiConfig.getXApiKey());
            headers.set("Accept", "application/json");
            headers.set("Content-Type", "application/json");

            HttpEntity<String> requestEntity = new HttpEntity<>(headers);

            // Adicionar tratamento específico para erros HTTP
            try {
                ResponseEntity<String> responseEntity = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        requestEntity,
                        String.class
                );

                System.out.println("Status da resposta: " + responseEntity.getStatusCode());
                System.out.println("Headers da resposta: " + responseEntity.getHeaders());

                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    String resposta = responseEntity.getBody();

                    if (resposta == null) {
                        System.out.println("Resposta da API é NULL");
                        return "{}";
                    } else {
                        System.out.println("Tamanho da resposta: " + resposta.length() + " caracteres");
                        if (resposta.length() > 2) { // Maior que "{}" vazio
                            String previewContent = resposta.length() > 100 ?
                                    resposta.substring(0, 100) + "..." : resposta;
                            System.out.println("Prévia do conteúdo: " + previewContent);
                        } else {
                            System.out.println("ALERTA: Resposta da API está vazia mesmo com status 200 OK");
                        }
                        return resposta;
                    }
                } else {
                    throw new RuntimeException("Erro ao acessar a API. Status: " + responseEntity.getStatusCode());
                }
            } catch (Exception e) {
               throw e;
            }
        } catch (Exception e) {
            System.err.println("Erro ao processar requisição para " + path + ": " + e.getMessage());
            e.printStackTrace();
            return "{}";
        }
    }

    public String listarMovimentacoesPorProcesso(Long idProcesso, int limit, int offset) {
        if (idProcesso == null) {
            throw new IllegalArgumentException("ID do processo não pode ser nulo");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("id-processo", idProcesso);
        params.put("limit", limit);
        params.put("offset", offset);
        params.put("order-desc", "data-recebimento-destino");

        return obterDados("/processo/" + apiConfig.getVersao() + "/movimentacoes", params);
    }

    public String obterUltimaMovimentacaoPorProcesso(Long idProcesso) {
        Map<String, Object> params = new HashMap<>();
        params.put("id-processo", idProcesso);
        params.put("limit", 1);
        params.put("offset", 0);
        params.put("order-desc", "data-recebimento-destino");

        System.out.println("Buscando última movimentação do processo: " + idProcesso);

        return obterDados("/processo/" + apiConfig.getVersao() + "/movimentacoes", params);
    }

    public String listarMovimentacoes(int limit, int offset) {
        Map<String, Object> params = new HashMap<>();
        params.put("limit", limit);
        params.put("offset", offset);
        params.put("order-desc", "data-recebimento-destino");

        return obterDados("/processo/" + apiConfig.getVersao() + "/movimentacoes", params);
    }


    public String listarProcessos(int limit, int offset, String orgaoOrigem, String numeroProcesso) {
        Map<String, Object> params = new HashMap<>();
        params.put("limit", limit);
        params.put("offset", offset);


        // Verificando se os parâmetros estão sendo adicionados corretamente
        System.out.println("Consultando processos com parâmetros: ");
        System.out.println("- limit: " + limit);
        System.out.println("- offset: " + offset);

        if (orgaoOrigem != null && !orgaoOrigem.isEmpty()) {
            System.out.println("- orgaoOrigem: " + orgaoOrigem);
            // Verificar se o formato do parâmetro está correto conforme a API
            params.put("orgao-origem", orgaoOrigem);
        }

        if (numeroProcesso != null && !numeroProcesso.isEmpty()) {
            System.out.println("- numeroProcesso: " + numeroProcesso);
            // Verificar se o formato do parâmetro está correto conforme a API
            params.put("id-processo", numeroProcesso);
        }
        params.put("order-desc", "ano");

        return obterDados("/processo/" + apiConfig.getVersao() + "/processos", params);
    }

    public String obterProcessoPorId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do processo não pode ser nulo ou vazio");
        }
        return obterDados("/processo/" + apiConfig.getVersao() + "/processos/" + id);
    }

    public List<String> listarAte1000Processos(String orgaoOrigem, String numeroProcesso) {
        int limit = 100;   // Quantidade por requisição
        int total = 1000;  // Quantidade máxima a buscar
        int offset = 0;

        List<String> resultados = new ArrayList<>();

        while (offset < total) {
            Map<String, Object> params = new HashMap<>();
            params.put("limit", limit);
            params.put("offset", offset);

            if (orgaoOrigem != null && !orgaoOrigem.isEmpty()) {
                params.put("orgao-origem", orgaoOrigem);
            }

            if (numeroProcesso != null && !numeroProcesso.isEmpty()) {
                params.put("id-processo", numeroProcesso);
            }

            // Ordenar mais novos primeiro
            params.put("order-desc", "ano");

            System.out.println("Consultando processos com parâmetros: ");
            System.out.println("- limit: " + limit);
            System.out.println("- offset: " + offset);
            if (orgaoOrigem != null) System.out.println("- orgaoOrigem: " + orgaoOrigem);
            if (numeroProcesso != null) System.out.println("- numeroProcesso: " + numeroProcesso);

            String resposta = obterDados("/processo/" + apiConfig.getVersao() + "/processos", params);

            resultados.add(resposta);

            offset += limit; // avança o próximo lote
        }

        return resultados;
    }

    public String listarDocumentosPorProcesso(Long idProcesso) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("id-processo", idProcesso);
        return obterDados("/processo/v1/documentos", queryParams);
    }



}
