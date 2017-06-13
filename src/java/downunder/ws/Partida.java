/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downunder.ws;

/**
 *
 * @author Eduardo
 */
public class Partida {
    Jogador jogador1;                       // ID do jogador 1
    Jogador jogador2;                       // ID do jogador 2
    int vezDoJogador;                       // ID do jogador que deve jogar no turno atual
    boolean primeiraEsferaJogadorAtual;     // Flag para indicar se o jogador esta jogando a primeira esfera de sua vez
    int qtdJogadores;                       // Quantidades de jogadores na partida;
    private enum StatusOrificio { Vazio, EsferaClara, EsferaEscura; }
    StatusOrificio[][] torre;               // Torre/Tabuleiro (5 x 8)
    int[] ultimasJogadas;
    private enum StatusPartida { APENASUMJOGADOR, ERRO, EMPROGRESSO, 
        JOGADOR1VENCEDOR, JOGADOR2VENCEDOR, EMPATE, JOGADOR1VENCEUWO,
        JOGADOR2VENCEUWO; }
    StatusPartida status;
    int qtdSeqJogador1;  // Quantidade de sequencias do jogador 1
    int qtdSeqJogador2;  // Quantidade de sequencias do jogador 2
    
    public Partida(Jogador jogador1) {
        this.jogador1 = jogador1;
        this.vezDoJogador = jogador1.getId();
        primeiraEsferaJogadorAtual = true;
        this.qtdJogadores = 1;
        this.torre = new StatusOrificio[5][8];
        this.ultimasJogadas = new int[5];
        this.status = StatusPartida.APENASUMJOGADOR;
        
        for (int i=0; i<5; i++) {
            for (int j=0; j<8; j++) {
                torre[i][j] = StatusOrificio.Vazio;
            }
            ultimasJogadas[i] = 0;
        }
    }
    
    public boolean entraNaPartida(Jogador j) {
        if (qtdJogadores == 1) {
            jogador2 = j;
            status = StatusPartida.EMPROGRESSO;
            qtdJogadores++;
            return true;
        }
        return false;
    }
    
    public boolean jogadorEstaNaPartida(int idJogador) {
        if (jogador1 != null)
            if (jogador1.getId() == idJogador) return true;
        if (jogador2 != null)
            if (jogador2.getId() == idJogador) return true;
        return false;
    }
    
    public int ehMinhaVez(int idJogador) {
        if (status == StatusPartida.APENASUMJOGADOR) return -2; // Erro: ainda nao ha 2 jogadores na partida
        if (status == StatusPartida.EMPROGRESSO) {
            if (vezDoJogador == idJogador) return 1;  // Sim, vez do jogador
            return 0;                                 // Nao, nao eh a vez do jogador
        }
        if (status == StatusPartida.EMPATE) return 4; // Empate
        if (status == StatusPartida.JOGADOR1VENCEDOR) {
            if (jogador1.getId() == idJogador) return 2;      // Vencedor
            if (jogador2.getId() == idJogador) return 3;      // Perdedor
        }
        if (status == StatusPartida.JOGADOR2VENCEDOR) {
            if (jogador1.getId() == idJogador) return 3;      // Perdedor
            if (jogador2.getId() == idJogador) return 2;      // Vencedor
        }
        if (status == StatusPartida.JOGADOR1VENCEUWO) {
            if (jogador1.getId() == idJogador) return 5;      // Vencedor por WO
            if (jogador2.getId() == idJogador) return 6;      // Perdedor por WO
        }
        if (status == StatusPartida.JOGADOR2VENCEUWO) {
            if (jogador1.getId() == idJogador) return 6;      // Perdedor por WO
            if (jogador2.getId() == idJogador) return 5;      // Vencedor por WO
        }
        
        return -1;                                    // Erro
    }
    
    public int soltaEsfera(int idJogador, int orificio) {
        /* 1 (tudo certo)
         * 0 (movimento inválido, por exemplo, em um orifício que já tem 8 esferas)
         * ­1 (erro)
         * ­2 (partida não iniciada:ainda não há dois jogadores registrados na partida)
         * ­3 (não é a vez do jogador). */

        if ((!jogadorEstaNaPartida(idJogador)) || (orificio < 0) || (orificio > 4)) return -1; // Erro: jogador nao esta na partida E/OU orificio invalido
        if (status == StatusPartida.APENASUMJOGADOR) return -2;                                // Apenas um jogador na partida
        if (vezDoJogador != idJogador) return -3;                                              // Nao eh a vez do jogador
        if (torre[orificio][7] != StatusOrificio.Vazio) return 0;                              // Orificio ja tem 8 esferas
        
        // Determina a posicao da esfera no orificio, para que seja colocada logo acima da ultima esfera ou no fundo da torre
        int posicao = 0;
        for (int i=0; i<8; i++) {
            if (torre[orificio][i] == StatusOrificio.Vazio) {
                posicao = i;
                break;
            }
        }
        
        // Determina a cor da esfera do jogador
        StatusOrificio esfera;
        if (vezDoJogador == jogador1.getId())
            esfera = StatusOrificio.EsferaClara;
        else
            esfera = StatusOrificio.EsferaEscura;
        torre[orificio][posicao] = esfera;  // Coloca a esfera no orificio escolhido
        
        for (int i=0; i<5; i++) {
            if (ultimasJogadas[i] == 1) ultimasJogadas[i] = 2;
            else if (ultimasJogadas[i] == 2) ultimasJogadas[i] = 0;
        }
        ultimasJogadas[orificio] = 1;
        
        if (primeiraEsferaJogadorAtual) // Se o jogador esta jogando sua primeira esfera, nao troca o turno
            primeiraEsferaJogadorAtual = false;
        else {                          // Esta e a segunda esfera do jogador, passa a vez para o outro jogador
            primeiraEsferaJogadorAtual = true;
            proximoTurno();
        }

        return 1;
    }
    
    private void proximoTurno() {
        if (vezDoJogador == jogador1.getId())
            vezDoJogador = jogador2.getId();
        else
            vezDoJogador = jogador1.getId();
        
        for (int i=0; i<5; i++) {
            if (torre[i][7] == StatusOrificio.Vazio)
                return; // Jogo ainda nao acabou: sai do metodo
        }
        
        // Jogo acabou: verifica quem eh o vencedor
        verificaVencedor();
    }
    
    private void verificaVencedor() {
        qtdSeqJogador1 = 0;  // Quantidade de sequencias do jogador 1
        qtdSeqJogador2 = 0;  // Quantidade de sequencias do jogador 2
        
        int countClaras = 0;     /* Contadores para auxiliar na */
        int countEscuras = 0;    /*  descoberta das sequencias  */
              
        
        // Verifica as esferas verticalmente
        for (int i=0; i<5; i++) {
            for (int j=0; j<8; j++) {
                if (torre[i][j] == StatusOrificio.EsferaClara) {
                    countClaras++; 
                    qtdSeqJogador2 = qtdSeqJogador2 + calculaQtdSequencias(countEscuras);
                    countEscuras = 0;
                }
                else if (torre[i][j] == StatusOrificio.EsferaEscura) {
                    countEscuras++;
                    qtdSeqJogador1 = qtdSeqJogador1 + calculaQtdSequencias(countClaras);
                    countClaras = 0;
                }
            }
            qtdSeqJogador1 = qtdSeqJogador1 + calculaQtdSequencias(countClaras);
            qtdSeqJogador2 = qtdSeqJogador2 + calculaQtdSequencias(countEscuras);
            countClaras = countEscuras = 0;
        }
        
        // Verifica as esferas horizontalmente
        for (int i=0; i<8; i++) {
            for (int j=0; j<5; j++) {
                if (torre[j][i] == StatusOrificio.EsferaClara) {
                    countClaras++;
                    qtdSeqJogador2 = qtdSeqJogador2 + calculaQtdSequencias(countEscuras);
                    countEscuras = 0;
                }
                else if (torre[j][i] == StatusOrificio.EsferaEscura) {
                    countEscuras++;
                    qtdSeqJogador1 = qtdSeqJogador1 + calculaQtdSequencias(countClaras);
                    countClaras = 0;
                }
            }
            qtdSeqJogador1 = qtdSeqJogador1 + calculaQtdSequencias(countClaras);
            qtdSeqJogador2 = qtdSeqJogador2 + calculaQtdSequencias(countEscuras);
            countClaras = countEscuras = 0;
        }
        
        // Verifica as esferas nas diagonais
        // Posicoes que serao verificadas (primeiro numero = numero de esferas na diagonal)
        int[] posicoes = { 4, 0,3,1,2,2,1,3,0,     // Ex: 4 esferas, nas posicoes 0,3;1,2;2,1;3,0
                           5, 0,4,1,3,2,2,3,1,4,0,
                           5, 0,5,1,4,2,3,3,2,4,1,
                           5, 0,6,1,5,2,4,3,3,4,2,
                           5, 0,7,1,6,2,5,3,4,4,3,
                           4, 1,7,2,6,3,5,4,4,
                           
                           4, 1,0,2,1,3,2,4,3,
                           5, 0,0,1,1,2,2,3,3,4,4,
                           5, 0,1,1,2,2,3,3,4,4,5,
                           5, 0,2,1,3,2,4,3,5,4,6,
                           5, 0,3,1,4,2,5,3,6,4,7,
                           4, 0,4,1,5,2,6,3,7      };
        
        int i = 0;
        int j;
        int numEsferas = 0;
        
        while (i < posicoes.length) {
            numEsferas = (posicoes[i] * 2);
            i++;
            for (j=i; j<numEsferas+i; j=j+2) {
                if (torre[posicoes[j]][posicoes[j+1]] == StatusOrificio.EsferaClara) {
                    countClaras++;
                    qtdSeqJogador2 = qtdSeqJogador2 + calculaQtdSequencias(countEscuras);
                    countEscuras = 0;
                }
                else if (torre[posicoes[j]][posicoes[j+1]] == StatusOrificio.EsferaEscura) {
                    countEscuras++;
                    qtdSeqJogador1 = qtdSeqJogador1 + calculaQtdSequencias(countClaras);
                    countClaras = 0;
                }
            }
            qtdSeqJogador1 = qtdSeqJogador1 + calculaQtdSequencias(countClaras);
            qtdSeqJogador2 = qtdSeqJogador2 + calculaQtdSequencias(countEscuras);
            countClaras = countEscuras = 0;
            i = j;
        }
        
        if (qtdSeqJogador1 > qtdSeqJogador2) status = StatusPartida.JOGADOR1VENCEDOR;
        else if (qtdSeqJogador2 > qtdSeqJogador1) status = StatusPartida.JOGADOR2VENCEDOR;
        else status = StatusPartida.EMPATE;
    }
    
    // Recebe a quantidade de esferas consecutivas esncontradas e retorna a quantidade de sequencias que elas representam para o jogador
    // Exemplo: 4 esferas = 1 sequencia
    private int calculaQtdSequencias(int qtdEsferas) {
        if (qtdEsferas < 4) return 0;
        return qtdEsferas - 3;
    }
    
    public String obtemTabuleiro() {
        String tabuleiro = "";
        if (status == StatusPartida.EMPROGRESSO) {                     // Partida ainda esta em progresso
            for (int i=0; i<5; i++) {
                if (torre[i][7] == StatusOrificio.Vazio)
                    tabuleiro = tabuleiro + "-";
                else if (torre[i][7] == StatusOrificio.EsferaClara)
                    tabuleiro = tabuleiro + "C";
                else if (torre[i][7] == StatusOrificio.EsferaEscura)
                    tabuleiro = tabuleiro + "E";
            }
            for (int i=0; i<5; i++) {
                if (ultimasJogadas[i] == 0) tabuleiro = tabuleiro + ".";
                else tabuleiro = tabuleiro + "^";
            }
        }
        else if (status != StatusPartida.APENASUMJOGADOR) {           // Partida ja terminou
            for (int i=7; i>=0; i--) {
                for (int j=0; j<5; j++) {
                    if (torre[j][i] == StatusOrificio.Vazio)
                        tabuleiro = tabuleiro + "-";
                    else if (torre[j][i] == StatusOrificio.EsferaClara)
                        tabuleiro = tabuleiro + "C";
                    else if (torre[j][i] == StatusOrificio.EsferaEscura)
                        tabuleiro = tabuleiro + "E";
                }
            }
            tabuleiro = tabuleiro + "," + qtdSeqJogador1 + "," + qtdSeqJogador2;
        }
        return tabuleiro;
    }
    
    public boolean partidaEmProgresso() {
        if (status == StatusPartida.EMPROGRESSO)
            return true;
        return false;
    }
    
    public Jogador getJogador1() {
        return jogador1;
    }
    
    public Jogador getJogador2() {
        return jogador2;
    }
    
    public int getIdOponente(int idJogador) {
        if (status == StatusPartida.APENASUMJOGADOR) return -1;
        else if (idJogador == jogador1.getId()) return jogador2.getId();
        else if (idJogador == jogador2.getId()) return jogador1.getId();
        return -1;
    }
    
    public int getQtdJogadores() {
        return qtdJogadores;
    }
    
    public int encerraPartida(int idJogador) {
        if (!jogadorEstaNaPartida(idJogador)) return -1;
        if (status == StatusPartida.EMPROGRESSO) {
            if (idJogador == jogador1.getId()) {
                status = StatusPartida.JOGADOR2VENCEUWO;
            }
            else if (idJogador == jogador2.getId()) {
                status = StatusPartida.JOGADOR1VENCEUWO;
            }
        }
        if (jogador1 != null)
            if (idJogador == jogador1.getId())
                jogador1 = null;
        if (jogador2 != null)
            if (idJogador == jogador2.getId())
                jogador2 = null;
        qtdJogadores--;
        return 0;
    }
}
