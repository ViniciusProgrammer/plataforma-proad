package br.ufrn.plataformaproad.controller;

import br.ufrn.plataformaproad.service.ApiUfrnService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/ufrn")
public class ApiUfrnController {

    private final ApiUfrnService apiUfrnService;

    public ApiUfrnController(ApiUfrnService apiUfrnService) {
        this.apiUfrnService = apiUfrnService;
    }

    @GetMapping("/processo/{id}")
    public ResponseEntity<String> obterProcesso(@PathVariable String id) {
        String resposta = apiUfrnService.obterProcessoPorId(id);
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/processos")
    public ResponseEntity<List<Map<String, Object>>> listarProcessos(
            @RequestParam(defaultValue = "100") int limit,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(required = false) String orgaoOrigem,
            @RequestParam(required = false) String numeroProcesso) {

        try {
            String processosJson = apiUfrnService.listarProcessos(limit, offset, orgaoOrigem, numeroProcesso);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode processos = objectMapper.readTree(processosJson);

            List<Map<String, Object>> resultado = new ArrayList<>();

            if (processos.isArray()) {
                for (JsonNode processo : processos) {
                    Map<String, Object> processoComMovimentacao = new HashMap<>();

                    Long idProcesso = processo.has("id-processo") ? processo.get("id-processo").asLong() : null;

                    if (idProcesso == null) {
                        continue; // Pula processos sem ID
                    }

                    processoComMovimentacao.put("idProcesso", idProcesso);

                    // Montar o número do processo a partir dos campos disponíveis
                    String numeroFormatado = String.format("%d.%d/%d-%02d",
                            processo.has("radical") ? processo.get("radical").asInt() : 0,
                            processo.has("num-protocolo") ? processo.get("num-protocolo").asInt() : 0,
                            processo.has("ano") ? processo.get("ano").asInt() : 0,
                            processo.has("dv") ? processo.get("dv").asInt() : 0
                    );
                    processoComMovimentacao.put("numero", numeroFormatado);

                    processoComMovimentacao.put("radical", processo.has("radical") ? processo.get("radical").asInt() : null);
                    processoComMovimentacao.put("numProtocolo", processo.has("num-protocolo") ? processo.get("num-protocolo").asInt() : null);
                    processoComMovimentacao.put("ano", processo.has("ano") ? processo.get("ano").asInt() : null);
                    processoComMovimentacao.put("dv", processo.has("dv") ? processo.get("dv").asInt() : null);

                    try {
                        String movimentacoesJson = apiUfrnService.obterUltimaMovimentacaoPorProcesso(idProcesso);
                        JsonNode movimentacoes = objectMapper.readTree(movimentacoesJson);

                        if (movimentacoes.isArray() && movimentacoes.size() > 0) {
                            JsonNode ultimaMovimentacao = movimentacoes.get(0);

                            processoComMovimentacao.put("unidadeAtual",
                                    ultimaMovimentacao.has("unidade-destino") ? ultimaMovimentacao.get("unidade-destino").asText() : "N/A");
                            processoComMovimentacao.put("dataRecebimento",
                                    ultimaMovimentacao.has("data-recebimento-destino") ? ultimaMovimentacao.get("data-recebimento-destino").asLong() : null);
                            processoComMovimentacao.put("unidadeOrigem",
                                    ultimaMovimentacao.has("unidade-origem") ? ultimaMovimentacao.get("unidade-origem").asText() : "N/A");
                        } else {
                            processoComMovimentacao.put("unidadeAtual", "Sem movimentação");
                        }
                    } catch (Exception e) {
                        System.err.println("Erro ao buscar movimentação do processo " + idProcesso + ": " + e.getMessage());
                        processoComMovimentacao.put("unidadeAtual", "Erro ao buscar");
                    }

                    resultado.add(processoComMovimentacao);
                }
            }

            return ResponseEntity.ok(resultado);

        } catch (Exception e) {
            System.err.println("Erro ao listar processos: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/processos/proad")
    public ResponseEntity<List<Map<String, Object>>> listarProcessosPROAD(
            @RequestParam(defaultValue = "100") int limit,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(required = false) String orgaoOrigem,
            @RequestParam(required = false) String numeroProcesso) {

        try {
            String processosJson = apiUfrnService.listarProcessos(limit, offset, "PROAD", numeroProcesso);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode processos = objectMapper.readTree(processosJson);

            List<Map<String, Object>> resultado = new ArrayList<>();

            if (processos.isArray()) {
                for (JsonNode processo : processos) {
                    Map<String, Object> processoComMovimentacao = new HashMap<>();

                    Long idProcesso = processo.has("id-processo") ? processo.get("id-processo").asLong() : null;

                    if (idProcesso == null) {
                        continue; // Pula processos sem ID
                    }

                    processoComMovimentacao.put("idProcesso", idProcesso);

                    // Montar o número do processo a partir dos campos disponíveis
                    String numeroFormatado = String.format("%d.%d/%d-%02d",
                            processo.has("radical") ? processo.get("radical").asInt() : 0,
                            processo.has("num-protocolo") ? processo.get("num-protocolo").asInt() : 0,
                            processo.has("ano") ? processo.get("ano").asInt() : 0,
                            processo.has("dv") ? processo.get("dv").asInt() : 0
                    );
                    processoComMovimentacao.put("numero", numeroFormatado);

                    processoComMovimentacao.put("radical", processo.has("radical") ? processo.get("radical").asInt() : null);
                    processoComMovimentacao.put("numProtocolo", processo.has("num-protocolo") ? processo.get("num-protocolo").asInt() : null);
                    processoComMovimentacao.put("ano", processo.has("ano") ? processo.get("ano").asInt() : null);
                    processoComMovimentacao.put("dv", processo.has("dv") ? processo.get("dv").asInt() : null);

                    processoComMovimentacao.put("orgaoOrigem", processo.has("orgao-origem") ? processo.get("orgao-origem").asText() : "N/A");

                    processoComMovimentacao.put("status", processo.has("status") ? processo.get("status").asText() : "N/A");

                    processoComMovimentacao.put("data-cadastro",
                            processo.has("data-cadastro") ? processo.get("data-cadastro").asLong() : null);

                    processoComMovimentacao.put("assunto-processo",
                            processo.has("assunto-processo") ? processo.get("assunto-processo").asText() : "N/A");



                    try {
                        String movimentacoesJson = apiUfrnService.obterUltimaMovimentacaoPorProcesso(idProcesso);
                        JsonNode movimentacoes = objectMapper.readTree(movimentacoesJson);

                        if (movimentacoes.isArray() && movimentacoes.size() > 0) {
                            JsonNode ultimaMovimentacao = movimentacoes.get(0);

                            processoComMovimentacao.put("unidadeAtual",
                                    ultimaMovimentacao.has("unidade-destino") ? ultimaMovimentacao.get("unidade-destino").asText() : "N/A");
                            processoComMovimentacao.put("dataRecebimento",
                                    ultimaMovimentacao.has("data-recebimento-destino") ? ultimaMovimentacao.get("data-recebimento-destino").asLong() : null);
                            processoComMovimentacao.put("unidadeOrigem",
                                    ultimaMovimentacao.has("unidade-origem") ? ultimaMovimentacao.get("unidade-origem").asText() : "N/A");
                        } else {
                            processoComMovimentacao.put("unidadeAtual", "Sem movimentação");
                        }
                    } catch (Exception e) {
                        System.err.println("Erro ao buscar movimentação do processo " + idProcesso + ": " + e.getMessage());
                        processoComMovimentacao.put("unidadeAtual", "Erro ao buscar");
                    }

                    resultado.add(processoComMovimentacao);
                }
            }

            return ResponseEntity.ok(resultado);

        } catch (Exception e) {
            System.err.println("Erro ao listar processos: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/processos/dfi")
    public ResponseEntity<List<Map<String, Object>>> listarProcessosDFI(
            @RequestParam(defaultValue = "100") int limit,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(required = false) String orgaoOrigem,
            @RequestParam(required = false) String numeroProcesso) {

        try {
            String processosJson = apiUfrnService.listarProcessos(limit, offset, "COMPRAS - DIVISÃO DE FASE INTERNA DE COMPRAS", numeroProcesso);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode processos = objectMapper.readTree(processosJson);

            List<Map<String, Object>> resultado = new ArrayList<>();

            if (processos.isArray()) {
                for (JsonNode processo : processos) {
                    Map<String, Object> processoComMovimentacao = new HashMap<>();

                    Long idProcesso = processo.has("id-processo") ? processo.get("id-processo").asLong() : null;

                    if (idProcesso == null) {
                        continue; // Pula processos sem ID
                    }

                    processoComMovimentacao.put("idProcesso", idProcesso);

                    // Montar o número do processo a partir dos campos disponíveis
                    String numeroFormatado = String.format("%d.%d/%d-%02d",
                            processo.has("radical") ? processo.get("radical").asInt() : 0,
                            processo.has("num-protocolo") ? processo.get("num-protocolo").asInt() : 0,
                            processo.has("ano") ? processo.get("ano").asInt() : 0,
                            processo.has("dv") ? processo.get("dv").asInt() : 0
                    );
                    processoComMovimentacao.put("numero", numeroFormatado);

                    processoComMovimentacao.put("radical", processo.has("radical") ? processo.get("radical").asInt() : null);
                    processoComMovimentacao.put("numProtocolo", processo.has("num-protocolo") ? processo.get("num-protocolo").asInt() : null);
                    processoComMovimentacao.put("ano", processo.has("ano") ? processo.get("ano").asInt() : null);
                    processoComMovimentacao.put("dv", processo.has("dv") ? processo.get("dv").asInt() : null);

                    try {
                        String movimentacoesJson = apiUfrnService.obterUltimaMovimentacaoPorProcesso(idProcesso);
                        JsonNode movimentacoes = objectMapper.readTree(movimentacoesJson);

                        if (movimentacoes.isArray() && movimentacoes.size() > 0) {
                            JsonNode ultimaMovimentacao = movimentacoes.get(0);

                            processoComMovimentacao.put("unidadeAtual",
                                    ultimaMovimentacao.has("unidade-destino") ? ultimaMovimentacao.get("unidade-destino").asText() : "N/A");
                            processoComMovimentacao.put("dataRecebimento",
                                    ultimaMovimentacao.has("data-recebimento-destino") ? ultimaMovimentacao.get("data-recebimento-destino").asLong() : null);
                            processoComMovimentacao.put("unidadeOrigem",
                                    ultimaMovimentacao.has("unidade-origem") ? ultimaMovimentacao.get("unidade-origem").asText() : "N/A");
                        } else {
                            processoComMovimentacao.put("unidadeAtual", "Sem movimentação");
                        }
                    } catch (Exception e) {
                        System.err.println("Erro ao buscar movimentação do processo " + idProcesso + ": " + e.getMessage());
                        processoComMovimentacao.put("unidadeAtual", "Erro ao buscar");
                    }

                    resultado.add(processoComMovimentacao);
                }
            }

            return ResponseEntity.ok(resultado);

        } catch (Exception e) {
            System.err.println("Erro ao listar processos: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }



    @GetMapping("/processo/{idProcesso}/documentos")
    public ResponseEntity<String> listarDocumentosPorProcesso(@PathVariable Long idProcesso) {
        String resposta = apiUfrnService.listarDocumentosPorProcesso(idProcesso);
        return ResponseEntity.ok(resposta);
    }
}
