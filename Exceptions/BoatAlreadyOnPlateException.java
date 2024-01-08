package Exceptions;

public class BoatAlreadyOnPlateException extends Exception {
    public BoatAlreadyOnPlateException() {
        super("This boat is already on the plate !");
    }
}