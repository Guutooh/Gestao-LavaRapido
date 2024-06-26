package br.com.jatao.model;

import br.com.jatao.enums.Adicionais;
import br.com.jatao.enums.Lavagem;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
@Entity
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrdemServico implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @Column(nullable = false)
    private LocalDateTime data;

    @OneToOne
    private Carro carro;

    @Enumerated(EnumType.STRING)
    private Lavagem lavagem;

    @Enumerated(EnumType.STRING)
    private Adicionais adicionais;

    @OneToOne
    private Cliente cliente;



}
