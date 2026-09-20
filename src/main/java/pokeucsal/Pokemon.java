package pokeucsal;

public class Pokemon {
    private final String nome;

    private final TipoPoke tipo;

    private final int maxHp;

    private final Golpe[] golpes;

    private int atk;

    private int def;

    private int hp;

    private int spd;

    private int precisao = 100;

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

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getHp() {
        return hp;
    }

    public int getSpd() {
        return spd;
    }

    public void setSpd(int spd) {
        this.spd = spd;
    }

    public int getPrecisao() {
        return precisao;
    }

    public Golpe[] getGolpes() {
        return golpes;
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
            System.out.println(nome + " foi Paralisado e sua Velocidade Caiu!");
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
                nome + " Sofreu " + danoQueimadura + " de Dano por Queimadura! HP: " + this.hp +
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
                nome + " sofreu " + danoVeneno + " de Dano por Veneno Acumulado! HP: " + this.hp +
                    "/" + maxHp);
        }
    }

    public void diminuirPrecisao(int valor) {
        this.precisao -= valor;
        if (this.precisao < 30) {
            this.precisao = 30;
        }
        System.out.println("A Precisão de " + this.nome + " Caiu Para " + this.precisao + "%!");
    }

    public void dano(final int danoBruto) {
        // Calcula a mitigação normal de defesa
        final int danoEfetivo = Math.max(1, danoBruto - (this.def / 4));

        // Ferramenta Anti OTK
        final int danoMaximoPermitido = (int) (this.maxHp * 0.40);
        final int danoFinal = Math.min(danoEfetivo, Math.max(1, danoMaximoPermitido));

        this.hp -= danoFinal;
        if (this.hp < 0) {
            this.hp = 0;
        }
        System.out.println(
            this.nome + " recebeu " + danoFinal + " de dano! Vida restante: " + this.hp + "/" +
                this.maxHp);
    }

    public void curar(final double porcentagem) {
        if (porcentagem >= 1.0) {
            this.hp = this.maxHp;
        } else {
            this.hp += (int) (this.maxHp * porcentagem);
            if (this.hp > this.maxHp) {
                this.hp = this.maxHp;
            }
        }
        this.queimado = false;
        this.envenenado = false;
        this.paralisado = false;
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