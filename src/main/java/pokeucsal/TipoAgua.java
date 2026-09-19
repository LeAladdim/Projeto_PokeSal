package pokeucsal;

public class TipoAgua implements TipoPoke {

    @Override
    public String getNomeTipo() {
        return "Água";
    }

    @Override
    public double calcMult(TipoPoke defensor) {
        if (defensor.getNomeTipo().equals("Fogo")) {
            return 2.0;
        } else if (defensor.getNomeTipo().equals("Planta")) {
            return 0.5;
        }
        return 1.0;
    }

    @Override
    public void apBf(Pokemon usuario) {
        usuario.setSpd(usuario.getSpd() + 10);
        System.out.println(
            "Correnteza ágil! A Velocidade de " + usuario.getNome() + " aumentou para " +
                usuario.getSpd() + "!");
    }

    @Override
    public void apDb(Pokemon usuario) {
        usuario.setAtk(usuario.getAtk() - 5);
        System.out.println(
            "Esforço exaustivo! O Ataque de " + usuario.getNome() + " diminuiu para " +
                usuario.getAtk() + "!");
    }
}