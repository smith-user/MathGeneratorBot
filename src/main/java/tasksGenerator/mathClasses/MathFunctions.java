package tasksGenerator.mathClasses;

import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

/**
 * Класс, содержащий методы для математических вычислений.
 */
final public class MathFunctions {

    /**
     * Находит наибольший общий делитель (НОД) двух чисел.
     * @param n1 певрое число
     * @param n2 второе число
     * @return наибольший общий делитель
     */
    public static int gcdByEuclidAlgorithm(int n1, int n2) {
        while (n2 != 0) {
            int t = n2;
            n2 = n1 % n2;
            n1 = t;
        }
        return n1;
    }

    /**
     * Находит наименьшее общее кратное (НОК) двух чисел.
     * @param a певрое число
     * @param b второе число
     * @return наименьшее общее кратное
     */
    public static int leastCommonMultiple(int a, int b) {
        return a * b / gcdByEuclidAlgorithm(a, b);
    }

    /**
     * Перевод матиматического выражения с рациональными числами и скобками из инфиксной нотации в постфиксную.
     * @param exp матиматического выражения с рациональными числами
     * @param operations ключ - математический оператор, встречающиеся в {@code exp}; значение - приоритет оператора (0 - самый низкий)
     * @return строка {@code exp} в постфиксной нотации
     * @throws IllegalArgumentException некорректное математическое выражение
     */
    public static Object[] getRPN(String exp, Map<Character, Integer> operations) throws IllegalArgumentException {
        ArrayList<Object> result = new ArrayList<>();
        StringBuilder number = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        for(int i = 0; i < exp.length(); i++)
        {
            char symbol = exp.charAt(i);
            if(
                    isNumeric(number.toString() + symbol) ||
                    (symbol == '-' && i < exp.length() - 1 && isNumeric(String.valueOf(exp.charAt(i + 1))) &&
                            (i == 0 || exp.charAt(i - 1) == '('))
            ) {
                number.append(symbol);
                continue;
            }

            if (isNumeric(number.toString())) {
                result.add(
                        new Fraction(Integer.parseInt(number.toString()))
                );
                number.delete(0, number.length());
            }

            if(symbol == '(') {
                stack.push(symbol);
            } else if (symbol == ')') {
                while (!stack.empty() && stack.peek() != '(')
                    result.add(stack.pop());
                if (!stack.empty() && stack.peek() == '(')
                    stack.pop();
                else
                    throw new IllegalArgumentException("Некорректная строка: %s".formatted(exp.substring(0, i + 1)));
            } else if (operations.containsKey(symbol)) {
                int operPrio = operations.get(symbol);
                while( !stack.empty() && operations.containsKey(stack.peek()) &&
                        (symbol != '^' && operPrio <= operations.get(stack.peek()) ||
                                symbol == '^' && operPrio < operations.get(stack.peek()))
                ) {
                    result.add(stack.pop());
                }
                stack.push(symbol);
            } else {
                throw new IllegalArgumentException("Некорректная строка: %s".formatted(exp.substring(0, i + 1)));
            }
        }
        if(number.length() > 0)
            result.add(new Fraction(Integer.parseInt(number.toString())));

        while(!stack.empty())
            result.add(stack.pop());
        return result.toArray();
    }

    public static boolean isNumeric(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static int intRandomSigned(int maxNum) {
        return (int) Math.pow(-1, intRandomUnsigned(1)) * intRandomUnsigned(maxNum);
    }

    public static int intRandomSignedNonZero(int maxNum) {
        int number = intRandomSigned(maxNum);
        while (number == 0)
            number = intRandomSigned(maxNum);
        return number;
    }

    public static int intRandomUnsigned(int maxNum) {
        return (int) (Math.random() * (++maxNum));
    }

    public static int intRandomUnsignedNonZero(int maxNum) {
        int number = intRandomUnsigned(maxNum);
        while (number == 0)
            number = intRandomUnsigned(maxNum);
        return number;
    }
}
