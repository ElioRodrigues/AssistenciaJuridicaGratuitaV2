package com.example.ProjetoAssistenciaJuridica.controller;

import com.example.ProjetoAssistenciaJuridica.model.AreaAtuacao; // Importar AreaAtuacao
import com.example.ProjetoAssistenciaJuridica.model.Solicitacao;
import com.example.ProjetoAssistenciaJuridica.repository.AreaAtuacaoRepository; // Importar AreaAtuacaoRepository
import com.example.ProjetoAssistenciaJuridica.service.SolicitacaoService;
import com.example.ProjetoAssistenciaJuridica.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//import java.util.Arrays; acho que não ta precisando mais
import java.util.List;

@Controller
public class SolicitacaoController {

    @Autowired
    private SolicitacaoService solicitacaoService;

    @Autowired
    private UserService userService;

    @Autowired
    private AreaAtuacaoRepository areaAtuacaoRepository;


    @GetMapping("/cliente/solicitacao/nova")
    @PreAuthorize("hasRole(\'CLIENTE\')")
    public String showNovaSolicitacaoForm(Model model) {
        model.addAttribute("solicitacao", new Solicitacao());
        // Buscar todas as áreas de atuação do banco
        List<AreaAtuacao> areas = areaAtuacaoRepository.findAll();
        // Adicionar a lista de áreas ao modelo com o nome "areas"
        model.addAttribute("areas", areas);
        return "cliente/nova_solicitacao";
    }

    @PostMapping("/cliente/solicitacao/nova")
    @PreAuthorize("hasRole(\'CLIENTE\')")
    public String processNovaSolicitacao(@ModelAttribute("solicitacao") Solicitacao solicitacao,
                                         BindingResult result,
                                         Authentication authentication,
                                         RedirectAttributes redirectAttributes,
                                         Model model) {
        // Se houver erro de validação, precisa adicionar as áreas novamente ao model
        // para que o select no formulário seja populado novamente.
        if (result.hasErrors()) {
            List<AreaAtuacao> areas = areaAtuacaoRepository.findAll();
            model.addAttribute("areas", areas);
            return "cliente/nova_solicitacao";
        }

        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            // O service agora precisa garantir que a 'area' (que vem associada pelo th:field)
            // seja persistida corretamente. A validação de área não nula deve ocorrer
            // antes ou durante a chamada do service.
            solicitacaoService.criarNovaSolicitacao(solicitacao, userDetails.getUsername());
            redirectAttributes.addFlashAttribute("sucesso", "Solicitação criada com sucesso!");
            return "redirect:/cliente/solicitacoes/historico";
        } catch (Exception e) {
            // Adicionar áreas ao modelo em caso de erro também
            List<AreaAtuacao> areas = areaAtuacaoRepository.findAll();
            model.addAttribute("areas", areas);
            model.addAttribute("erro", "Erro ao criar solicitação: " + e.getMessage());
            return "cliente/nova_solicitacao";
        }
    }

    @GetMapping("/cliente/solicitacoes/historico")
    @PreAuthorize("hasRole(\'CLIENTE\')")
    public String showHistoricoCliente(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<Solicitacao> solicitacoes = solicitacaoService.buscarSolicitacoesPorCliente(userDetails.getUsername());
        model.addAttribute("solicitacoes", solicitacoes);
        return "cliente/historico_solicitacoes";
    }

    @GetMapping("/advogado/solicitacoes/listar")
    @PreAuthorize("hasRole(\'ADVOGADO\')")
    public String showListarSolicitacoesAbertas(Model model) {
        List<Solicitacao> solicitacoes = solicitacaoService.buscarSolicitacoesAbertas();
        model.addAttribute("solicitacoes", solicitacoes);
        // ATENÇÃO: O template advogado/listar_solicitacoes.html também pode precisar
        // de ajustes se ele exibia 'categoria' ou 'tema'. Agora deve exibir 'solicitacao.area.nome'.
        return "advogado/listar_solicitacoes";
    }

    @PostMapping("/advogado/solicitacao/assumir/{id}")
    @PreAuthorize("hasRole(\'ADVOGADO\')")
    public String assumirSolicitacao(@PathVariable Long id,
                                     Authentication authentication,
                                     RedirectAttributes redirectAttributes) {
        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            solicitacaoService.advogadoAssumirSolicitacao(id, userDetails.getUsername());
            redirectAttributes.addFlashAttribute("sucesso", "Solicitação assumida com sucesso!");
            return "redirect:/advogado/solicitacoes/aceitas";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao assumir solicitação: " + e.getMessage());
            return "redirect:/advogado/solicitacoes/listar";
        }
    }

    @GetMapping("/advogado/solicitacoes/aceitas")
    @PreAuthorize("hasRole(\'ADVOGADO\')")
    public String showHistoricoAdvogado(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<Solicitacao> solicitacoes = solicitacaoService.buscarSolicitacoesPorAdvogado(userDetails.getUsername());
        model.addAttribute("solicitacoes", solicitacoes);
        // ATENÇÃO: O template advogado/historico_aceitas.html também pode precisar
        // de ajustes se ele exibia 'categoria' ou 'tema'. Agora deve exibir 'solicitacao.area.nome'.
        return "advogado/historico_aceitas";
    }
}

