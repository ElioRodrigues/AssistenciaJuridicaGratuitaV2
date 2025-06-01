package com.example.ProjetoAssistenciaJuridica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cliente") // Define um prefixo para todas as rotas deste controlador
public class ClienteDashboardController {

    @GetMapping("/dashboard") // Mapeia para GET /cliente/dashboard
    public String clienteDashboard() {
        // Retorna o nome do arquivo HTML do template (sem a extensão .html)
        // que está dentro da pasta 'templates/cliente/'
        return "cliente/cliente_dashboard";
    }

    // Você pode adicionar outros mapeamentos para o cliente aqui depois
    // Ex: @GetMapping("/nova-solicitacao"), @PostMapping("/salvar-solicitacao"), etc.
}
