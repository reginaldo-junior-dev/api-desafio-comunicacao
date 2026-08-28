package reginaldo.api_desafio_comunicacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import reginaldo.api_desafio_comunicacao.entity.Agendamento;

import java.util.UUID;

public interface AgendamentoRepository extends JpaRepository<Agendamento, UUID> {
}
