package go;

import java.util.Scanner;
import javax.swing.*;

import bo.*;
import Exceptions.*;
import ui.*;

public class Main {

    public static int longueur;

    public static int largeur;

    public static int nbBateauJ1;

    public static int nbBateauJ2;

    private static WindowGame fenetre;

    private static WindowGame fenetreAttack;

    public static boolean buttonPressed;

    public static void main(String[] args) throws BoatAlreadyOnPlateException, PlacementAlreadyOccupedException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez la longueur :");
        longueur = scanner.nextInt();
        System.out.println("Entrez la largeur :");
        largeur = scanner.nextInt();

        Bateau[] listBateauJ1 = new Bateau[5];
        listBateauJ1[0] = new PorteAvions();
        listBateauJ1[1] = new ContreTorpilleur();
        listBateauJ1[2] = new Torpilleur();
        listBateauJ1[3] = new SousMarin();
        listBateauJ1[4] = new Croiseur();

        Bateau[] listBateauJ2 = new Bateau[5];
        listBateauJ2[0] = new PorteAvions();
        listBateauJ2[1] = new ContreTorpilleur();
        listBateauJ2[2] = new Torpilleur();
        listBateauJ2[3] = new SousMarin();
        listBateauJ2[4] = new Croiseur();

        Plateau plateauJ1 = new Plateau();
        Plateau plateauJ2 = new Plateau();
        Plateau plateauJ1J2 = new Plateau();
        Plateau plateauJ2J1 = new Plateau();

        System.out.println("Placez les bateaux à la main ou de manière random (main ou random) :");
        String handOrRandom = "random";
        if (handOrRandom.toLowerCase().equals("main")) {
            plateauJ1.bateauInList(listBateauJ1);
            plateauJ2.bateauInList(listBateauJ2);
        } else if (handOrRandom.toLowerCase().equals("random")) {
            plateauJ1.bateauInListRandom(listBateauJ1);
            plateauJ2.bateauInListRandom(listBateauJ2);
        }
        System.out.println("En console ou graphique ?");
        String consoleOrGraph = "graphique";
        if (consoleOrGraph.toLowerCase().equals("console")) {
            /* Méthode console */
            boolean run = false;
            while (run == true) {
                boolean runJ1 = true;
                while (runJ1 == true && run == true) {
                    plateauJ1.afficher();
                    int[] shotJ1 = plateauJ1.shoot(plateauJ2J1);
                    if (plateauJ2J1.changePlateau(shotJ1[0], shotJ1[1], plateauJ2, listBateauJ2,
                            listBateauJ2.length) == false) {
                        runJ1 = false;
                    }
                    plateauJ2J1.afficher();
                    if (plateauJ1.victory(listBateauJ2, "joueur 1") == true) {
                        run = false;
                    }
                }
                boolean runJ2 = true;
                while (runJ2 == true && run == true) {
                    plateauJ2.afficher();
                    int[] shotJ2 = plateauJ2.shoot(plateauJ1J2);
                    if (plateauJ1J2.changePlateau(shotJ2[0], shotJ2[1], plateauJ1, listBateauJ1,
                            listBateauJ1.length) == false) {
                        runJ2 = false;
                    }
                    plateauJ1J2.afficher();
                    if (plateauJ2.victory(listBateauJ1, "joueur 2") == true) {
                        run = false;
                    }
                }
            }
            /* Méthode console */
        } else if (consoleOrGraph.toLowerCase().equals("graphique")) {
            /* Méthode graphique */
            fenetre = new WindowGame(plateauJ1.getListPlateau());
            fenetreAttack = new WindowGame(plateauJ2J1.getListPlateau());
            boolean run = true;
            while (run == true) {
                buttonPressed = false;
                boolean runJ1 = true;
                while (runJ1 == true && run == true) {
                    fenetre.setPlateau(plateauJ1.getListPlateau());
                    fenetreAttack.setPlateau(plateauJ2J1.getListPlateau());
                    while (buttonPressed == false) {
                        System.out.print("");
                        System.out.print("");
                        System.out.print("");
                        System.out.print("");
                        System.out.print("");
                        System.out.print("");
                        System.out.print("");
                        if (buttonPressed == true) {
                            break;
                        }
                    }
                    buttonPressed = false;
                    if (plateauJ2J1.changePlateau(fenetreAttack.getGameEvent().getIndex(), plateauJ2, listBateauJ2,
                            listBateauJ2.length) == false) {
                        runJ1 = false;
                    }
                    if (plateauJ1.victory(listBateauJ2, "joueur 1") == true) {
                        run = false;
                    }
                }
                buttonPressed = false;
                boolean runJ2 = true;
                while (runJ2 == true && run == true) {
                    fenetre.setPlateau(plateauJ2.getListPlateau());
                    fenetreAttack.setPlateau(plateauJ1J2.getListPlateau());
                    while (buttonPressed == false) {
                        System.out.print("");
                        System.out.print("");
                        System.out.print("");
                        System.out.print("");
                        System.out.print("");
                        System.out.print("");
                        System.out.print("");
                        if (buttonPressed == true) {
                            break;
                        }
                    }
                    buttonPressed = false;
                    if (plateauJ1J2.changePlateau(fenetreAttack.getGameEvent().getIndex(), plateauJ1, listBateauJ1,
                            listBateauJ1.length) == false) {
                        runJ2 = false;
                    }
                    if (plateauJ2.victory(listBateauJ1, "joueur 2") == true) {
                        run = false;
                    }
                }
            }
            /* Méthode graphique */
        }
        scanner.close();
    }
}