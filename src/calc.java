import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class calc {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Введите выражение: ");
            String input = scanner.nextLine();

            ExpressionEvaluator expressionEvaluator = new ExpressionEvaluator();
            String result = expressionEvaluator.evaluateExpressionAndFormatResult(input);

            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}

class ExpressionEvaluator {
    public String evaluateExpressionAndFormatResult(String input) throws Exception {
        CalculatorLogic calculatorLogic = new CalculatorLogic();
        String[] parts = input.split("\\s*[-+*/]\\s*");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Неверный формат выражения");
        }

        String operand1 = parts[0];
        String operand2 = parts[1];
        String operator = input.replaceAll("[^\\-+*/]", "");

        if ((calculatorLogic.isRoman(operand1) && !calculatorLogic.isRoman(operand2)) || (!calculatorLogic.isRoman(operand1) && calculatorLogic.isRoman(operand2))) {
            throw new IllegalArgumentException("Используются одновременно разные системы счисления");
        }

        int result;
        if (calculatorLogic.isRoman(operand1)) {
            result = calculatorLogic.evaluateRomanExpression(operand1, operand2, operator);
            return calculatorLogic.toRoman(result);
        } else {
            result = calculatorLogic.evaluateArabicExpression(Integer.parseInt(operand1), Integer.parseInt(operand2), operator);
            return String.valueOf(result);
        }
    }
}

class CalculatorLogic {
    public int evaluateArabicExpression(int operand1, int operand2, String operator) throws ArithmeticException {
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                if (operand2 == 0) {
                    throw new ArithmeticException("Деление на ноль");
                }
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("Неверный оператор: " + operator);
        }
    }

    public int evaluateRomanExpression(String roman1, String roman2, String operator) throws Exception {
        int operand1 = parseRoman(roman1);
        int operand2 = parseRoman(roman2);

        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                if (operand2 == 0) {
                    throw new ArithmeticException("Деление на ноль");
                }
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("Неверный оператор: " + operator);
        }
    }

    public int parseRoman(String roman) {
        Map<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);

        int result = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            int value = romanMap.get(roman.charAt(i));

            if (value < prevValue) {
                result -= value;
            } else {
                result += value;
            }

            prevValue = value;
        }

        return result;
    }

    public String toRoman(int number) {
        String[] romanNumerals = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        if (number < 1 || number > 10) {
            throw new IllegalArgumentException("Число должно быть от 1 до 10 включительно");
        }
        return romanNumerals[number - 1];
    }

    public boolean isRoman(String input) {
        return input.matches("[IVX]+");
    }
}
