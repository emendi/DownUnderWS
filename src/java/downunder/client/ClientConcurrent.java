/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downunder.client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Eduardo
 */
public class ClientConcurrent implements Runnable{
    @Override
    public void run() {
        ClientWithValidation client = new ClientWithValidation();
    }
 
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 2; i++) executor.execute(new ClientConcurrent());
    }
}
