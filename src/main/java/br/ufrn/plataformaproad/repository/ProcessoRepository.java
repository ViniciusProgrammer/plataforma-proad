package br.ufrn.plataformaproad.repository;

import br.ufrn.plataformaproad.domain.Processo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessoRepository extends JpaRepository<Processo, Long> {
}