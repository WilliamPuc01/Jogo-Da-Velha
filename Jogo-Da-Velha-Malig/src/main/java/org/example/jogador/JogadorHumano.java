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
        // sua marca e permitir digitacao
        // intanciando marca
        // (repete até digitar X ou O; aceita minúscula)
        while (true)
        {
            System.out.print("Escolha sua marca (X ou O): ");
            String input = this.teclado.readLine();
            if (input == null) // fim da entrada (Ctrl+D)
                throw new Exception("Entrada encerrada");

            input = input.trim().toUpperCase();
            if (input.length() == 1)
            {
                try
                {
                    this.marca = new Marca(input.charAt(0));
                    break; // marca válida: sai do laço
                }
                catch (Exception erro)
                {} // não era X nem O: pergunta de novo
            }
            System.out.println("Marca invalida. Digite X ou O.");
        }
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
        // jogar, permitindo digitacao e chamando
        // os métodos apropriados de tabDoJogo
        //
        // Repete até a jogada ser válida: dois números de 0 a 2,
        // separados por espaço, apontando para uma casa vazia.
        while (true)
        {
            this.mostrarTabuleiro();
            System.out.print("Sua vez (" + this.marca + "). Digite a linha e a coluna (0, 1 ou 2) separados por espaco: ");

            try
            {
                String input = this.teclado.readLine();
                if (input == null) // fim da entrada (Ctrl+D): desiste da jogada
                    return;

                String[] partes = input.trim().split(" +");
                if (partes.length != 2)
                {
                    System.out.println("Digite dois numeros separados por espaco, por exemplo: 1 2");
                    continue;
                }

                int lin = Integer.parseInt(partes[0]);
                int col = Integer.parseInt(partes[1]);

                // haMarcaNaPosicao lança exceção se lin ou col estiver fora de 0..2
                if (this.tabDoJogo.haMarcaNaPosicao(lin, col))
                {
                    System.out.println("Essa casa ja esta ocupada. Escolha outra.");
                    continue;
                }

                this.tabDoJogo.setMarcaNaPosicao(this.marca, lin, col);
                return; // jogada feita
            }
            catch (NumberFormatException erro)
            {
                System.out.println("Digite apenas numeros, por exemplo: 1 2");
            }
            catch (IOException erro)
            {
                System.out.println("Erro ao ler o teclado. Tente de novo.");
            }
            catch (Exception erro) // posição fora do tabuleiro
            {
                System.out.println("Posicao invalida. Linha e coluna vao de 0 a 2.");
            }
        }
    }

    // Mostra o tabuleiro com os números das linhas e colunas,
    // para facilitar a digitação da jogada:
    //        0  1  2
    //    0  [X][ ][ ]
    //    1  [ ][O][ ]
    //    2  [ ][ ][ ]
    private void mostrarTabuleiro ()
    {
        String[] linhas = this.tabDoJogo.toString().split("\n");

        System.out.println();
        System.out.println("    0  1  2");
        for (int l = 0; l < linhas.length; l++)
            System.out.println(l + "  " + linhas[l]);
        System.out.println();
    }

    public void vcGanhou ()
    {
        // mostra mensagem indicando vitoria
        this.mostrarTabuleiro();
        System.out.println("Parabens! Voce ganhou!");
    }

    public void vcPerdeu ()
    {
        // mostra mensagem indicando derrota
        this.mostrarTabuleiro();
        System.out.println("Que pena! Voce perdeu!");
    }

    public void deuVelha ()
    {
        // mostra mensagem indicando empate
        this.mostrarTabuleiro();
        System.out.println("Deu velha!");
    }

    public boolean querMaisUmaPartida ()
    {
        // perguntar se deseja jogar de novo
        // permitindo digitacao e retornando
        // true ou false, conforme a resposta
        // (só Enter conta como S, a opção em maiúscula no "(S/n)")
        while (true)
        {
            System.out.print("Deseja jogar mais uma partida? (S/n): ");

            String input;
            try
            {
                input = this.teclado.readLine();
            }
            catch (IOException e)
            {
                System.out.println("Erro ao ler entrada do usuario.");
                return false;
            }

            if (input == null) // fim da entrada (Ctrl+D): encerra
                return false;

            input = input.trim();
            if (input.isEmpty() || input.equalsIgnoreCase("S"))
                return true;
            if (input.equalsIgnoreCase("N"))
                return false;

            System.out.println("Responda S ou N.");
        }
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