package br.ufrn.plataformaproad.repository;

import br.ufrn.plataformaproad.domain.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    Movimentacao findTopByIdProcessoOrderByDataRecebimentoDestinoDesc(Long idProcesso);
}