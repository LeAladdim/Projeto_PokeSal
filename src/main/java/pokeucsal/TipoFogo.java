package pokeucsal;

public class TipoFogo implements TipoPoke {

    @Override
    public String getNomeTipo() {
        return "Fogo";
    }

    @Override
    public double calcMult(TipoPoke defensor) {
        if (defensor.getNomeTipo().equals("Planta")) {
            return 2.0;
        } else if (defensor.getNomeTipo().equals("Água")) {
            return 0.5;
        }
        return 1.0;
    }

    @Override
    public void apBf(Pokemon usuario) {
        usuario.setAtk(usuario.getAtk() + 10);
        System.out.println("A chama cresce! O Ataque de " + usuario.getNome() + " aumentou para " +
            usuario.getAtk() + "!");
    }

    @Override
    public void apDb(Pokemon usuario) {
        usuario.setDef(usuario.getDef() - 5);
        System.out.println("Golpe de risco! A Defesa de " + usuario.getNome() + " diminuiu para " +
            usuario.getDef() + " devido ao cansaço!");
    }
}