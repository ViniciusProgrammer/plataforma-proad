// src/main/java/br/ufrn/plataformaproad/domain/Movimentacao.java
package br.ufrn.plataformaproad.domain;

import jakarta.persistence.*;

@Entity
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMovimentacao;

    private Long idProcesso;

    private Long dataEnvioOrigem;
    private Long dataRecebimentoDestino;
    private String enviadoPor;
    private String recebidoPor;
    private String unidadeOrigem;
    private String unidadeDestino;

    // Getters e Setters
    public Long getIdMovimentacao() { return idMovimentacao; }
    public void setIdMovimentacao(Long idMovimentacao) { this.idMovimentacao = idMovimentacao; }

    public Long getIdProcesso() { return idProcesso; }
    public void setIdProcesso(Long idProcesso) { this.idProcesso = idProcesso; }

    public Long getDataEnvioOrigem() { return dataEnvioOrigem; }
    public void setDataEnvioOrigem(Long dataEnvioOrigem) { this.dataEnvioOrigem = dataEnvioOrigem; }

    public Long getDataRecebimentoDestino() { return dataRecebimentoDestino; }
    public void setDataRecebimentoDestino(Long dataRecebimentoDestino) { this.dataRecebimentoDestino = dataRecebimentoDestino; }

    public String getEnviadoPor() { return enviadoPor; }
    public void setEnviadoPor(String enviadoPor) { this.enviadoPor = enviadoPor; }

    public String getRecebidoPor() { return recebidoPor; }
    public void setRecebidoPor(String recebidoPor) { this.recebidoPor = recebidoPor; }

    public String getUnidadeOrigem() { return unidadeOrigem; }
    public void setUnidadeOrigem(String unidadeOrigem) { this.unidadeOrigem = unidadeOrigem; }

    public String getUnidadeDestino() { return unidadeDestino; }
    public void setUnidadeDestino(String unidadeDestino) { this.unidadeDestino = unidadeDestino; }
}
