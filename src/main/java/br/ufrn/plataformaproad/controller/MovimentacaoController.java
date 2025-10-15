package br.ufrn.plataformaproad.controller;

import br.ufrn.plataformaproad.service.ApiUfrnService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/movimentacoes")
public class MovimentacaoController {

    private final ApiUfrnService apiUfrnService;
    private final ObjectMapper objectMapper;

    public MovimentacaoController(ApiUfrnService apiUfrnService) {
        this.apiUfrnService = apiUfrnService;
        this.objectMapper = new ObjectMapper();
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> listar(
            @RequestParam(required = false) Long idProcesso,
            @RequestParam(defaultValue = "100") Integer limit,
            @RequestParam(defaultValue = "0") Integer offset) {

        try {
            String movimentacoesJson;

            if (idProcesso != null) {
                movimentacoesJson = apiUfrnService.listarMovimentacoesPorProcesso(idProcesso, limit, offset);
            } else {
                Map<String, Object> params = new HashMap<>();
                params.put("limit", limit);
                params.put("offset", offset);
                params.put("order-desc", "data-recebimento-destino");
                movimentacoesJson = apiUfrnService.obterDados("/processo/v1/movimentacoes", params);
            }

            JsonNode movimentacoes = objectMapper.readTree(movimentacoesJson);
            List<Map<String, Object>> resultado = new ArrayList<>();

            if (movimentacoes.isArray()) {
                for (JsonNode mov : movimentacoes) {
                    Map<String, Object> movimentacao = new HashMap<>();
                    movimentacao.put("idMovimentacao", mov.get("id-movimentacao").asLong());
                    movimentacao.put("idProcesso", mov.get("id-processo").asLong());
                    movimentacao.put("unidadeOrigem", mov.get("unidade-origem").asText());
                    movimentacao.put("unidadeDestino", mov.get("unidade-destino").asText());
                    movimentacao.put("dataEnvio", mov.get("data-envio-origem").asLong());
                    movimentacao.put("dataRecebimento", mov.get("data-recebimento-destino").asLong());
                    movimentacao.put("enviadoPor", mov.has("enviado-por") ? mov.get("enviado-por").asText() : null);
                    movimentacao.put("recebidoPor", mov.has("recebido-por") ? mov.get("recebido-por").asText() : null);
                    resultado.add(movimentacao);
                }
            }

            return ResponseEntity.ok(resultado);

        } catch (Exception e) {
            System.err.println("Erro ao buscar movimentações: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{idMovimentacao}")
    public ResponseEntity<Map<String, Object>> buscarPorId(@PathVariable Long idMovimentacao) {
        try {
            String movimentacaoJson = apiUfrnService.obterDados(
                    "/processo/v1/movimentacoes/" + idMovimentacao);

            JsonNode mov = objectMapper.readTree(movimentacaoJson);

            Map<String, Object> movimentacao = new HashMap<>();
            movimentacao.put("idMovimentacao", mov.get("id-movimentacao").asLong());
            movimentacao.put("idProcesso", mov.get("id-processo").asLong());
            movimentacao.put("unidadeOrigem", mov.get("unidade-origem").asText());
            movimentacao.put("unidadeDestino", mov.get("unidade-destino").asText());
            movimentacao.put("dataEnvio", mov.get("data-envio-origem").asLong());
            movimentacao.put("dataRecebimento", mov.get("data-recebimento-destino").asLong());
            movimentacao.put("enviadoPor", mov.has("enviado-por") ? mov.get("enviado-por").asText() : null);
            movimentacao.put("recebidoPor", mov.has("recebido-por") ? mov.get("recebido-por").asText() : null);

            return ResponseEntity.ok(movimentacao);

        } catch (Exception e) {
            System.err.println("Erro ao buscar movimentação: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
