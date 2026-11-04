package org.example.jogador;

import org.example.jogo.Marca;
import org.example.jogo.Tabuleiro;

public interface Jogador
{
    // faça
    public abstract Marca getMarca ();
    public abstract void facaSuaJogada ();
    
    // Não é necessário implementar:
    // public abstract void vcGanhou ();
    // public abstract void vcPerdeu ();
    // public abstract void deuVelha ();
    // public abstract boolean querMaisUmaPartida ();
    // Pois apenas o JogadorHumano precisa dessas implementações
    // JogadorAutomatico não precisa.
}