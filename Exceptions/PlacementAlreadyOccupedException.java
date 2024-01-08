package Exceptions;

public class PlacementAlreadyOccupedException extends Exception {

    public PlacementAlreadyOccupedException() {
        super("This placement is already occuped by an other boat !");
    }
}
