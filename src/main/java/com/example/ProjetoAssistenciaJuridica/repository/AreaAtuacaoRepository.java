package com.example.ProjetoAssistenciaJuridica.repository;

import com.example.ProjetoAssistenciaJuridica.model.AreaAtuacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Opcional, mas boa prática
public interface AreaAtuacaoRepository extends JpaRepository<AreaAtuacao, Long> {
    // Você pode adicionar métodos de busca personalizados aqui se precisar no futuro,
    // por exemplo: Optional<AreaAtuacao> findByNome(String nome);
}
