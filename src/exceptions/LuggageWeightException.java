package exceptions;

public class LuggageWeightException extends Exception {
    public LuggageWeightException(Double extra_weight) {
        super("This particular luggage is too heavy (" + extra_weight + " kg over the limit)");
    }
}
