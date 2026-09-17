package pokeucsal;

public class Pokemon {
    private String tipo;
    private String nome;
    private int atk;
    private int hp;
    private int def;
    private int spd;

    public Pokemon(String nome, String tipo, int atk, int def, int hp, int spd) {
        this.nome = nome;
        this.tipo = tipo;
        this.atk = atk;
        this.def = def;
        this.hp = hp;
        this.spd = spd;
    }

    public String getNome() {
        return nome;
    }
    public String getTipo() {
        return tipo;
    }
    public int getAtk() {
        return atk;
    }
    public int getHp() {
        return hp;
    }
    public int getDef() {
        return def;
    }
    public int getSpd() {
        return spd;
    }

    public void exibirSts(){
        System.out.println("--- Status de " + nome + " ---");
        System.out.println("Tipo: " + tipo);
        System.out.println("Vida: " + hp);
        System.out.println("Ataque: " + atk);
        System.out.println("Defesa: " + def);
        System.out.println("Velocidade: " + spd);
        System.out.println("--------------------------");
    }
}


