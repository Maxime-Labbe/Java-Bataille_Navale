package bo;

public class Croiseur extends Bateau {

    private static String name = "croiseur";
    private static int nbCase = 4;
    private static String symbole = "C ";

    public Croiseur() {
        super(name, nbCase, symbole);
    }
}