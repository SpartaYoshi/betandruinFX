package exceptions;

public class NotEnoughMoneyException extends Exception {

    public NotEnoughMoneyException() {
        super();
    }

    /**This exception is triggered if the user wants to bet an amount of money larger than the available he/she has
     *@param arg String of the exception
     */
    public NotEnoughMoneyException(String arg) {
        super(arg);
    }
}
