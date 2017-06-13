/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downunder.ws;

import static java.lang.Thread.sleep;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Eduardo
 */
public class DownUnder extends UnicastRemoteObject implements DownUnderInterface {
    Jogo jogo;
    
    public DownUnder() throws RemoteException, InterruptedException {
        jogo = new Jogo(50);
    }
    
    public synchronized int preRegistro(String nomeJogador1, int idJogador1, String nomeJogador2, int idJogador2) throws RemoteException {
        return jogo.preRegistro(nomeJogador1, idJogador1, nomeJogador2, idJogador2);
    }
    
    public synchronized int registraJogador(String nomeJogador) throws RemoteException {
        return jogo.registraJogador(nomeJogador);
    }
    
    public synchronized int encerraPartida(int idJogador) throws RemoteException {
        return jogo.encerraPartida(idJogador);
    }
    
    public synchronized int temPartida(int idJogador) throws RemoteException {
        return jogo.temPartida(idJogador);
    }
    
    public synchronized int ehMinhaVez(int idJogador) throws RemoteException {
        return jogo.ehMinhaVez(idJogador);
    }
    
    public synchronized String obtemTabuleiro(int idJogador) throws RemoteException {
        return jogo.obtemTabuleiro(idJogador);
    }
    
    public synchronized int soltaEsfera(int idJogador, int orificio) throws RemoteException {
        return jogo.soltaEsfera(idJogador, orificio);
    }
    
    public synchronized String obtemOponente(int idJogador) throws RemoteException {
        return jogo.obtemOponente(idJogador);
    }
}
