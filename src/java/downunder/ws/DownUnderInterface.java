package downunder.ws;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Eduardo
 */
public interface DownUnderInterface extends Remote {
 
    public int registraJogador(String nomeJogador) throws RemoteException;
    
    public int encerraPartida(int idJogador) throws RemoteException;
    
    public int temPartida(int idJogador) throws RemoteException;
    
    public int ehMinhaVez(int idJogador) throws RemoteException;
    
    public String obtemTabuleiro(int idJogador) throws RemoteException;
    
    public int soltaEsfera(int idJogador, int orificio) throws RemoteException;
    
    public String obtemOponente(int idJogador) throws RemoteException;
}
