package bo;

import java.util.Scanner;
import java.util.Random;
import go.Main;
import Exceptions.*;

public class Plateau {

    private String[] listPlateau;

    public Plateau() {
        this.listPlateau = new String[Main.longueur * Main.largeur];
        initialisePlateau();
    }

    public String[] getListPlateau() {
        return listPlateau;
    }

    public void initialisePlateau() {
        for (int i = 0; i < listPlateau.length; i++) {
            listPlateau[i] = ". ";
        }
    }

    public void bateauInListRandom(Bateau[] listBateau) {
        String[] coloumnOrLine = { "colonne", "ligne" };
        String[] bateau = { "contretorpilleur", "croiseur", "porteavions", "sousmarin", "torpilleur" };
        Random random = new Random();

        for (int i = 0; i < listBateau.length; i++) {
            String randomColoumnOrLine = "";
            String randomBateau = "";
            int indexBateau = 0;

            while (randomBateau == "" || randomColoumnOrLine == "") {
                randomColoumnOrLine = coloumnOrLine[random.nextInt(coloumnOrLine.length)];
                indexBateau = random.nextInt(bateau.length);
                randomBateau = bateau[indexBateau];
            }

            bateau[indexBateau] = "";
            Bateau bato = listBateau[0];
            for (int k = 0; k < listBateau.length; k++) {
                if (randomBateau.equals(listBateau[k].getName())) {
                    bato = listBateau[k];
                }
            }

            if (randomColoumnOrLine.equals("ligne")) {
                int ligne = random.nextInt(Main.largeur - bato.getNbCase());
                int colonne = random.nextInt(Main.longueur - bato.getNbCase());
                while (placeUsedRandom(ligne, colonne, randomColoumnOrLine, bato) == true) {
                    ligne = random.nextInt(Main.largeur - bato.getNbCase());
                    colonne = random.nextInt(Main.longueur - bato.getNbCase());
                }
                for (int j = colonne; j != bato.getNbCase() + colonne; j++) {
                    listPlateau[ligne * Main.longueur + j] = bato.getSymbole();
                }
            } else if (randomColoumnOrLine.equals("colonne")) {
                int ligne = random.nextInt(Main.largeur - bato.getNbCase());
                int colonne = random.nextInt(Main.longueur - bato.getNbCase());
                while (placeUsedRandom(ligne, colonne, randomColoumnOrLine, bato) == true) {
                    ligne = random.nextInt(Main.largeur - bato.getNbCase());
                    colonne = random.nextInt(Main.longueur - bato.getNbCase());
                }
                for (int j = ligne; j != bato.getNbCase() + ligne; j++) {
                    listPlateau[j * Main.longueur + colonne] = bato.getSymbole();
                }
            }
        }
    }

    public void bateauInList(Bateau[] listBateau) throws BoatAlreadyOnPlateException, PlacementAlreadyOccupedException {
        initialisePlateau();
        for (int i = 0; i < listBateau.length; i++) {
            Scanner sc = new Scanner(System.in);
            Bateau bato;
            bato = listBateau[0];
            System.out.println("Liste des bateaux : ");
            for (Bateau item : listBateau) {
                System.out.print(item.getName() + " ");
            }

            System.out.println();
            System.out.println("Quel bateau voulez vous placez ? ");
            String bat = sc.nextLine();
            bat.toLowerCase();
            for (int k = 0; k < listBateau.length; k++) {
                if (bat.equals(listBateau[k].getName())) {
                    bato = listBateau[k];
                }
            }
            symboleInPlateau(bato.getSymbole());

            System.out.println("Placer le bateau sur une colonne ou une ligne ? ");
            String coloumnOrLine = sc.nextLine();

            System.out.println("Quelle " + coloumnOrLine + " ?");
            int valeurFixe = sc.nextInt() - 1;

            if (coloumnOrLine.toLowerCase().equals("ligne")) {
                System.out.println("Point de départ du bateau sur la ligne (Ce sera le point le plus à gauche) :");
                int premierPoint = sc.nextInt() - 1;
                for (int j = premierPoint; j != bato.getNbCase() + premierPoint; j++) {
                    placeUsed(valeurFixe * Main.longueur + j);
                    listPlateau[valeurFixe * Main.longueur + j] = bato.getSymbole();
                }
            }

            else if (coloumnOrLine.toLowerCase().equals("colonne")) {
                System.out.println("Point de départ du bateau sur la colonne (Ce sera le point le plus haut) :");
                int premierPoint = sc.nextInt() - 1;
                for (int j = premierPoint; j != bato.getNbCase() + premierPoint; j++) {
                    placeUsed(j * Main.longueur + valeurFixe);
                    listPlateau[j * Main.longueur + valeurFixe] = bato.getSymbole();
                }
            }
            afficher();
        }
    }

    public void afficher() {
        for (int i = 0; i < Main.largeur; i++) {
            for (int j = 0; j < Main.longueur; j++) {
                System.out.print(listPlateau[i * Main.largeur + j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean symboleInPlateau(String symbole) throws BoatAlreadyOnPlateException {
        for (String item : listPlateau) {
            if (item.equals(symbole)) {
                throw new BoatAlreadyOnPlateException();
            }
        }
        return false;
    }

    public boolean placeUsed(int index) throws PlacementAlreadyOccupedException {
        if (listPlateau[index] != ". ") {
            throw new PlacementAlreadyOccupedException();
        }
        return false;
    }

    public boolean placeUsedRandom(int ligne, int colonne, String coloumnOrLine, Bateau bateau) {
        if (coloumnOrLine.equals("ligne"))
            for (int i = colonne; i < bateau.getNbCase() + colonne; i++) {
                if (listPlateau[ligne * Main.longueur + i] != ". ") {
                    return true;
                }
            }
        else if (coloumnOrLine.equals("colonne")) {
            for (int i = ligne; i < bateau.getNbCase() + ligne; i++) {
                if (listPlateau[i * Main.longueur + colonne] != ". ") {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean changePlateau(int ligne, int colonne, Plateau otherPlayerPlateau, Bateau[] listBateau,
            int nbBateau) {
        switch (otherPlayerPlateau.getListPlateau()[ligne * Main.longueur + colonne]) {
            case ". ":
                listPlateau[ligne * Main.longueur + colonne] = "W ";
                otherPlayerPlateau.getListPlateau()[ligne * Main.longueur + colonne] = "W ";
                return false;

            case "C ":
                listPlateau[ligne * Main.longueur + colonne] = "T ";
                otherPlayerPlateau.getListPlateau()[ligne * Main.longueur
                        + colonne] = otherPlayerPlateau.getListPlateau()[ligne * Main.longueur + colonne].toLowerCase();
                listBateau[4].decrementState();
                nbBateau -= 1;
                return true;

            case "T ":
                listPlateau[ligne * Main.longueur + colonne] = "T ";
                otherPlayerPlateau.getListPlateau()[ligne * Main.longueur
                        + colonne] = otherPlayerPlateau.getListPlateau()[ligne * Main.longueur + colonne].toLowerCase();
                listBateau[2].decrementState();
                nbBateau -= 1;
                return true;

            case "P ":
                listPlateau[ligne * Main.longueur + colonne] = "T ";
                otherPlayerPlateau.getListPlateau()[ligne * Main.longueur
                        + colonne] = otherPlayerPlateau.getListPlateau()[ligne * Main.longueur + colonne].toLowerCase();
                listBateau[0].decrementState();
                nbBateau -= 1;
                return true;

            case "U ":
                listPlateau[ligne * Main.longueur + colonne] = "T ";
                otherPlayerPlateau.getListPlateau()[ligne * Main.longueur
                        + colonne] = otherPlayerPlateau.getListPlateau()[ligne * Main.longueur + colonne].toLowerCase();
                listBateau[1].decrementState();
                nbBateau -= 1;
                return true;

            case "S ":
                listPlateau[ligne * Main.longueur + colonne] = "T ";
                otherPlayerPlateau.getListPlateau()[ligne * Main.longueur
                        + colonne] = otherPlayerPlateau.getListPlateau()[ligne * Main.longueur + colonne].toLowerCase();
                listBateau[3].decrementState();
                nbBateau -= 1;
                return true;

            default:

                return false;
        }
    }

    public boolean victory(Bateau[] listBateau, String joueur) {
        int coule = 0;
        for (int i = 0; i < listBateau.length; i++) {
            if (listBateau[i].getState() == 0) {
                coule += 1;
            }
        }
        if (coule == listBateau.length) {
            System.out.println("Le " + joueur + " a gagné !");
            return true;
        }
        return false;
    }

    public int[] shoot(Plateau otherPlateau) {
        Scanner scanner = new Scanner(System.in);
        int[] shots = new int[2];
        otherPlateau.afficher();
        shots[0] = -1;
        shots[1] = -1;
        while (!(rightPlace(shots[0], shots[1]))) {
            System.out.println("Entrez la ligne :");
            shots[0] = scanner.nextInt() - 1;
            System.out.println("Entrez la colonne :");
            shots[1] = scanner.nextInt() - 1;
        }
        return shots;
    }

    public boolean rightPlace(int ligne, int colonne) {
        if ((0 <= ligne && ligne < Main.longueur)
                && (0 <= colonne && colonne < Main.largeur)) {
            return true;
        }
        return false;
    }

    public boolean changePlateau(int index, Plateau otherPlayerPlateau, Bateau[] listBateau, int nbBateau) {
        switch (otherPlayerPlateau.getListPlateau()[index]) {
            case ". ":
                this.listPlateau[index] = "W ";
                otherPlayerPlateau.getListPlateau()[index] = "W ";
                return false;

            case "C ":
                this.listPlateau[index] = "T ";
                otherPlayerPlateau.getListPlateau()[index] = otherPlayerPlateau.getListPlateau()[index].toLowerCase();
                listBateau[4].decrementState();
                nbBateau -= 1;
                return true;

            case "T ":
                this.listPlateau[index] = "T ";
                otherPlayerPlateau.getListPlateau()[index] = otherPlayerPlateau.getListPlateau()[index].toLowerCase();
                listBateau[2].decrementState();
                nbBateau -= 1;
                return true;

            case "P ":
                this.listPlateau[index] = "T ";
                otherPlayerPlateau.getListPlateau()[index] = otherPlayerPlateau.getListPlateau()[index].toLowerCase();
                listBateau[0].decrementState();
                nbBateau -= 1;
                return true;

            case "U ":
                this.listPlateau[index] = "T ";
                otherPlayerPlateau.getListPlateau()[index] = otherPlayerPlateau.getListPlateau()[index].toLowerCase();
                listBateau[1].decrementState();
                nbBateau -= 1;
                return true;

            case "S ":
                this.listPlateau[index] = "T ";
                otherPlayerPlateau.getListPlateau()[index] = otherPlayerPlateau.getListPlateau()[index].toLowerCase();
                listBateau[3].decrementState();
                nbBateau -= 1;
                return true;

            default:

                return false;
        }
    }
}
