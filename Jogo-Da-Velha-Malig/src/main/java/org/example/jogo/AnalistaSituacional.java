package org.example.jogo;

public class AnalistaSituacional
{
    private Tabuleiro tabDoJogo;

    public AnalistaSituacional (Tabuleiro tab) throws Exception
    {
        if (tab == null) {
            throw new Exception("Tabuleiro nao pode ser nulo");
        }

        this.tabDoJogo = tab;
    }

    public Marca getMarcaDoVencedor ()
    {
        try {

            for (int l = 0; l < 3; l++) {
                Marca m = tabDoJogo.getMarcaNaPosicao(l, 0);
                if (m != null &&
                        m.equals(tabDoJogo.getMarcaNaPosicao(l, 1)) &&
                        m.equals(tabDoJogo.getMarcaNaPosicao(l, 2))) {
                    return m;
                }
            }


            for (int c = 0; c < 3; c++) {
                Marca m = tabDoJogo.getMarcaNaPosicao(0, c);
                if (m != null &&
                        m.equals(tabDoJogo.getMarcaNaPosicao(1, c)) &&
                        m.equals(tabDoJogo.getMarcaNaPosicao(2, c))) {
                    return m;
                }
            }


            Marca mDiag1 = tabDoJogo.getMarcaNaPosicao(0, 0);
            if (mDiag1 != null &&
                    mDiag1.equals(tabDoJogo.getMarcaNaPosicao(1, 1)) &&
                    mDiag1.equals(tabDoJogo.getMarcaNaPosicao(2, 2))) {
                return mDiag1;
            }


            Marca mDiag2 = tabDoJogo.getMarcaNaPosicao(0, 2);
            if (mDiag2 != null &&
                    mDiag2.equals(tabDoJogo.getMarcaNaPosicao(1, 1)) &&
                    mDiag2.equals(tabDoJogo.getMarcaNaPosicao(2, 0))) {
                return mDiag2;
            }
        } catch (Exception e) {

        }

        return null;
    }

    private boolean tabuleiroCheio ()
    {
        try {
            for (int l = 0; l < 3; l++) {
                for (int c = 0; c < 3; c++) {

                    if (!tabDoJogo.haMarcaNaPosicao(l, c)) {
                        return false;
                    }
                }
            }
        } catch (Exception e) {

        }
        return true;
    }

    public boolean fimDeJogo ()
    {

        return getMarcaDoVencedor() != null || tabuleiroCheio();
    }

    @Override
    public String toString ()
    {
        String status = "Jogo em andamento";
        Marca vencedor = getMarcaDoVencedor();

        if (vencedor != null) {
            status = "Vencedor: " + vencedor;
        } else if (tabuleiroCheio()) {
            status = "Empate (Velha)";
        }

        return "Situação: " + status + "\n" + tabDoJogo.toString();
    }

    @Override
    public boolean equals (Object obj)
    {
        if (obj == this) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        AnalistaSituacional outro = (AnalistaSituacional) obj;


        return this.tabDoJogo.equals(outro.tabDoJogo);
    }

    @Override
    public int hashCode ()
    {
        int result = 1;

        result = 7 * result + this.tabDoJogo.hashCode();
        return result;
    }
}
