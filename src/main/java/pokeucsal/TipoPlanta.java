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
        usuario.setDef(usuario.getDef() + 10);
        System.out.println(
            "Casca mais grossa! A Defesa de " + usuario.getNome() + " aumentou para " +
                usuario.getDef() + "!");
        Batalha.pausar(1500);
    }

    @Override
    public void apDb(Pokemon usuario) {
        usuario.setSpd(usuario.getSpd() - 5);
        System.out.println(
            "Raizes pesadas envolvem o alvo... A Velocidade de " + usuario.getNome() + " " +
                "diminuiu para " + usuario.getSpd() + "!");
        Batalha.pausar(1500);
    }
}