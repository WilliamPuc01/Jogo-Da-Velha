package org.example.jogador;

import org.example.jogo.Marca;
import org.example.jogo.Tabuleiro;

public class JogadorAutomatico implements Jogador, Cloneable
{
    private Marca     marca     = null;
    private Tabuleiro tabDoJogo = null;

    public JogadorAutomatico (Tabuleiro tab) throws Exception
    {   
        if (tab == null)
            throw new Exception("Tabuleiro nulo");
        
        this.tabDoJogo = tab;
        // atribuir tab para tabDoJogo
        // fazer o jogador escolher sua marca
        // via sorteio de numeros aleatorio
        if ((int)(Math.random()*2) == 0)
            marca= new Marca('X');
        else
            marca=new Marca('O');
    }

    public JogadorAutomatico (Tabuleiro tab, Marca mrc) throws Exception
    {
        if (tab == null || mrc == null)
            throw new Exception("Tabuleiro ou marca nulos");
        
        // atribuir tab para tabDoJogo
        // e mrc para marca
        this.tabDoJogo = tab;
        this.marca = mrc;
    }

    public Marca getMarca ()
    {
        // retorna a marca do jogador
        return this.marca;
    }

    public void facaSuaJogada ()
    {
        // O robô faz UMA jogada por vez. Tudo abaixo é uma única corrente de
        // if / else if, em ordem de prioridade: o primeiro teste verdadeiro
        // joga, e todos os outros são pulados.
        //
        //   1) ganhar  ->  2) atrapalhar  ->  3) jogada de futuro  ->  4) qualquer casa livre
        //
        // As estratégias 1 a 3 passam pelas 8 linhas do tabuleiro, com 3 ifs
        // por linha (um para cada casa da linha que pode receber a jogada):
        //
        //   linha 0:  (0,0) (0,1) (0,2)        coluna 0:  (0,0) (1,0) (2,0)
        //   linha 1:  (1,0) (1,1) (1,2)        coluna 1:  (0,1) (1,1) (2,1)
        //   linha 2:  (2,0) (2,1) (2,2)        coluna 2:  (0,2) (1,2) (2,2)
        //   diagonal principal:   (0,0) (1,1) (2,2)
        //   diagonal secundária:  (0,2) (1,1) (2,0)
        //
        // Como ler cada condição:
        //   !haMarcaNaPosicao(l,c)                                              -> casa vazia
        //    haMarcaNaPosicao(l,c) &&  getMarcaNaPosicao(l,c).equals(this.marca) -> casa minha
        //    haMarcaNaPosicao(l,c) && !getMarcaNaPosicao(l,c).equals(this.marca) -> casa do adversário
        // O haMarcaNaPosicao vem antes porque, numa casa vazia, getMarcaNaPosicao
        // devolve null e o equals quebraria. O && para no primeiro false.
        //
        // Legenda dos desenhos:
        //   M = marca do robô      A = marca do adversário      * = onde o robô joga
        //   [ ] = precisa estar vazia                           . = tanto faz

        try
        {
            // ======================================================================
            // 1) GANHAR
            //    Linha com 2 marcas minhas e 1 casa vazia: jogo na vazia e ganho.
            // ======================================================================

            // tentando ganhar na linha 0
            //     1º if        2º if        3º if
            //   [*][M][M]    [M][*][M]    [M][M][*]
            //   [.][.][.]    [.][.][.]    [.][.][.]
            //   [.][.][.]    [.][.][.]    [.][.][.]
            if (!this.tabDoJogo.haMarcaNaPosicao(0,0) &&
                 this.tabDoJogo.haMarcaNaPosicao(0,1) &&  this.tabDoJogo.getMarcaNaPosicao(0,1).equals(this.marca) &&
                 this.tabDoJogo.haMarcaNaPosicao(0,2) &&  this.tabDoJogo.getMarcaNaPosicao(0,2).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 0,0);
            else if ( this.tabDoJogo.haMarcaNaPosicao(0,0) &&  this.tabDoJogo.getMarcaNaPosicao(0,0).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(0,1) &&
                      this.tabDoJogo.haMarcaNaPosicao(0,2) &&  this.tabDoJogo.getMarcaNaPosicao(0,2).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 0,1);
            else if ( this.tabDoJogo.haMarcaNaPosicao(0,0) &&  this.tabDoJogo.getMarcaNaPosicao(0,0).equals(this.marca) &&
                      this.tabDoJogo.haMarcaNaPosicao(0,1) &&  this.tabDoJogo.getMarcaNaPosicao(0,1).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(0,2))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 0,2);

            // tentando ganhar na linha 1
            //     1º if        2º if        3º if
            //   [.][.][.]    [.][.][.]    [.][.][.]
            //   [*][M][M]    [M][*][M]    [M][M][*]
            //   [.][.][.]    [.][.][.]    [.][.][.]
            else if (!this.tabDoJogo.haMarcaNaPosicao(1,0) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,1) &&  this.tabDoJogo.getMarcaNaPosicao(1,1).equals(this.marca) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,2) &&  this.tabDoJogo.getMarcaNaPosicao(1,2).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 1,0);
            else if ( this.tabDoJogo.haMarcaNaPosicao(1,0) &&  this.tabDoJogo.getMarcaNaPosicao(1,0).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(1,1) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,2) &&  this.tabDoJogo.getMarcaNaPosicao(1,2).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 1,1);
            else if ( this.tabDoJogo.haMarcaNaPosicao(1,0) &&  this.tabDoJogo.getMarcaNaPosicao(1,0).equals(this.marca) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,1) &&  this.tabDoJogo.getMarcaNaPosicao(1,1).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(1,2))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 1,2);

            // tentando ganhar na linha 2:  (2,0) (2,1) (2,2)
            else if (!this.tabDoJogo.haMarcaNaPosicao(2,0) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,1) &&  this.tabDoJogo.getMarcaNaPosicao(2,1).equals(this.marca) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,2) &&  this.tabDoJogo.getMarcaNaPosicao(2,2).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 2,0);
            else if ( this.tabDoJogo.haMarcaNaPosicao(2,0) &&  this.tabDoJogo.getMarcaNaPosicao(2,0).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(2,1) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,2) &&  this.tabDoJogo.getMarcaNaPosicao(2,2).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 2,1);
            else if ( this.tabDoJogo.haMarcaNaPosicao(2,0) &&  this.tabDoJogo.getMarcaNaPosicao(2,0).equals(this.marca) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,1) &&  this.tabDoJogo.getMarcaNaPosicao(2,1).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(2,2))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 2,2);

            // tentando ganhar na coluna 0:  (0,0) (1,0) (2,0)
            else if (!this.tabDoJogo.haMarcaNaPosicao(0,0) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,0) &&  this.tabDoJogo.getMarcaNaPosicao(1,0).equals(this.marca) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,0) &&  this.tabDoJogo.getMarcaNaPosicao(2,0).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 0,0);
            else if ( this.tabDoJogo.haMarcaNaPosicao(0,0) &&  this.tabDoJogo.getMarcaNaPosicao(0,0).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(1,0) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,0) &&  this.tabDoJogo.getMarcaNaPosicao(2,0).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 1,0);
            else if ( this.tabDoJogo.haMarcaNaPosicao(0,0) &&  this.tabDoJogo.getMarcaNaPosicao(0,0).equals(this.marca) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,0) &&  this.tabDoJogo.getMarcaNaPosicao(1,0).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(2,0))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 2,0);

            // tentando ganhar na coluna 1:  (0,1) (1,1) (2,1)
            else if (!this.tabDoJogo.haMarcaNaPosicao(0,1) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,1) &&  this.tabDoJogo.getMarcaNaPosicao(1,1).equals(this.marca) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,1) &&  this.tabDoJogo.getMarcaNaPosicao(2,1).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 0,1);
            else if ( this.tabDoJogo.haMarcaNaPosicao(0,1) &&  this.tabDoJogo.getMarcaNaPosicao(0,1).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(1,1) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,1) &&  this.tabDoJogo.getMarcaNaPosicao(2,1).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 1,1);
            else if ( this.tabDoJogo.haMarcaNaPosicao(0,1) &&  this.tabDoJogo.getMarcaNaPosicao(0,1).equals(this.marca) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,1) &&  this.tabDoJogo.getMarcaNaPosicao(1,1).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(2,1))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 2,1);

            // tentando ganhar na coluna 2:  (0,2) (1,2) (2,2)
            else if (!this.tabDoJogo.haMarcaNaPosicao(0,2) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,2) &&  this.tabDoJogo.getMarcaNaPosicao(1,2).equals(this.marca) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,2) &&  this.tabDoJogo.getMarcaNaPosicao(2,2).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 0,2);
            else if ( this.tabDoJogo.haMarcaNaPosicao(0,2) &&  this.tabDoJogo.getMarcaNaPosicao(0,2).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(1,2) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,2) &&  this.tabDoJogo.getMarcaNaPosicao(2,2).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 1,2);
            else if ( this.tabDoJogo.haMarcaNaPosicao(0,2) &&  this.tabDoJogo.getMarcaNaPosicao(0,2).equals(this.marca) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,2) &&  this.tabDoJogo.getMarcaNaPosicao(1,2).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(2,2))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 2,2);

            // tentando ganhar na diagonal principal:  (0,0) (1,1) (2,2)
            else if (!this.tabDoJogo.haMarcaNaPosicao(0,0) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,1) &&  this.tabDoJogo.getMarcaNaPosicao(1,1).equals(this.marca) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,2) &&  this.tabDoJogo.getMarcaNaPosicao(2,2).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 0,0);
            else if ( this.tabDoJogo.haMarcaNaPosicao(0,0) &&  this.tabDoJogo.getMarcaNaPosicao(0,0).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(1,1) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,2) &&  this.tabDoJogo.getMarcaNaPosicao(2,2).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 1,1);
            else if ( this.tabDoJogo.haMarcaNaPosicao(0,0) &&  this.tabDoJogo.getMarcaNaPosicao(0,0).equals(this.marca) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,1) &&  this.tabDoJogo.getMarcaNaPosicao(1,1).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(2,2))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 2,2);

            // tentando ganhar na diagonal secundária:  (0,2) (1,1) (2,0)
            else if (!this.tabDoJogo.haMarcaNaPosicao(0,2) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,1) &&  this.tabDoJogo.getMarcaNaPosicao(1,1).equals(this.marca) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,0) &&  this.tabDoJogo.getMarcaNaPosicao(2,0).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 0,2);
            else if ( this.tabDoJogo.haMarcaNaPosicao(0,2) &&  this.tabDoJogo.getMarcaNaPosicao(0,2).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(1,1) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,0) &&  this.tabDoJogo.getMarcaNaPosicao(2,0).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 1,1);
            else if ( this.tabDoJogo.haMarcaNaPosicao(0,2) &&  this.tabDoJogo.getMarcaNaPosicao(0,2).equals(this.marca) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,1) &&  this.tabDoJogo.getMarcaNaPosicao(1,1).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(2,0))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 2,0);

            // ======================================================================
            // 2) ATRAPALHAR
            //    Linha com 2 marcas do adversário e 1 casa vazia: jogo na vazia
            //    para ele não ganhar na próxima jogada. Igual ao GANHAR, só que
            //    com um ! antes do equals ("não é minha" = "é do adversário").
            // ======================================================================

            // tentando atrapalhar vitoria na linha 0
            //     1º if        2º if        3º if
            //   [*][A][A]    [A][*][A]    [A][A][*]
            //   [.][.][.]    [.][.][.]    [.][.][.]
            //   [.][.][.]    [.][.][.]    [.][.][.]
            else if (!this.tabDoJogo.haMarcaNaPosicao(0,0) &&
                      this.tabDoJogo.haMarcaNaPosicao(0,1) && !this.tabDoJogo.getMarcaNaPosicao(0,1).equals(this.marca) &&
                      this.tabDoJogo.haMarcaNaPosicao(0,2) && !this.tabDoJogo.getMarcaNaPosicao(0,2).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 0,0);
            else if ( this.tabDoJogo.haMarcaNaPosicao(0,0) && !this.tabDoJogo.getMarcaNaPosicao(0,0).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(0,1) &&
                      this.tabDoJogo.haMarcaNaPosicao(0,2) && !this.tabDoJogo.getMarcaNaPosicao(0,2).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 0,1);
            else if ( this.tabDoJogo.haMarcaNaPosicao(0,0) && !this.tabDoJogo.getMarcaNaPosicao(0,0).equals(this.marca) &&
                      this.tabDoJogo.haMarcaNaPosicao(0,1) && !this.tabDoJogo.getMarcaNaPosicao(0,1).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(0,2))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 0,2);

            // tentando atrapalhar vitoria na linha 1
            //     1º if        2º if        3º if
            //   [.][.][.]    [.][.][.]    [.][.][.]
            //   [*][A][A]    [A][*][A]    [A][A][*]
            //   [.][.][.]    [.][.][.]    [.][.][.]
            else if (!this.tabDoJogo.haMarcaNaPosicao(1,0) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,1) && !this.tabDoJogo.getMarcaNaPosicao(1,1).equals(this.marca) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,2) && !this.tabDoJogo.getMarcaNaPosicao(1,2).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 1,0);
            else if ( this.tabDoJogo.haMarcaNaPosicao(1,0) && !this.tabDoJogo.getMarcaNaPosicao(1,0).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(1,1) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,2) && !this.tabDoJogo.getMarcaNaPosicao(1,2).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 1,1);
            else if ( this.tabDoJogo.haMarcaNaPosicao(1,0) && !this.tabDoJogo.getMarcaNaPosicao(1,0).equals(this.marca) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,1) && !this.tabDoJogo.getMarcaNaPosicao(1,1).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(1,2))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 1,2);

            // tentando atrapalhar vitoria na linha 2:  (2,0) (2,1) (2,2)
            else if (!this.tabDoJogo.haMarcaNaPosicao(2,0) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,1) && !this.tabDoJogo.getMarcaNaPosicao(2,1).equals(this.marca) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,2) && !this.tabDoJogo.getMarcaNaPosicao(2,2).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 2,0);
            else if ( this.tabDoJogo.haMarcaNaPosicao(2,0) && !this.tabDoJogo.getMarcaNaPosicao(2,0).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(2,1) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,2) && !this.tabDoJogo.getMarcaNaPosicao(2,2).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 2,1);
            else if ( this.tabDoJogo.haMarcaNaPosicao(2,0) && !this.tabDoJogo.getMarcaNaPosicao(2,0).equals(this.marca) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,1) && !this.tabDoJogo.getMarcaNaPosicao(2,1).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(2,2))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 2,2);

            // tentando atrapalhar vitoria na coluna 0:  (0,0) (1,0) (2,0)
            else if (!this.tabDoJogo.haMarcaNaPosicao(0,0) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,0) && !this.tabDoJogo.getMarcaNaPosicao(1,0).equals(this.marca) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,0) && !this.tabDoJogo.getMarcaNaPosicao(2,0).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 0,0);
            else if ( this.tabDoJogo.haMarcaNaPosicao(0,0) && !this.tabDoJogo.getMarcaNaPosicao(0,0).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(1,0) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,0) && !this.tabDoJogo.getMarcaNaPosicao(2,0).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 1,0);
            else if ( this.tabDoJogo.haMarcaNaPosicao(0,0) && !this.tabDoJogo.getMarcaNaPosicao(0,0).equals(this.marca) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,0) && !this.tabDoJogo.getMarcaNaPosicao(1,0).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(2,0))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 2,0);

            // tentando atrapalhar vitoria na coluna 1:  (0,1) (1,1) (2,1)
            else if (!this.tabDoJogo.haMarcaNaPosicao(0,1) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,1) && !this.tabDoJogo.getMarcaNaPosicao(1,1).equals(this.marca) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,1) && !this.tabDoJogo.getMarcaNaPosicao(2,1).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 0,1);
            else if ( this.tabDoJogo.haMarcaNaPosicao(0,1) && !this.tabDoJogo.getMarcaNaPosicao(0,1).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(1,1) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,1) && !this.tabDoJogo.getMarcaNaPosicao(2,1).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 1,1);
            else if ( this.tabDoJogo.haMarcaNaPosicao(0,1) && !this.tabDoJogo.getMarcaNaPosicao(0,1).equals(this.marca) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,1) && !this.tabDoJogo.getMarcaNaPosicao(1,1).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(2,1))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 2,1);

            // tentando atrapalhar vitoria na coluna 2:  (0,2) (1,2) (2,2)
            else if (!this.tabDoJogo.haMarcaNaPosicao(0,2) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,2) && !this.tabDoJogo.getMarcaNaPosicao(1,2).equals(this.marca) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,2) && !this.tabDoJogo.getMarcaNaPosicao(2,2).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 0,2);
            else if ( this.tabDoJogo.haMarcaNaPosicao(0,2) && !this.tabDoJogo.getMarcaNaPosicao(0,2).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(1,2) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,2) && !this.tabDoJogo.getMarcaNaPosicao(2,2).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 1,2);
            else if ( this.tabDoJogo.haMarcaNaPosicao(0,2) && !this.tabDoJogo.getMarcaNaPosicao(0,2).equals(this.marca) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,2) && !this.tabDoJogo.getMarcaNaPosicao(1,2).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(2,2))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 2,2);

            // tentando atrapalhar vitoria na diagonal principal:  (0,0) (1,1) (2,2)
            else if (!this.tabDoJogo.haMarcaNaPosicao(0,0) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,1) && !this.tabDoJogo.getMarcaNaPosicao(1,1).equals(this.marca) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,2) && !this.tabDoJogo.getMarcaNaPosicao(2,2).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 0,0);
            else if ( this.tabDoJogo.haMarcaNaPosicao(0,0) && !this.tabDoJogo.getMarcaNaPosicao(0,0).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(1,1) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,2) && !this.tabDoJogo.getMarcaNaPosicao(2,2).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 1,1);
            else if ( this.tabDoJogo.haMarcaNaPosicao(0,0) && !this.tabDoJogo.getMarcaNaPosicao(0,0).equals(this.marca) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,1) && !this.tabDoJogo.getMarcaNaPosicao(1,1).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(2,2))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 2,2);

            // tentando atrapalhar vitoria na diagonal secundária:  (0,2) (1,1) (2,0)
            else if (!this.tabDoJogo.haMarcaNaPosicao(0,2) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,1) && !this.tabDoJogo.getMarcaNaPosicao(1,1).equals(this.marca) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,0) && !this.tabDoJogo.getMarcaNaPosicao(2,0).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 0,2);
            else if ( this.tabDoJogo.haMarcaNaPosicao(0,2) && !this.tabDoJogo.getMarcaNaPosicao(0,2).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(1,1) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,0) && !this.tabDoJogo.getMarcaNaPosicao(2,0).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 1,1);
            else if ( this.tabDoJogo.haMarcaNaPosicao(0,2) && !this.tabDoJogo.getMarcaNaPosicao(0,2).equals(this.marca) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,1) && !this.tabDoJogo.getMarcaNaPosicao(1,1).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(2,0))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 2,0);

            // ======================================================================
            // 3) JOGADA DE FUTURO
            //    Linha com 1 marca minha e 2 casas vazias: planto a 2ª marca na
            //    primeira casa vazia da linha, para ter chance de ganhar ali na
            //    próxima jogada.
            // ======================================================================

            // tentando jogada de futuro na linha 0
            //     1º if        2º if        3º if
            //   [M][*][ ]    [*][M][ ]    [*][ ][M]
            //   [.][.][.]    [.][.][.]    [.][.][.]
            //   [.][.][.]    [.][.][.]    [.][.][.]
            else if ( this.tabDoJogo.haMarcaNaPosicao(0,0) &&  this.tabDoJogo.getMarcaNaPosicao(0,0).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(0,1) &&
                     !this.tabDoJogo.haMarcaNaPosicao(0,2))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 0,1);
            else if (!this.tabDoJogo.haMarcaNaPosicao(0,0) &&
                      this.tabDoJogo.haMarcaNaPosicao(0,1) &&  this.tabDoJogo.getMarcaNaPosicao(0,1).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(0,2))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 0,0);
            else if (!this.tabDoJogo.haMarcaNaPosicao(0,0) &&
                     !this.tabDoJogo.haMarcaNaPosicao(0,1) &&
                      this.tabDoJogo.haMarcaNaPosicao(0,2) &&  this.tabDoJogo.getMarcaNaPosicao(0,2).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 0,0);

            // tentando jogada de futuro na linha 1
            //     1º if        2º if        3º if
            //   [.][.][.]    [.][.][.]    [.][.][.]
            //   [M][*][ ]    [*][M][ ]    [*][ ][M]
            //   [.][.][.]    [.][.][.]    [.][.][.]
            else if ( this.tabDoJogo.haMarcaNaPosicao(1,0) &&  this.tabDoJogo.getMarcaNaPosicao(1,0).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(1,1) &&
                     !this.tabDoJogo.haMarcaNaPosicao(1,2))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 1,1);
            else if (!this.tabDoJogo.haMarcaNaPosicao(1,0) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,1) &&  this.tabDoJogo.getMarcaNaPosicao(1,1).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(1,2))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 1,0);
            else if (!this.tabDoJogo.haMarcaNaPosicao(1,0) &&
                     !this.tabDoJogo.haMarcaNaPosicao(1,1) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,2) &&  this.tabDoJogo.getMarcaNaPosicao(1,2).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 1,0);

            // tentando jogada de futuro na linha 2:  (2,0) (2,1) (2,2)
            else if ( this.tabDoJogo.haMarcaNaPosicao(2,0) &&  this.tabDoJogo.getMarcaNaPosicao(2,0).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(2,1) &&
                     !this.tabDoJogo.haMarcaNaPosicao(2,2))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 2,1);
            else if (!this.tabDoJogo.haMarcaNaPosicao(2,0) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,1) &&  this.tabDoJogo.getMarcaNaPosicao(2,1).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(2,2))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 2,0);
            else if (!this.tabDoJogo.haMarcaNaPosicao(2,0) &&
                     !this.tabDoJogo.haMarcaNaPosicao(2,1) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,2) &&  this.tabDoJogo.getMarcaNaPosicao(2,2).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 2,0);

            // tentando jogada de futuro na coluna 0:  (0,0) (1,0) (2,0)
            else if ( this.tabDoJogo.haMarcaNaPosicao(0,0) &&  this.tabDoJogo.getMarcaNaPosicao(0,0).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(1,0) &&
                     !this.tabDoJogo.haMarcaNaPosicao(2,0))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 1,0);
            else if (!this.tabDoJogo.haMarcaNaPosicao(0,0) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,0) &&  this.tabDoJogo.getMarcaNaPosicao(1,0).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(2,0))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 0,0);
            else if (!this.tabDoJogo.haMarcaNaPosicao(0,0) &&
                     !this.tabDoJogo.haMarcaNaPosicao(1,0) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,0) &&  this.tabDoJogo.getMarcaNaPosicao(2,0).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 0,0);

            // tentando jogada de futuro na coluna 1:  (0,1) (1,1) (2,1)
            else if ( this.tabDoJogo.haMarcaNaPosicao(0,1) &&  this.tabDoJogo.getMarcaNaPosicao(0,1).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(1,1) &&
                     !this.tabDoJogo.haMarcaNaPosicao(2,1))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 1,1);
            else if (!this.tabDoJogo.haMarcaNaPosicao(0,1) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,1) &&  this.tabDoJogo.getMarcaNaPosicao(1,1).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(2,1))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 0,1);
            else if (!this.tabDoJogo.haMarcaNaPosicao(0,1) &&
                     !this.tabDoJogo.haMarcaNaPosicao(1,1) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,1) &&  this.tabDoJogo.getMarcaNaPosicao(2,1).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 0,1);

            // tentando jogada de futuro na coluna 2:  (0,2) (1,2) (2,2)
            else if ( this.tabDoJogo.haMarcaNaPosicao(0,2) &&  this.tabDoJogo.getMarcaNaPosicao(0,2).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(1,2) &&
                     !this.tabDoJogo.haMarcaNaPosicao(2,2))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 1,2);
            else if (!this.tabDoJogo.haMarcaNaPosicao(0,2) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,2) &&  this.tabDoJogo.getMarcaNaPosicao(1,2).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(2,2))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 0,2);
            else if (!this.tabDoJogo.haMarcaNaPosicao(0,2) &&
                     !this.tabDoJogo.haMarcaNaPosicao(1,2) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,2) &&  this.tabDoJogo.getMarcaNaPosicao(2,2).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 0,2);

            // tentando jogada de futuro na diagonal principal:  (0,0) (1,1) (2,2)
            else if ( this.tabDoJogo.haMarcaNaPosicao(0,0) &&  this.tabDoJogo.getMarcaNaPosicao(0,0).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(1,1) &&
                     !this.tabDoJogo.haMarcaNaPosicao(2,2))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 1,1);
            else if (!this.tabDoJogo.haMarcaNaPosicao(0,0) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,1) &&  this.tabDoJogo.getMarcaNaPosicao(1,1).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(2,2))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 0,0);
            else if (!this.tabDoJogo.haMarcaNaPosicao(0,0) &&
                     !this.tabDoJogo.haMarcaNaPosicao(1,1) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,2) &&  this.tabDoJogo.getMarcaNaPosicao(2,2).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 0,0);

            // tentando jogada de futuro na diagonal secundária:  (0,2) (1,1) (2,0)
            else if ( this.tabDoJogo.haMarcaNaPosicao(0,2) &&  this.tabDoJogo.getMarcaNaPosicao(0,2).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(1,1) &&
                     !this.tabDoJogo.haMarcaNaPosicao(2,0))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 1,1);
            else if (!this.tabDoJogo.haMarcaNaPosicao(0,2) &&
                      this.tabDoJogo.haMarcaNaPosicao(1,1) &&  this.tabDoJogo.getMarcaNaPosicao(1,1).equals(this.marca) &&
                     !this.tabDoJogo.haMarcaNaPosicao(2,0))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 0,2);
            else if (!this.tabDoJogo.haMarcaNaPosicao(0,2) &&
                     !this.tabDoJogo.haMarcaNaPosicao(1,1) &&
                      this.tabDoJogo.haMarcaNaPosicao(2,0) &&  this.tabDoJogo.getMarcaNaPosicao(2,0).equals(this.marca))
                this.tabDoJogo.setMarcaNaPosicao(this.marca, 0,2);

            // ======================================================================
            // 4) QUALQUER CASA LIVRE
            //    Nenhuma estratégia acima serviu (por exemplo, na primeira jogada
            //    do robô, com o tabuleiro vazio): joga na primeira casa vazia,
            //    da esquerda para a direita, de cima para baixo.
            // ======================================================================
            else
            {
                for (int lin = 0; lin < 3; lin++)
                    for (int col = 0; col < 3; col++)
                        if (!this.tabDoJogo.haMarcaNaPosicao(lin,col))
                        {
                            this.tabDoJogo.setMarcaNaPosicao(this.marca, lin,col);
                            return; // já jogou: sai do método
                        }
            }
        }
        catch (Exception erro)
        {} // ignorando pois sei que não estou usando posicao invalida nem comparando com equals um null
    }

    @Override
    public String toString ()
    {
        return "Jogador Automatico: " + this.marca;
    }

    @Override
    public boolean equals (Object obj)
    {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (this.getClass() != obj.getClass())
            return false;

        JogadorAutomatico outro = (JogadorAutomatico)obj;

        return this.marca.equals(outro.marca) && this.tabDoJogo.equals(outro.tabDoJogo);
    }

    @Override
    public int hashCode ()
    {
        // faça
        int ret = 666;
        ret = 7 * ret + this.marca.hashCode();
        ret = 7 * ret + this.tabDoJogo.hashCode();
        return ret;
    }

    // construtor de cópia
    public JogadorAutomatico (JogadorAutomatico modelo) throws Exception
    {
        // faça
        if (modelo == null)
            throw new Exception("Modelo ausente");

        this.marca = new Marca(modelo.marca.getSimbolo());
        this.tabDoJogo = new Tabuleiro(modelo.tabDoJogo);
    }

    @Override
    public Object clone ()
    {
        // faça
        try {
            return new JogadorAutomatico(this);
        }
        catch (Exception erro)
        {
            return null; // sei que não vai acontecer
        }
    }
}