package sudoku;

/**
 *
 * @author Fernando
 */
public class Nodo {

    private int linha;
    private int coluna;
    public int qtd = 9;
    int valor[] = new int[10];

    public Nodo() {
        for (int i = 1; i < this.valor.length; i++) {
            this.valor[i] = i;
        }
    }

    public Nodo(int linha, int coluna) {
        for (int i = 1; i < this.valor.length; i++) {
            this.valor[i] = i;
        }

    }
    
    public Nodo(Nodo n){
        this.linha = n.linha;
        this.coluna = n.coluna;
        this.qtd = n.qtd;
        System.arraycopy(n.valor, 0, this.valor, 0, n.valor.length);
    }

    public Nodo copy() {
        Nodo copia = new Nodo(linha,coluna);
        copia.qtd = qtd;
        System.arraycopy(this.valor, 0, copia.valor, 0, this.valor.length);
        return copia;
    }
    @Override
    public boolean equals(Object objeto) {
        return this.linha == ((Nodo) objeto).linha && this.coluna == ((Nodo) objeto).coluna;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }

    public void setValor(int[] valor) {
        System.arraycopy(valor, 0, this.valor, 0, valor.length);
    }

    //Causa exceção
    public void setPosicao(int posicao) {
        for (int i = 0; i < this.valor.length; i++) {
            this.valor[i] = -1;
        }
        this.valor[posicao] = posicao;
        this.qtd = 1;
    }

    //Causa exceção
    public void setValorPosicao(int posicao, int valor) {
        if (this.valor[posicao] != -1 && valor == -1) {
            this.valor[posicao] = valor;
            this.qtd--;
        }
    }
}
