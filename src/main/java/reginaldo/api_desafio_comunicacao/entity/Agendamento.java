package reginaldo.api_desafio_comunicacao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import reginaldo.api_desafio_comunicacao.ENUM.StatusAgendamento;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "agendamento")
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    private LocalDateTime dataHora;


    private String destinatario;


    private String mensagem;

    @Enumerated(EnumType.STRING)
    private StatusAgendamento statusAgendamento = StatusAgendamento.AGENDADO;

}
