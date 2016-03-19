package sudoku;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Fernando
 */
public class SudokuAC3_VRM extends SudokuAC3 {

    public SudokuAC3_VRM(int[][] matriz, int n) {
        super(matriz, n);
    }

    @Override
    protected boolean solucionaStart(int matriz_s[][], Nodo pontos[][]) {
        Nodo pontos_copia[][] = new Nodo[this.n][this.n];
        int matriz_copia[][] = new int[this.n][this.n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(matriz_s[i], 0, matriz_copia[i], 0, n);
            for (int j = 0; j < pontos.length; j++) {
                pontos_copia[i][j] = new Nodo(pontos[i][j]);
            }
        }
        if (AC3(matriz_copia, pontos_copia)) {
            if (soluciona(matriz_copia, pontos_copia)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean AC3(int matriz[][], Nodo pontos[][]) {
        Queue<Nodo> lista = new LinkedList<>();
        for (int linha = 0; linha < this.n; linha++) {
            for (int coluna = 0; coluna < this.n; coluna++) {
                if (matriz[linha][coluna] != 0) {
                    int iniC = (int) (coluna - coluna % this.raiz);
                    int iniL = (int) (linha - linha % this.raiz);
                    for (int i = 0; i < this.n; i++) {
                        if (matriz[i][coluna] == 0) {
                            if (!lista.contains(pontos[i][coluna])) {
                                lista.add(pontos[i][coluna]);
                            }
                        }
                        if (matriz[linha][i] == 0) {
                            if (!lista.contains(pontos[linha][i])) {
                                lista.add(pontos[linha][i]);
                            }
                        }
                        if (i < this.raiz) {
                            for (int j = 0; j < this.raiz; j++) {
                                if (matriz[i + iniL][j + iniC] == 0) {
                                    if (!lista.contains(pontos[i + iniL][j + iniC])) {
                                        lista.add(pontos[i + iniL][j + iniC]);
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
        while (!lista.isEmpty()) {
            Nodo aux = lista.poll();
            int qtd = aux.qtd;
            int iniC_aux = (int) (aux.getColuna() - aux.getColuna() % this.raiz);
            int iniL_aux = (int) (aux.getLinha() - aux.getLinha() % this.raiz);
            for (int i = 0; i < this.n; i++) {
                if (matriz[i][aux.getColuna()] != 0) {
                    aux.setValorPosicao(matriz[i][aux.getColuna()], -1);
                }
                if (matriz[aux.getLinha()][i] != 0) {
                    aux.setValorPosicao(matriz[aux.getLinha()][i], -1);
                }
                if (i < this.raiz) {
                    for (int j = 0; j < this.raiz; j++) {
                        if (matriz[i + iniL_aux][j + iniC_aux] != 0) {
                            aux.setValorPosicao(matriz[i + iniL_aux][j + iniC_aux], -1);
                        }
                    }
                }
            }
            pontos[aux.getLinha()][aux.getColuna()].setValor(aux.valor);
            if (aux.qtd != qtd) {
                if (aux.qtd == 1) {
                    int dominio = 0;
                    for (int i = 1; i < aux.valor.length; i++) {
                        if (aux.valor[i] != -1) {
                            dominio = i;
                        }
                    }
                    matriz[aux.getLinha()][aux.getColuna()] = dominio;
                }
                for (int i = 0; i < this.n; i++) {
                    if (pontos[i][aux.getColuna()].qtd > 1) {
                        if (!lista.contains(pontos[i][aux.getColuna()])) {
                            lista.add(pontos[i][aux.getColuna()]);
                        }
                    }
                    if (pontos[aux.getLinha()][i].qtd > 1) {
                        if (!lista.contains(pontos[aux.getLinha()][i])) {
                            lista.add(pontos[aux.getLinha()][i]);
                        }
                    }
                    if (i < this.raiz) {
                        for (int j = 0; j < this.raiz; j++) {
                            if (pontos[i + iniL_aux][j + iniC_aux].qtd > 1) {
                                if (!lista.contains(pontos[i + iniL_aux][j + iniC_aux])) {
                                    lista.add(pontos[i + iniL_aux][j + iniC_aux]);
                                }
                            }
                        }
                    }
                }
            }
            if (aux.qtd == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected boolean soluciona(int matriz_s[][], Nodo pontos[][]) {
        conta++;
        int linhas[] = new int[10];
        int colunas[] = new int[10];
        int valores_referencia[] = new int[3];
        boolean achou = true;
        for (int i = 0; i < n + 1; i++) {
            linhas[i] = colunas[i] = -1;
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                linhas[pontos[i][j].qtd] = pontos[i][j].getLinha();
                colunas[pontos[i][j].qtd] = pontos[i][j].getColuna();
            }
        }
        for (int i = 2; i < n + 1; i++) {
            if (linhas[i] != -1) {
                achou = false;
                valores_referencia[1] = linhas[i];
                valores_referencia[2] = colunas[i];
                break;
            }
        }
        if (achou) {
            for (int i = 0; i < n; i++) {
                System.arraycopy(matriz_s[i], 0, this.matriz[i], 0, n);
            }
            return true;
        }
        for (int valor = 1; valor <= this.n; valor++) {
            Nodo pontos_copia[][] = new Nodo[this.n][this.n];
            int matriz_copia[][] = new int[this.n][this.n];
            for (int i = 0; i < n; i++) {
                System.arraycopy(matriz_s[i], 0, matriz_copia[i], 0, n);
                for (int j = 0; j < pontos.length; j++) {
                    pontos_copia[i][j] = new Nodo(pontos[i][j]);
                }
            }
            if (pontos_copia[valores_referencia[1]][valores_referencia[2]].valor[valor] != -1) {
                matriz_copia[valores_referencia[1]][valores_referencia[2]] = valor;
                pontos_copia[valores_referencia[1]][valores_referencia[2]].setPosicao(valor);
                if (AC3(matriz_copia, pontos_copia)) {
                    if (soluciona(matriz_copia, pontos_copia)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
