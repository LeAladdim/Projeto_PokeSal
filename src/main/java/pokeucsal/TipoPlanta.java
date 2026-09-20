package pokeucsal;

public class TipoPlanta implements TipoPoke {

    @Override
    public String getNomeTipo() {
        return "Planta";
    }

    @Override
    public double calcMult(final TipoPoke defensor) {
        if (defensor.getNomeTipo().equals("Água")) {
            return 2.0;
        } else if (defensor.getNomeTipo().equals("Fogo")) {
            return 0.5;
        }
        return 1.0;
    }

    @Override
    public void apBf(final Pokemon usuario) {
        usuario.setDef(usuario.getDef() + 10);
        usuario.setSpd(usuario.getSpd() - 5);
        System.out.println(
            "As Raízes Te Envolvem... " + usuario.getNome() + " Aumentou Sua Defesa Para " +
                usuario.getDef() + " e a Sua Velocidade Diminuiu Para " + usuario.getSpd() +
                " Devido as Grossas Raízes!");
        Batalha.pausar(1500);
    }

    @Override
    public void apDb(final Pokemon alvo) {
        // CORREÇÃO: Afeta exclusivamente a Velocidade
        alvo.setSpd(alvo.getSpd() - 5);
        System.out.println(
            "O Inimigo Está Caindo na Areia Movediça! A Velocidade de " + alvo.getNome() +
                " Diminuiu Para " +
                alvo.getSpd() + " Devido ao Pavor!");
        Batalha.pausar(1500);
    }
}