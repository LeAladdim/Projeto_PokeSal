package pokeucsal;

public class Pokemon {
    private final String nome;

    private final TipoPoke tipo;

    private int atk;

    private int def;

    private int hp;

    private final int maxHp;

    private int spd;

    private int precisao = 100;

    private final Golpe[] golpes;

    private boolean queimado = false;

    private boolean envenenado = false;

    private boolean paralisado = false;

    private int turnosEnvenenado = 0;

    public Pokemon(String nome, TipoPoke tipo, int atk, int def, int hp, int spd, Golpe[] golpes) {
        this.nome = nome;
        this.tipo = tipo;
        this.atk = atk;
        this.def = def;
        this.hp = hp;
        this.maxHp = hp;
        this.spd = spd;
        this.golpes = golpes;
    }

    public String getNome() {
        return nome;
    }

    public TipoPoke getTipo() {
        return tipo;
    }

    public int getAtk() {
        return atk;
    }

    public int getDef() {
        return def;
    }

    public int getHp() {
        return hp;
    }

    public int getSpd() {
        return spd;
    }

    public int getPrecisao() {
        return precisao;
    }

    public Golpe[] getGolpes() {
        return golpes;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public void setSpd(int spd) {
        this.spd = spd;
    }

    public boolean isQueimado() {
        return queimado;
    }

    public void setQueimado(boolean queimado) {
        this.queimado = queimado;
        if (queimado) {
            System.out.println(nome + " foi queimado!");
        }
    }

    public boolean isEnvenenado() {
        return envenenado;
    }

    public void setEnvenenado(boolean envenenado) {
        this.envenenado = envenenado;
        if (envenenado) {
            this.turnosEnvenenado = 0;
            System.out.println(nome + " foi envenenado!");
        }
    }

    public boolean isParalisado() {
        return paralisado;
    }

    public void setParalisado(boolean paralisado) {
        this.paralisado = paralisado;
        if (paralisado) {
            this.spd = (int) (this.spd * 0.75); // Reduz SPD em 25%
            System.out.println(nome + " foi paralisado e sua velocidade caiu!");
        }
    }

    public void processarStatusFimDeTurno() {
        if (queimado) {
            final int danoQueimadura = Math.max(1, maxHp / 16);
            this.hp -= danoQueimadura;
            if (this.hp < 0) {
                this.hp = 0;
            }
            System.out.println(
                nome + " sofreu " + danoQueimadura + " de dano por queimadura! HP: " + this.hp +
                    "/" + maxHp);
        }
        if (envenenado) {
            turnosEnvenenado++;
            final int danoVeneno = Math.max(1, (maxHp / 16) * turnosEnvenenado);
            this.hp -= danoVeneno;
            if (this.hp < 0) {
                this.hp = 0;
            }
            System.out.println(
                nome + " sofreu " + danoVeneno + " de dano por veneno acumulado! HP: " + this.hp +
                    "/" + maxHp);
        }
    }

    public void diminuirPrecisao(int valor) {
        this.precisao -= valor;
        if (this.precisao < 30) {
            this.precisao = 30;
        }
        System.out.println("A Precisão de " + this.nome + " caiu para " + this.precisao + "%!");
    }

    public void dano(int danoBruto) {
        final int danoEfetivo = Math.max(0, danoBruto - this.def);
        this.hp -= danoEfetivo;
        if (this.hp < 0) {
            this.hp = 0;
        }
        System.out.println(
            this.nome + " recebeu " + danoEfetivo + " de dano! Vida restante: " + this.hp + "/" +
                maxHp);
    }

    public void curar(double porcentagem) {
        final int valorCura = (int) (this.maxHp * porcentagem);
        this.hp += valorCura;
        if (this.hp > this.maxHp) {
            this.hp = this.maxHp;
        }
        System.out.println(this.nome + " recuperou vida! HP atual: " + this.hp + "/" + maxHp);
    }

    public void exibirSts() {
        System.out.println("--- Status de " + nome + " ---");
        System.out.println("Tipo: " + tipo.getNomeTipo());
        System.out.println("Vida: " + hp + "/" + maxHp);
        System.out.println("Ataque: " + atk);
        System.out.println("Defesa: " + def);
        System.out.println("Velocidade: " + spd);
        System.out.println("--------------------------");
    }
}