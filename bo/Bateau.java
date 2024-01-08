package bo;

public class Bateau {

    private String name;
    private int nbCase;
    private String symbole;
    private int state;

    public Bateau(String name, int nbCase, String symbole) {
        this.name = name;
        this.nbCase = nbCase;
        this.symbole = symbole;
        this.state = nbCase;
    }

    public void setNbCase(int nbCase) {
        this.nbCase = nbCase;
    }

    public int getNbCase() {
        return nbCase;
    }

    public String getSymbole() {
        return symbole;
    }

    public String getName() {
        return name;
    }

    public int getState() {
        return state;
    }

    public void decrementState() {
        state -= 1;
    }

    public boolean drowned() {
        if (state == 0) {
            return true;
        }
        return false;
    }
}