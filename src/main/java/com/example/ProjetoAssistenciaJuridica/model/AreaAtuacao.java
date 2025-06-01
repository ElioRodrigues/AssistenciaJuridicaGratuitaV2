package com.example.ProjetoAssistenciaJuridica.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "area_atuacao") // Define o nome da tabela no banco
public class AreaAtuacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome; // Ex: "Direito de Família", "Direito do Trabalho"

    // Relacionamento inverso (opcional, mas útil se precisar listar advogados por área)
    @ManyToMany(mappedBy = "areasAtuacao")
    private Set<Advogado> advogados;

    // Construtores (padrão e com nome)
    public AreaAtuacao() {
    }

    public AreaAtuacao(String nome) {
        this.nome = nome;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Advogado> getAdvogados() {
        return advogados;
    }

    public void setAdvogados(Set<Advogado> advogados) {
        this.advogados = advogados;
    }

    // hashCode e equals (importante para coleções como Set)
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AreaAtuacao that = (AreaAtuacao) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }
}
