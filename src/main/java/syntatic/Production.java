package syntatic;

public class Production {

    private String left;
    private String[] right;

    Production(String prod) {
        String[] elements = prod.split("=");
        left = elements[0].trim();
        right = elements[1].trim().split(" ");
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder(left);
        builder.append(" =");

        for (String el : right) {
            builder.append(" ").append(el);
        }

        return builder.toString();

    }

    String[] getRight() {
        return right;
    }

}
