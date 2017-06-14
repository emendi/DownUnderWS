/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downunder.ws;

import java.rmi.RemoteException;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Eduardo
 */
@WebService(serviceName = "DownUnderWS")
public class DownUnderWS {

    DownUnder du;
    
    public DownUnderWS() {
        try {
            du = new DownUnder();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Operação de Web service
     */
    @WebMethod(operationName = "preRegistro")
    public int preRegistro(@WebParam(name = "nomeJogador1") String nomeJogador1, @WebParam(name = "idJogador1") int idJogador1, @WebParam(name = "nomeJogador2") String nomeJogador2, @WebParam(name = "idJogador2") int idJogador2) throws RemoteException {
        //TODO write your implementation code here:
        return du.preRegistro(nomeJogador1, idJogador1, nomeJogador2, idJogador2);
    }

    /**
     * Operação de Web service
     */
    @WebMethod(operationName = "registraJogador")
    public int registraJogador(@WebParam(name = "nomeJogador") String nomeJogador) throws RemoteException {
        //TODO write your implementation code here:
        return du.registraJogador(nomeJogador);
    }

    /**
     * Operação de Web service
     */
    @WebMethod(operationName = "encerraPartida")
    public int encerraPartida(@WebParam(name = "idJogador") int idJogador) throws RemoteException {
        //TODO write your implementation code here:
        return du.encerraPartida(idJogador);
    }

    /**
     * Operação de Web service
     */
    @WebMethod(operationName = "temPartida")
    public int temPartida(@WebParam(name = "idJogador") int idJogador) throws RemoteException {
        //TODO write your implementation code here:
        return du.temPartida(idJogador);
    }

    /**
     * Operação de Web service
     */
    @WebMethod(operationName = "ehMinhaVez")
    public int ehMinhaVez(@WebParam(name = "idJogador") int idJogador) throws RemoteException {
        //TODO write your implementation code here:
        return du.ehMinhaVez(idJogador);
    }

    /**
     * Operação de Web service
     */
    @WebMethod(operationName = "obtemTabuleiro")
    public String obtemTabuleiro(@WebParam(name = "idJogador") int idJogador) throws RemoteException {
        //TODO write your implementation code here:
        return du.obtemTabuleiro(idJogador);
    }

    /**
     * Operação de Web service
     */
    @WebMethod(operationName = "soltaEsfera")
    public int soltaEsfera(@WebParam(name = "idJogador") int idJogador, @WebParam(name = "orificio") int orificio) throws RemoteException {
        //TODO write your implementation code here:
        return du.soltaEsfera(idJogador, orificio);
    }

    /**
     * Operação de Web service
     */
    @WebMethod(operationName = "obtemOponente")
    public String obtemOponente(@WebParam(name = "idJogador") int idJogador) throws RemoteException {
        //TODO write your implementation code here:
        return du.obtemOponente(idJogador);
    }
    
    
}
