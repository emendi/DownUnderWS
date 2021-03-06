package downunder.client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author roland
 */
public class ClientWithValidation {

    private static DownUnderWS_Service service;
    private static DownUnderWS port;
    
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        String fileName = "DownUnder-4000";
        
        if (args.length > 0) fileName = args[0];
        
        executaTeste("C:\\Users\\eduardo\\Documents\\NetBeansProjects\\DownUnderWS\\test\\" + fileName);
    }

    private static void executaTeste(String rad) throws IOException {
        service = new DownUnderWS_Service();
        port = service.getDownUnderWSPort();
        
        String inFile = rad+".in";
        FileInputStream is = new FileInputStream(new File(inFile));
        System.setIn(is);

        String outFile = rad+".out";
        FileWriter outWriter = new FileWriter(outFile);
        try (PrintWriter out = new PrintWriter(outWriter)) {
            Scanner leitura = new Scanner(System.in);
            int numOp = leitura.nextInt();
            for (int i=0;i<numOp;++i) {
                System.out.print("\r"+rad+": "+(i+1)+"/"+numOp);
                int op = leitura.nextInt();
                String parametros = leitura.next();
                String param[] = parametros.split(":",-1);
                switch(op) {
                    case 0:
                        if (param.length!=4)
                            erro(inFile,i+1);
                        else
                            out.println(preRegistro(param[0],Integer.parseInt(param[1]),param[2],Integer.parseInt(param[3])));
                        break;
                    case 1:
                        if (param.length!=1)
                            erro(inFile,i+1);
                        else
                            out.println(registraJogador(param[0]));
                        break;
                    case 2:
                        if (param.length!=1)
                            erro(inFile,i+1);
                        else
                            out.println(encerraPartida(Integer.parseInt(param[0])));
                        break;
                    case 3:
                        if (param.length!=1)
                            erro(inFile,i+1);
                        else
                            out.println(temPartida(Integer.parseInt(param[0])));
                        break;
                    case 4:
                        if (param.length!=1)
                            erro(inFile,i+1);
                        else
                            out.println(ehMinhaVez(Integer.parseInt(param[0])));
                        break;
                    case 5:
                        if (param.length!=1)
                            erro(inFile,i+1);
                        else
                            out.println(obtemTabuleiro(Integer.parseInt(param[0])));
                        break;
                    case 6:
                        if (param.length!=2)
                            erro(inFile,i+1);
                        else
                            out.println(soltaEsfera(Integer.parseInt(param[0]),Integer.parseInt(param[1])));
                        break;
                    case 7:
                        if (param.length!=1)
                            erro(inFile,i+1);
                        else
                            out.println(obtemOponente(Integer.parseInt(param[0])));
                        break;
                    default:
                        erro(inFile,i+1);
                }
            }
            System.out.println("... terminado!");
            out.close();
            leitura.close();
            executaValidacao(rad);
        }
    }
    
    private static void executaValidacao(String rad) throws IOException {
        int count_matches = 0;
        int count_lines = 0;
        
        try {
            File expectedFile = new File(rad + ".res");
            File outputFile = new File(rad + ".out");

            FileInputStream is_expectedFile = new FileInputStream(expectedFile);
            FileInputStream is_outputFile = new FileInputStream(outputFile);

            Scanner s_expectedFile = new Scanner(is_expectedFile);
            Scanner s_outputFile = new Scanner(is_outputFile);

            while (s_expectedFile.hasNextLine()) {
                if (s_expectedFile.nextLine().equals(s_outputFile.nextLine())) {
                    count_matches++;
                }
                count_lines++;
            }
            
            s_expectedFile.close();
            s_outputFile.close();
        } catch (Exception e) {
            System.out.println("Ops... Erro ao tentar efetuar a validacao do arquivo de saida. Verifique o arquivo manualmente.");
            return;
        }
        System.out.println("Validacao completa. Linhas com resultado igual ao esperado: " + count_matches + "/" + count_lines + " (" + (((float)count_matches/count_lines)*100) + "%).");
    }
    
    private static void erro(String arq,int operacao) {
        System.err.println("Entrada invalida: erro na operacao "+operacao+" do arquivo "+arq);
        System.exit(1);
    }

    private static int preRegistro(java.lang.String nome1, int id1, java.lang.String nome2, int id2) {
        return port.preRegistro(nome1, id1, nome2, id2);
    }

    private static int registraJogador(java.lang.String nome) {
        return port.registraJogador(nome);
    }

    private static int encerraPartida(int id) {
        return port.encerraPartida(id);
    }
    
    private static int temPartida(int id) {
        return port.temPartida(id);
    }

    private static int ehMinhaVez(int id) {
        return port.ehMinhaVez(id);
    }

    private static String obtemTabuleiro(int id) {
        return port.obtemTabuleiro(id);
    }

    private static int soltaEsfera(int id, int orificio) {
        return port.soltaEsfera(id, orificio);
    }

    private static String obtemOponente(int id) {
        return port.obtemOponente(id);
    }
    
}
