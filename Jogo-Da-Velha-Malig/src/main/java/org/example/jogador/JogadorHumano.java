package org.example.jogador;

import org.example.jogo.Marca;
import org.example.jogo.Tabuleiro;
import org.example.jogador.Jogador;
import java.io.*;

public class JogadorHumano implements Jogador, Cloneable
{
    private Marca          marca     = null;
    private Tabuleiro      tabDoJogo = null;
    private BufferedReader teclado   = new BufferedReader
            (new InputStreamReader
                    (System.in));

    public JogadorHumano (Tabuleiro tab) throws Exception
    {
        if (tab == null)
            throw new Exception("Tabuleiro nulo");
        // atribuir tab para tabDoJogo
        this.tabDoJogo = tab;
        // pedir para o jogador escolher
        System.out.print("Escolha sua marca (X ou O): ");
        // sua marca e permitir digitacao
        String input = teclado.readLine();
        if (input == null || input.length() != 1)
            throw new Exception("Marca invalida");
        this.marca = new Marca(input.charAt(0));
        // intanciando marca

    }

    public JogadorHumano (Tabuleiro tab, Marca mrc) throws Exception
    {
        if (tab == null)
            throw new Exception("Tabuleiro nulo");
        if (mrc == null)
            throw new Exception("Marca nula");
        // atribuir tab para tabDoJogo
        this.tabDoJogo = tab;
        // e mrc para marca
        this.marca = mrc;
    }

    public Marca getMarca ()
    {
        // retorna a marca do jogador
        return this.marca;
    }

    public void facaSuaJogada ()
    {
        // pergunta para o jogador onde ele quer
        System.out.print("Digite a linha (0, 1 ou 2) e a coluna (0, 1 ou 2) separados por espaco: ");
        // jogar, permitindo digitacao e chamando
        // os métodos apropriados de tabDoJogo
    }

    public void vcGanhou ()
    {
        System.out.println("Parabens! Voce ganhou!");
        // mostra mensagem indicando vitoria
    }

    public void vcPerdeu ()
    {
        System.out.println("Que pena! Voce perdeu!");
        // mostra mensagem indicando derrota
    }

    public void deuVelha ()
    {
        // mostra mensagem indicando empate
        System.out.println("Deu velha!");
    }

    public boolean querMaisUmaPartida ()
    {
        // perguntar se deseja jogar de novo
        System.out.print("Deseja jogar mais uma partida? (S/n): ");
        // permitindo digitacao e retornando
        String input = null;
        try
        {
            input = teclado.readLine();
        }
        catch (IOException e)
        {
            System.out.println("Erro ao ler entrada do usuario.");
        }
        char resposta = input.charAt(0);

        if (resposta == 'S' || resposta == 's')
            return true;
        
        return false;
    }

    @Override
    public String toString ()
    {
        // faça
        return "Jogador Humano: " + this.marca;
    }

    @Override
    public boolean equals (Object obj)
    {
        // faça
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (this.getClass() != obj.getClass())
            return false;
            
        JogadorHumano other = (JogadorHumano) obj;
        if (marca == null)
        {
            if (other.marca != null)
                return false;
        }
        else if (!marca.equals(other.marca))
            return false;
        return true;
    }


    @Override
    public int hashCode ()
    {
        // faça
        final int base = 13;

        int result = 7;
        result = base * result + ((marca == null) ? 0 : marca.hashCode());
        return result;
    }

    // construtor de cópia
    public JogadorHumano (JogadorHumano modelo) throws Exception

    {
        // faça
        if (modelo == null)
            throw new Exception("Modelo nulo");
        this.marca = modelo.marca;
        this.tabDoJogo = modelo.tabDoJogo;
    }

    @Override
    public Object clone ()
    {
        // faça
        try
        {
            return new JogadorHumano(this);
        }
        catch (Exception e)
        {
            return null;
        }
    }
}