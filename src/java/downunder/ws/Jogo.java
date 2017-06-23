/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downunder.ws;

import java.util.ArrayList;

/**
 *
 * @author Eduardo
 */
public class Jogo {
    int qtdPartidas;                        // Numero maximo de partidas
    ArrayList<Jogador> jogadores;           // Armazena o nome e id dos jogadores
    ArrayList<Partida> partidas;            // Array com todas as partidas
    ArrayList<Partida> partidasPreRegistro; // Array com as partidas do pre-registro
    
    public Jogo(int qtdPartidas) {
        this.qtdPartidas = qtdPartidas;
        this.jogadores = new ArrayList<>(qtdPartidas*2);
        this.partidas = new ArrayList<>(qtdPartidas);
        this.partidasPreRegistro = new ArrayList<>();
    }
    
    public int preRegistro(String nomeJogador1, int idJogador1, String nomeJogador2, int idJogador2) {
        Jogador j1 = new Jogador(nomeJogador1, idJogador1);
        Jogador j2 = new Jogador(nomeJogador2, idJogador2);
        
        jogadores.add(j1);
        jogadores.add(j2);

        // Cria uma partida "fantasma" para salvar o pre-registro
        Partida p = new Partida(getJogador(idJogador1));
        p.entraNaPartida(getJogador(idJogador2));
        partidasPreRegistro.add(p);
        
        return 0;
    }
    
    public int registraJogador(String nomeJogador) {
        // Verifica se o jogador ja foi pre registrado
        for (Partida p : partidasPreRegistro) {
            if (p.getJogador1().getNome().equals(nomeJogador)) { // Jogador foi pre-registrado e eh o jogador 1
                temPartida(p.getJogador1().getId());
                return p.getJogador1().getId();
            }
            else if (p.getJogador2().getNome().equals(nomeJogador)) { // Jogador foi pre-registrado e eh o jogador 2
                temPartida(p.getJogador2().getId());
                return p.getJogador2().getId();
            }
        }
        
        // Verifica se jogador ja existe
        for (Jogador j : jogadores) {
            if (j.getNome() == null) continue;
            if (j.getNome().equals(nomeJogador)) {
                return -1; // Jogador ja existe
            }
        }
        
        // Verifica se existe vaga para o jogador
        if (jogadores.size() < (qtdPartidas*2)) {
            int novoId = newId();
            jogadores.add(new Jogador(nomeJogador, novoId));
            temPartida(novoId);
            return novoId; // Existe vaga, retorna ID
        }
        
        return -2; // Nao ha vagas
    }
    
    public int encerraPartida(int idJogador) {
        for (Partida p : partidas) {
            if (p.jogadorEstaNaPartida(idJogador)) {
                p.encerraPartida(idJogador);
                removeJogador(idJogador);
                if (p.getQtdJogadores() == 0) {
                    partidas.remove(p);
                    System.out.println("Quantidade de partidas em progresso: " + partidas.size() + ".");
                }
                return 0;
            }
        }
        return -1;
    }
    
    public int temPartida(int idJogador) {
        if (getJogador(idJogador) == null) return -1;
        for (Partida p : partidas)  {
            if (p.jogadorEstaNaPartida(idJogador)) {
                if (p.partidaEmProgresso()) {
                    if (p.getJogador1().getId() == idJogador) {
                        return 1;  // Jogador inicia com esferas claras
                    }
                    else if (p.getJogador2().getId() == idJogador)
                        return 2;  // Jogador é o segundo a jogar, com esferas escuras
                }
                else {
                    return 0;
                }
            }
        }

        // Trata os casos em que o jogador foi pre-registrado
        for (Partida p : partidasPreRegistro) {
            if (p.getJogador1().getId() == idJogador) {             // Jogador foi pre-registrado e eh o jogador 1
                if (partidas.size() < qtdPartidas) {
                    partidas.add(new Partida(getJogador(idJogador)));   // Jogador inicia com esferas claras
                    return 0;
                }
            }
            else if (p.getJogador2().getId() == idJogador) {        // Jogador foi pre-registrado e eh o jogador 2
                for (Partida p1 : partidas) {
                    if (p1.getJogador1().getId() == p.getJogador1().getId()) {
                        p1.entraNaPartida(getJogador(idJogador));   // Jogador eh o segundo a jogar, com esferas escuras
                        partidasPreRegistro.remove(p);              // Os 2 jogadores entraram na partida, remove a partida "fantasma"
                        return 2;
                    }
                }
            }
        }
        
        // Tenta entrar em uma partida que tem apenas um jogador
        for (Partida p : partidas) {
            if (p.entraNaPartida(getJogador(idJogador))) return 2;
        }
        
        // Nao encontrou partida com vaga, cria uma nova
        if (partidas.size() < qtdPartidas) {
            partidas.add(new Partida(getJogador(idJogador))); // Cria uma nova partida e aguarda o oponente
            System.out.println("Quantidade de partidas em progresso: " + partidas.size() + ".");
            return 0;
        }
        return -1;
    }
    
    public int ehMinhaVez(int idJogador) {
        Partida p = null;
        try {
            p = getPartidaFromId(idJogador);
            if (p == null) return -1;
        } catch (Exception e) {
        }
        return (p.ehMinhaVez(idJogador));
    }

    public String obtemTabuleiro(int idJogador) {
        Partida p = null;
        try {
            p = getPartidaFromId(idJogador);
            if (p == null) return "";
        } catch (Exception e) {
        }
        return p.obtemTabuleiro();
    }
        
    public int soltaEsfera(int idJogador, int orificio) {
        Partida p = null;
        try {
            p = getPartidaFromId(idJogador);
            if (p == null) return -1;
        } catch (Exception e) { }
        return p.soltaEsfera(idJogador, orificio);
    }
   
    public String obtemOponente(int idJogador) {
        Partida p = null;
        try {
            p = getPartidaFromId(idJogador);
            if (p == null) return "";
        } catch (Exception e) { }

        int idOponente = p.getIdOponente(idJogador);
        if (idOponente == -1) return "";
        return getJogador(idOponente).getNome();
    }
    
    private Partida getPartidaFromId(int idJogador) {
        for (Partida p : partidas) {
            if (p.jogadorEstaNaPartida(idJogador)) return p;
        }
        return null;
    }
    
    private Jogador getJogador(int idJogador) {
        for (Jogador j : jogadores) {
            if (j.getId() == idJogador) {
                return j;
            }
        }
        return null;
    }
    
    private int newId() {
        int i = 0;
        while (getJogador(i) == null) {
            i++;
        }
        return i;
    }
    
    private void removeJogador(int idJogador) {
        for (Jogador j : jogadores) {
            if (j.getId() == idJogador) {
                jogadores.remove(j);
                return;
            }
        }
    }
}


