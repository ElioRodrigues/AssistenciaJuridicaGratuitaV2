package com.example.ProjetoAssistenciaJuridica.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Solicitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Removido: private String tema;
    // Removido: private String categoria;

    @Lob // Para textos mais longos
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusSolicitacao status = StatusSolicitacao.ABERTA; // status inicial

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advogado_id", nullable = true) // Advogado pode ser nulo inicialmente
    private Advogado advogado;

    // *** NOVO RELACIONAMENTO MANY-TO-ONE COM AreaAtuacao ***
    @ManyToOne(fetch = FetchType.LAZY) // FetchType.LAZY é geralmente recomendado
    @JoinColumn(name = "area_id", nullable = false) // Assumindo que toda solicitação DEVE ter uma área
    private AreaAtuacao area; // Referência à área de atuação
    // *** FIM DO NOVO RELACIONAMENTO ***

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    private LocalDateTime dataAceite;

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Removido: getTema() e setTema()
    // Removido: getCategoria() e setCategoria()

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusSolicitacao getStatus() {
        return status;
    }

    public void setStatus(StatusSolicitacao status) {
        this.status = status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Advogado getAdvogado() {
        return advogado;
    }

    public void setAdvogado(Advogado advogado) {
        this.advogado = advogado;
    }

    // *** GETTER E SETTER PARA area ***
    public AreaAtuacao getArea() {
        return area;
    }

    public void setArea(AreaAtuacao area) {
        this.area = area;
    }
    // *** FIM GETTER/SETTER ***

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    // Removido set para dataCriacao pois é inicializada e não deve ser atualizada (updatable=false)
    // public void setDataCriacao(LocalDateTime dataCriacao) {
    //     this.dataCriacao = dataCriacao;
    // }

    public LocalDateTime getDataAceite() {
        return dataAceite;
    }

    public void setDataAceite(LocalDateTime dataAceite) {
        this.dataAceite = dataAceite;
    }
}
