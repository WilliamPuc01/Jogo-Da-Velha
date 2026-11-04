package org.example.jogo;


public class Marca
{
    private char simbolo;

    public Marca (char smb) throws Exception
    {
        // validar smb, garantindo que ele
        // seja 'X' ou 'O' e coloca-lo em
        // simbolo
        // dar excecao em caso de valor invalido (minusculo, diferente de X ou O, etc)
        if(smb != 'X' && smb != 'O')
            throw new Exception("Só pode digitar X ou O");
            
        this.simbolo = smb;

    }

    public char getSimbolo ()
    {
        // retornar simbolo
        return simbolo;
    }

    @Override
    public String toString ()
    {
        return ""+simbolo;
    }

    @Override
    public boolean equals (Object obj)
    {
        if(obj == this)return true;
        if(obj == null)return false;
        if( getClass() != obj.getClass())return false;
        Marca outro = (Marca) obj;
        return  simbolo == outro.simbolo;
        // retornar true se this for igual a obj ou false caso contrario
    }

    @Override
    public int hashCode ()
    {
        // faça
        int result = 1;

        result = 31 * result + (int) simbolo;

        return result;
    }
}