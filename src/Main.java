import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final char[][] POLYBIUS_SQUARE = {
            {'A', 'B', 'C', 'D', 'E'},
            {'F', 'G', 'H', 'I', 'K'},
            {'L', 'M', 'N', 'O', 'P'},
            {'Q', 'R', 'S', 'T', 'U'},
            {'V', 'W', 'X', 'Y', 'Z'}
    };

    private static final Map<Character, String> ENCODE_MAP = new HashMap<>();
    private static final Map<String, Character> DECODE_MAP = new HashMap<>();

    static {
        for (int row = 0; row < POLYBIUS_SQUARE.length; row++) {
            for (int col = 0; col < POLYBIUS_SQUARE[row].length; col++) {
                char letter = POLYBIUS_SQUARE[row][col];
                String key = "" + (row + 1) + (col + 1);
                ENCODE_MAP.put(letter, key);
                DECODE_MAP.put(key, letter);
            }
        }
    }

    public static String encrypt(String text) {
        text = text.toUpperCase().replace("J", "I").replace("NULL", " "); // "J" əvəzinə "I", "null" isə boşluq ilə əvəz edilir
        StringBuilder encrypted = new StringBuilder();

        for (char c : text.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                encrypted.append(ENCODE_MAP.get(c)).append(" ");
            } else if (Character.isWhitespace(c)) {
                encrypted.append(" "); // Boşluqlar olduğu kimi saxlanılır
            } else {
                encrypted.append(c); // Digər simvollar saxlanılır
            }
        }

        return encrypted.toString().trim();
    }

    public static String decrypt(String code) {
        StringBuilder decrypted = new StringBuilder();
        String[] parts = code.split(" ");

        for (String part : parts) {
            if (DECODE_MAP.containsKey(part)) {
                char decodedChar = DECODE_MAP.get(part);
                if (decodedChar == 'I') {
                    decrypted.append("I/J"); // "I" və "J" seçimlərini əlavə et
                } else {
                    decrypted.append(decodedChar);
                }
            } else if (part.isBlank()) {
                decrypted.append(" "); // Boşluq simvolu əlavə edilir
            } else {
                decrypted.append(part); // Digər simvollar saxlanılır
            }
        }

        return decrypted.toString().replaceAll(" +", " "); // Artıq boşluqlar silinir
    }

    public static void main(String[] args) {
        String text = "neurotech null final ";
        System.out.println("Original Text: " + text);

        String encrypted = encrypt(text);
        System.out.println("Encrypted Text: " + encrypted);

        String decrypted = decrypt(encrypted);
        System.out.println("Decrypted Text: " + decrypted);
    }
}
