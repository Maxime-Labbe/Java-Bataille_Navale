package bo;

public class Torpilleur extends Bateau {

    private static String name = "torpilleur";
    private static int nbCase = 2;
    private static String symbole = "T ";

    public Torpilleur() {
        super(name, nbCase, symbole);
    }
}