import StringModule.StringOperationPOA;

public class StringOperationImpl extends StringOperationPOA {

    @Override
    public String reverseString(String input) {
        return new StringBuilder(input).reverse().toString();
    }

    @Override
    public String uppercaseString(String input) {
        return input.toUpperCase();
    }
}
