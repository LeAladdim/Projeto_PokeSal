package pokeucsal;

public class TipoPlanta implements TipoPoke {

    @Override
    public String getNomeTipo() {
        return "Planta";
    }

    @Override
    public double calcMult(TipoPoke defensor) {
        if (defensor.getNomeTipo().equals("Água")) {
            return 2.0;
        } else if (defensor.getNomeTipo().equals("Fogo")) {
            return 0.5;
        }
        return 1.0;
    }

    @Override
    public void apBf(Pokemon usuario) {
        usuario.setSpd(usuario.getDef() + 10);
        usuario.setDef(usuario.getSpd() - 5);
        System.out.println(
            "As Raízes Te Envolvem... " + usuario.getNome() + " aumentou para " +
                usuario.getDef() + " e a Velocidade Diminuiu Para " + usuario.getSpd() +
                " Devido as Grossas Raízes!");
        Batalha.pausar(1500);
    }

    @Override
    public void apDb(final Pokemon alvo) {
        alvo.setDef(alvo.getSpd() - 5);
        System.out.println(
            "O Inimigo Está Caindo na Areia Movediça! A Velocidade de " + alvo.getNome() +
                " " +
                "diminuiu para " +
                alvo.getSpd() + " Devido ao Pavor!");
        Batalha.pausar(1500);
    }
}