package org.example.jogo;
public class Tabuleiro implements Cloneable
{
    // a principio, toda igual a null
    private Marca matriz [][];

    public Tabuleiro ()
    {
        this.matriz = new Marca [3][3];
    }

    public boolean haMarcaNaPosicao (int lin, int col) throws Exception
    {
        // verifico se em matriz[lin][col]
        // tem null, retornando false em caso
        // afirmativo, ou true em caso negativo
        // dar excecao caso lin ou col invalido

        if (lin < 0 || lin >= 3 || col < 0 || col >= 3) {
            throw new Exception("Posição inválida: linha ou coluna fora do tabuleiro.");
        }

        return this.matriz[lin][col] != null;

    }

    public Marca getMarcaNaPosicao (int lin, int col) throws Exception
    {
        // retornar a marca que tem em
        // matriz[lin][col]
        // dar excecao caso lin ou col invalido
        if( lin < 0 || lin >= 3 || col < 0 || col >= 3)throw new Exception("Posicao invalida");

        return this.matriz[lin][col];
    }

    public void setMarcaNaPosicao (Marca mrc, int lin, int col) throws Exception
    {
        // colocar mrc em matriz[lin][col],
        // dar excecao caso lin ou col invalido
        // dar excecao caso mrc seja null

        if( lin < 0 || lin >= 3 || col < 0 || col >= 3)throw new Exception("Posicao invalida");
        if(mrc == null)throw new Exception("Marca nao pode ser nula");

        this.matriz[lin][col] = mrc;
    }

    @Override
    public String toString ()
    {
        // retornar em um String o tabuleiro
        // formatado para ser exibido na tela
        String resultado = "";
        for (int l = 0; l < 3; l++) {
            for (int c = 0; c < 3; c++) {
                if (this.matriz[l][c] == null) {
                    resultado += "[ ]";
                } else {
                    resultado += "[" + this.matriz[l][c].toString() + "]";
                }
            }
            if (l < 2) {
                resultado += "\n";
            }
        }
        return resultado;
    }

    @Override
    public boolean equals (Object obj)
    {
        // retornar true se this for igual a obj ou false caso contrario
        if (obj == this) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        Tabuleiro outro = (Tabuleiro) obj;

        for(int i = 0; i < 3 ; i++){
            for(int j = 0 ; j < 3 ; j++){
                Marca m1 = this.matriz[i][j];
                Marca m2 = outro.matriz[i][j];

                if(m1 == null) {
                    if (m2 == null) return false;
                }
                else{
                    if(!m1.equals(m2))return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode ()
    {
        int result = 1;

        for(int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                Marca m = this.matriz[i][j];

                if(m == null){
                    result = 7 * result + 0;
                }else {
                    result = 7 * result + m.hashCode();
                }
            }
        }
        return result;
        // faça
    }

    // construtor de copia
    public Tabuleiro(Tabuleiro modelo) throws Exception {
        if (modelo == null) {
            throw new Exception("Modelo para copia nao pode ser nulo");
        }

        this.matriz = new Marca[3][3];

        for (int l = 0; l < 3; l++) {
            for (int c = 0; c < 3; c++) {

                this.matriz[l][c] = modelo.matriz[l][c];
            }
        }
    }

    @Override
    public Object clone ()
    {
        // faça
        Tabuleiro clone = null;
        try {
            clone = new Tabuleiro(this);
        } catch (Exception e) {

        }
        return clone;
    }
}