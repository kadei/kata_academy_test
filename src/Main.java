import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void print_instruction() {
        System.out.println("type arabic [1..10] or roman [I..X] numbers and use only [+-*/] arithmetic operations.");
        System.out.println("examples: 1 + 2; VII - IV");
    }

    public static void main(String[] args) {
        print_instruction();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String s = reader.readLine();
            if (s != null)
                System.out.println(calc(s.trim()));

        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    public static final Pattern ARABIC = Pattern.compile("^(10|\\d) ([+\\-*/]) (10|\\d)$");
    public static final Pattern ROMAN = Pattern.compile("^(VI{0,3}|IV|IX|X|I{1,3}) ([+\\-*/]) (VI{0,3}|IV|IX|X|I{1,3})$");
    public static final HashMap<String, BiFunction<Integer, Integer, Integer>> OPERATIONS;

    public static final HashMap<Character, Integer> ROMAN_NUMBERS;

    record Pair(int val, String str) {
    }

    public static final ArrayList<Pair> ARABIC_ROMAN;

    static {
        OPERATIONS = new HashMap<>(4);
        OPERATIONS.put("+", (n1, n2) -> n1 + n2);
        OPERATIONS.put("-", (n1, n2) -> n1 - n2);
        OPERATIONS.put("*", (n1, n2) -> n1 * n2);
        OPERATIONS.put("/", (n1, n2) -> {
            if (n2 == 0)
                throw new IllegalArgumentException("division by zero");

            return n1 / n2;
        });

        ARABIC_ROMAN = new ArrayList<>(10);
        ARABIC_ROMAN.add(new Pair(100, "C"));
        ARABIC_ROMAN.add(new Pair(90, "XC"));
        ARABIC_ROMAN.add(new Pair(50, "L"));
        ARABIC_ROMAN.add(new Pair(40, "XL"));
        ARABIC_ROMAN.add(new Pair(10, "X"));
        ARABIC_ROMAN.add(new Pair(9, "IX"));
        ARABIC_ROMAN.add(new Pair(5, "V"));
        ARABIC_ROMAN.add(new Pair(4, "IV"));
        ARABIC_ROMAN.add(new Pair(1, "I"));

        ROMAN_NUMBERS = new HashMap<>();
        ROMAN_NUMBERS.put('I', 1);
        ROMAN_NUMBERS.put('V', 5);
        ROMAN_NUMBERS.put('X', 10);
        ROMAN_NUMBERS.put('L', 50);
        ROMAN_NUMBERS.put('C', 100);
        ROMAN_NUMBERS.put('D', 500);
        ROMAN_NUMBERS.put('M', 1000);
    }

    public static String calc(String statement) {
        Matcher matcher = ARABIC.matcher(statement);
        if (matcher.find()) {
            int n1 = Integer.parseInt(matcher.group(1));
            int n2 = Integer.parseInt(matcher.group(3));
            return String.valueOf(OPERATIONS.get(matcher.group(2)).apply(n1, n2));
        }

        matcher = ROMAN.matcher(statement);
        if (matcher.find()) {
            int n1 = parseRoman(matcher.group(1));
            int n2 = parseRoman(matcher.group(3));
            return toRoman(OPERATIONS.get(matcher.group(2)).apply(n1, n2));
        }
        throw new IllegalArgumentException("isn't valid arithmetic statement");
    }

    public static int parseRoman(String roman) {
        Integer prevN = ROMAN_NUMBERS.get(roman.charAt(0));
        if (prevN == null)
            throw new IllegalArgumentException("isn't a roman number");

        int n = prevN;

        for (int i = 1; i < roman.length(); ++i) {
            Integer rn = ROMAN_NUMBERS.get(roman.charAt(i));
            if (rn == null)
                throw new IllegalArgumentException("isn't a roman number");

            if (prevN < rn) {
                n -= prevN;
                n += rn - prevN;
            } else {
                n += rn;
            }

            prevN = rn;
        }
        return n;
    }

    public static String toRoman(int n) {
        if (n < 0)
            throw new IllegalArgumentException("Roman numbers don't have negative numbers");

        if (n == 0)
            throw new IllegalArgumentException("Roman numbers don't have 0");

        if (n > 100)
            throw new IllegalArgumentException("a too large number");

        StringBuilder sb = new StringBuilder();
        for (var p : ARABIC_ROMAN) {
            while (n >= p.val()) {
                n -= p.val();
                sb.append(p.str());
            }
        }
        return sb.toString();
    }
}
