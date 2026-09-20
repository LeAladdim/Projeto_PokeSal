package pokeucsal;

/**
 * Tipo Planta: forte contra Água e fraco contra Fogo.
 */
public class TipoPlanta implements TipoPoke {

    // Multiplicadores de tipo .
    private static final double VANTAGEM = 2.0;
    private static final double DESVANTAGEM = 0.5;
    private static final double NEUTRO = 1.0;

    // Valores dos golpes .
    private static final int BONUS_DEF_BUFF = 10;
    private static final int PENALIDADE_SPD_BUFF = 5;
    private static final int PENALIDADE_SPD_DEBUFF = 5;

    @Override
    public String getNomeTipo() {
        return "Planta";
    }

    @Override
    public double calcMult(final TipoPoke defensor) {
        if (defensor.getNomeTipo().equals("Água")) {
            return VANTAGEM;
        } else if (defensor.getNomeTipo().equals("Fogo")) {
            return DESVANTAGEM;
        }
        return NEUTRO;
    }

    @Override
    public void apBf(final Pokemon usuario) {
        usuario.setDef(usuario.getDef() + BONUS_DEF_BUFF);
        usuario.setSpd(usuario.getSpd() - PENALIDADE_SPD_BUFF);
        System.out.println(
            "As Raízes Te Envolvem... " + usuario.getNome() + " Aumentou Sua Defesa Para "
                + usuario.getDef() + " e a Sua Velocidade Diminuiu Para " + usuario.getSpd()
                + " Devido as Grossas Raízes!");
        Batalha.pausar(Batalha.PAUSA_CURTA_MS);
    }

    @Override
    public void apDb(final Pokemon alvo) {
        // CORREÇÃO: Afeta exclusivamente a Velocidade
        alvo.setSpd(alvo.getSpd() - PENALIDADE_SPD_DEBUFF);
        System.out.println(
            "O Inimigo Está Caindo na Areia Movediça! A Velocidade de " + alvo.getNome()
                + " Diminuiu Para "
                + alvo.getSpd() + " Devido ao Pavor!");
        Batalha.pausar(Batalha.PAUSA_CURTA_MS);
    }
}