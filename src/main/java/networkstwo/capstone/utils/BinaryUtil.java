package networkstwo.capstone.utils;

public class BinaryUtil {
    public static String stringToBinary(String input) {
        StringBuilder binaryString = new StringBuilder();
        for (char c : input.toCharArray()) {
            String binaryChar = String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0');
            binaryString.append(binaryChar).append(" ");
        }
        return binaryString.toString().trim();
    }

    public static String binaryToString(String binaryInput) {
        StringBuilder textBuilder = new StringBuilder();
        String[] binaryChars = binaryInput.split(" ");

        for (String binaryChar : binaryChars) {
            char character = (char) Integer.parseInt(binaryChar, 2);
            textBuilder.append(character);
        }

        return textBuilder.toString();
    }
}
