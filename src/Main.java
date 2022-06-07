import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main {
    private static TreeMap<Integer, String> m_arabicToRoman = new TreeMap<>() {{
        put(1, "I");
        put(4, "IV");
        put(5, "V");
        put(9, "IX");
        put(10, "X");
        put(40, "XL");
        put(50, "L");
        put(90, "XC");
        put(100, "C");
        put(400, "CD");
        put(500, "D");
        put(900, "CM");
        put(1000, "M");
    }};

    private static HashMap<String, Integer> m_RomanToArabic = new HashMap<>() {{
        put("I", 1);
        put("II", 2);
        put("III", 3);
        put("IV", 4);
        put("V", 5);
        put("VI", 6);
        put("VII", 7);
        put("VIII", 8);
        put("IX", 9);
        put("X", 10);
        put("XI", 11);
        put("XII", 12);
        put("XIII", 13);
        put("XIV", 14);
        put("XV", 15);
        put("XVI", 16);
        put("XVII", 17);
        put("XVIII", 18);
        put("XIX", 19);
        put("XX", 20);
    }};

    public static String Convert(int number) throws Exception
    {
        if (number < 1)
        {
            throw new Exception("");
        }
        if (number > 3999)
        {
            throw new Exception("");
        }

        int arabicNumber = (int)(m_arabicToRoman.floorKey(number));

        if (arabicNumber == number)
        {
            return m_arabicToRoman.get(number).toString();
        }

        return m_arabicToRoman.get(arabicNumber).toString() + Convert(number - arabicNumber);
    }

    private enum Notation {
        ARABIC,
        ROMAN
    }

    public static void main(String[] args)
    {
        String input;
        while (true)
        {
            try
            {
                Scanner scanner = new Scanner(System.in);
                input = scanner.nextLine();
                if (input.equals("exit"))
                {
                    break;
                }
                String res = calc(input);
                System.out.println(res);
            }
            catch (Throwable e)
            {
                System.out.println("throws Exception");
            }
        }
    }

    public static String calc(String input) throws Exception
    {
        input = input.replaceAll("\\s+","");
        Pattern p = Pattern.compile("[\\+\\-\\*\\/]+");
        Matcher m = p.matcher(input);
        if (m.find() && IsSpecialChar(m.group()))
        {
            if (IsRomanNumbers(input.charAt(0)))
            {
                final int result = MakeMathOperation(input, m.group(), Notation.ROMAN);
                return Convert(result);
            }
            else
            {
                final String s = String.valueOf(MakeMathOperation(input, m.group(), Notation.ARABIC));
                return s;
            }
        }
        throw new Exception("");
    }

    private static boolean IsSpecialChar(String group)
    {
        return group.equals("+") || group.equals("-") || group.equals("/") || group.equals("*");
    }

    private static boolean IsRomanNumbers(char c)
    {
        return c == 'V' || c == 'I' || c == 'X';
    }

    private static int MakeMathOperation(String input, String action, Notation notation) throws Exception
    {
        int first_number, second_number;
        String first_substr = input.substring(0, input.indexOf(action));
        String second_substr = input.substring(input.indexOf(action) + 1);
        if (notation == Notation.ROMAN)
        {
            first_number = m_RomanToArabic.get(first_substr);
            second_number = m_RomanToArabic.get(second_substr);
        }
        else
        {
            first_number = Integer.parseInt(first_substr);
            second_number = Integer.parseInt(second_substr);
        }
        if (first_number > 10 || second_number > 10 || first_number < 1 || second_number < 1)
        {
            throw new Exception("");
        }
        switch (action) {
            case "+" -> {
                return first_number + second_number;
            }
            case "-" -> {
                return first_number - second_number;
            }
            case "*" -> {
                return first_number * second_number;
            }
            case "/" -> {
                return first_number / second_number;
            }
        }
        throw new Exception("");
    }
}