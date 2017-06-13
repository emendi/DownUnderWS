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
public class Jogador {
    private String nome;
    private int id;
    
    public Jogador(String nome, int id) {
        this.nome = nome;
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public int getId() {
        return id;
    }
}
