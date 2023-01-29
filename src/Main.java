// 221RDB030 Darja Sedova 12. grupa

import java.util.*;
import static java.lang.Character.toUpperCase;

public class Main {
    public static Map<Character, Byte> dict = new HashMap<>();
    public static boolean run = true;
    public static boolean error = false;

    public static void main(String[] args) {
        dict.put('A', (byte) 0b00);
        dict.put('C', (byte) 0b01);
        dict.put('G', (byte) 0b10);
        dict.put('T', (byte) 0b11);

        Scanner sc = new Scanner(System.in);

        while (run) {
            String line = sc.nextLine();
            int index = line.length();
            if (line.contains(" "))
                index = line.indexOf(" ");

            String command = line.substring(0, index);

            switch (command) {
                case "about": {
                    System.out.println("221RDB030\nDarja Sedova\n12th group\n--------------");
                    break;
                }
                case "comp": {
                    String arguments = line.substring(index + 1);
                    compress(arguments);
                    break;
                }
                case "decomp": {
                    String[] strArguments = line.substring(index + 1).split(" ");
                    int[] intArguments = Arrays.stream(strArguments).mapToInt(Integer::parseInt).toArray();
                    byte[] arguments = new byte[intArguments.length];
                    for (int i = 0; i < intArguments.length; i++) {
                        if (intArguments[i] > 128 || intArguments[i] < -127) {
                            System.out.println("wrong command format");
                            error = true;
                            break;
                        }
                        arguments[i] = (byte) intArguments[i];
                    }
                    if (!error)
                        decompress(arguments);
                    break;
                }
                case "exit": {
                    run = false;
                    break;
                }
                case "": {
                    break;
                }
                default: {
                    System.out.println("wrong command format");
                    error = true;
                    break;
                }
            }
        }
        sc.close();

    }

    public static void compress(String str) {
        int length = str.length();
        if (str.length() % 4 != 0) {
            str = str.concat("a".repeat(4 - str.length() % 4));
        }
        char[] chars = str.toCharArray();
        byte bit = 0b0;
        byte[] encoded = new byte[(int) Math.ceil(str.length() / 4) + 1];
        encoded[0] = (byte) (length);

        for (int i = 1; i <= chars.length; i ++) {
            char letter = toUpperCase(chars[i - 1]);

            if (dict.containsKey(letter)) {
                bit <<= 2;
                bit += dict.get(letter);

            } else {
                error = true;
                System.out.println("wrong command format");
                break;
            }

            if (i % 4 == 0) {
                encoded[i / 4] = bit;
                bit = 0b0;
            }
        }
        if (!error) {
            for (byte item : encoded) {
                String element = Integer.toHexString(item & 0xff);
                System.out.print(element.toUpperCase() + " ");
            }
        }

    }
    public static void decompress(byte[] arguments) {
        byte byteCount = arguments[0];
        byte length = arguments[1];
        String hexString = "";
        String bitString = "";
        String letters = "";

        if (arguments.length - 1 != byteCount)
            System.out.println("wrong command format");

        for (int i = 1; i <= byteCount; i++) {
            // convert dec to hex
            String hexChar = Integer.toHexString(arguments[i] + 256 & 0xff);
            if (i > 1) hexString = hexString.concat(" ");
            hexString = hexString.concat(hexChar.toUpperCase());

            if (i > 1) {
                // convert int to 8-bit binary
                String bin = Integer.toBinaryString(arguments[i] & 0xff);
                while (bin.length() < 8 )
                    bin = "0" + bin;
                bitString = bitString.concat(bin);
            }
        }
        bitString = bitString.substring(0, length * 2);

        for (int i = 0; i < bitString.length(); i += 2) {
            String bits = bitString.charAt(i) + "" + bitString.charAt(i + 1);
            letters = getKey(bits, letters);
        }
        System.out.println(hexString);
        System.out.println(letters);
    }

    public static String getKey(String bits, String letters) {
        if (bits.charAt(0) == '0') bits = bits.substring(1);
        for (Character key : dict.keySet()) {
            String value = Integer.toBinaryString(dict.get(key));
            if (value.equals(bits)) {
               letters = letters.concat(String.valueOf(key));
            }
        }
        return letters;
    }

}
