package sudoku;

/**
 *
 * @author Fernando
 */
public class Sudoku {

    protected final int matriz[][];
    private final int tabuleiro[][];
    protected final int raiz;
    protected final int n;
    protected int conta = 0;

    /**
     * Contrutor da Classe Sudoku, recebe o tabuleiro, e as dimensões
     * @param matriz - Tabuleiro
     * @param n - Valor da dimensão
     */
    public Sudoku(int matriz[][], int n) {
        this.matriz = new int[n][n];
        this.tabuleiro = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(matriz[i], 0, this.matriz[i], 0, n);
            System.arraycopy(matriz[i], 0, this.tabuleiro[i], 0, n);
        }
        this.n = n;
        this.raiz = (int) Math.sqrt(n);
    }

    /**
     * Este método tenta resolver o sudoku inicializado
     * @return - true o houver solução, caso contrário false
     */
    public boolean resolver() {
       return soluciona(this.matriz);
    }

    /**
     * Este método tenta resolver o sudoku
     * @param matriz - Tabuleiro a ser resolvido
     * @return - boolean se o valor for true o sudoku tem solução, caso contrário não há solução
     */
    protected boolean soluciona(int matriz[][]) {
        conta++;
        int valores_referencia[] = verificaZero(matriz);
        if (!(valores_referencia[0] == 1)) {
            return true;
        }
        for (int valor = 1; valor <= this.n; valor++) {
            if (!restricoes(matriz, valores_referencia[1], valores_referencia[2], valor)) {
                matriz[valores_referencia[1]][valores_referencia[2]] = valor;
                if (soluciona(matriz)) {
                    return true;
                }
                matriz[valores_referencia[1]][valores_referencia[2]] = 0;
            }
        }
        return false;
    }

    /**
     * Este método verifica se o valor passado por parâmetro está na mesma linha
     * @param matriz - Esta matriz contém o tabuleiro a ser analisado
     * @param linha - Linha do valor a ser analisado
     * @param valor - Valor a ser analisado
     * @return boolean - Se true o valor está na coluna
     */
    protected boolean verificarLinha(int matriz[][], int linha, int valor) {
        for (int i = 0; i < this.n; i++) {
            if (matriz[linha][i] == valor) {
                return true;
            }
        }
        return false;
    }

    /**
     * Este método verifica se o valor passado por parâmetro está na mesma coluna
     * @param matriz - Esta matriz contém o tabuleiro a ser analisado
     * @param coluna - Coluna do valor a ser analisado
     * @param valor - Valor a ser verificado
     * @return boolean - Se retorna true o valor está na coluna
     */
    protected boolean verificarColuna(int matriz[][], int coluna, int valor) {
        for (int i = 0; i < this.n; i++) {
            if (matriz[i][coluna] == valor) {
                return true;
            }
        }
        return false;
    }

    /**
     * Este método verifica se o valor passado por parâmetro está no mesmo bloco
     *
     * @param matriz - Esta matriz contém o tabuleiro a ser analisado
     * @param linha - Linha do valor a ser analisada
     * @param coluna - Coluna do valor a ser analisado
     * @param valor - Valor a ser verificado
     * @return boolean - Se retorna true o valor está no bloco
     */
    protected boolean verificarBloco(int matriz[][], int linha, int coluna, int valor) {
        int iniC = (int) (coluna - coluna % this.raiz);
        int iniL = (int) (linha - linha % this.raiz);
        for (int i = 0; i < this.raiz; i++) {
            for (int j = 0; j < this.raiz; j++) {
                if (matriz[i + iniL][j + iniC] == valor) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Este método verifica se o valor passado por parâmetro já está na linha,
     * coluna ou bloco
     *
     * @param matriz - Está matriz contém o tabuleiro que vai ser analisado
     * @param linha - Linha do valor a ser analisado
     * @param coluna - Coluna do valor a ser analisado
     * @param valor - Valor a ser verificado
     * @return boolean - Se retornar true o valor está na linha, coluna ou bloco
     */
    protected boolean restricoes(int matriz[][], int linha, int coluna, int valor) {
        return verificarLinha(matriz, linha, valor)
                || verificarColuna(matriz, coluna, valor)
                || verificarBloco(matriz, linha, coluna, valor);
    }

    /**
     * Este método verifica se ainda existem espaços para serem preenchido,
     * sendo estes "zeros"
     *
     * @param matriz - Tabuleiro a ser analisado
     * @return int[3] - array contendo em sua primeiro posição "0" ou "1", sendo
     * "0", indica que não há mais zeros na matriz, sendo "1", indica que há
     * zeros na matriz, já a segunda posição indica que a linha, e a terceira a
     * coluna do zero encontrado.
     */
    protected int[] verificaZero(int matriz[][]) {
        int valores_referencia[];
        int linha_zero;
        int coluna_zero = 0;
        for (linha_zero = 0; linha_zero < this.n; linha_zero++) {
            for (coluna_zero = 0; coluna_zero < this.n; coluna_zero++) {
                if (matriz[linha_zero][coluna_zero] == 0) {
                    valores_referencia = new int[]{1, linha_zero, coluna_zero};
                    return valores_referencia;
                }
            }
        }
        valores_referencia = new int[]{0, linha_zero, coluna_zero};
        return valores_referencia;
    }
    
    public int getChamadas(){
        return this.conta;
    }

    /**
     * Este método imprime o tabuleiro, e seu detalhes
     */
    public void imprime() {
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if ((j % 3) == 0 && j != 0) {
                    System.out.print("|");
                    System.out.print(" " + matriz[i][j] + " ");
                } else {
                    System.out.print(matriz[i][j] + " ");
                }
            }
            if (((i + 1) % 3) == 0 && i != this.n - 1) {
                System.out.println();
                System.out.print("---------------------");
            }
            System.out.println();
        }
    }

}
