package jogo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import sudoku.*;

/**
 *
 * @author Fernando
 */
public class Principal {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long ini, fim;
        int meu_jogo[][] = new int[9][9];
        try {
            File nomes = new File(new File("build/classes/sudoku/recursos/nomes.txt").getCanonicalPath());
            FileReader nomes_dado = new FileReader(nomes);
            BufferedReader ler_nome = new BufferedReader(nomes_dado);
            String nome = ler_nome.readLine();
            while (nome != null) {
                File dados = new File(new File("build/classes/sudoku/recursos/tabuleiros/" + nome).getCanonicalPath());
                FileReader fr = new FileReader(dados);
                BufferedReader leitor = new BufferedReader(fr);

                for (int i = 0; i < 9; i++) {
                    String linha = leitor.readLine();
                    linha = linha.replaceAll(" ", "");
                    for (int j = 0; j < 9; j++) {
                        meu_jogo[i][j] = Integer.valueOf(String.valueOf(linha.charAt(j)));
                    }
                }

                Sudoku array[] = new Sudoku[3];
                array[0] = new Sudoku(meu_jogo, 9);
                array[1] = new SudokuAC3(meu_jogo, 9);
                array[2] = new SudokuAC3_VRM(meu_jogo, 9);
                
                for(Sudoku sdk: array) {
                        
                    ini = System.currentTimeMillis();
                    sdk.resolver();
                    fim = System.currentTimeMillis();
                    System.out.println("");
                    sdk.imprime();
                    System.out.print("Arquivo: " + nome);
                    System.out.print(" Tempo Gasto: " + (fim - ini));
                    System.out.print(" Chamadas: " + sdk.getChamadas());
                    System.out.println("");
                    
                }
                nome = ler_nome.readLine();
            }
        } catch (IOException e) {
            System.out.println("Problema ao ler um dos arquivos! :/");
        }

    }
}
