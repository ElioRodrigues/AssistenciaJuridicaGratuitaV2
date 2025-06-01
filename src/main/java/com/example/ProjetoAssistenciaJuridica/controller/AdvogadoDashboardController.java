package com.example.ProjetoAssistenciaJuridica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/advogado") // Define o prefixo /advogado para as rotas
public class AdvogadoDashboardController {

    @GetMapping("/dashboard") // Mapeia para GET /advogado/dashboard
    public String advogadoDashboard() {
        // Retorna o nome do arquivo HTML do template (sem a extensão .html)
        // que está dentro da pasta 'templates/advogado/'
        return "advogado/advogado_dashboard";
    }

    // Adicione outros mapeamentos para o advogado aqui depois
    // Ex: @GetMapping("/listar-solicitacoes"), @PostMapping("/aceitar-solicitacao"), etc.
}
