package tasksGenerator.mathClasses;

/**
 * Класс представлющий собой рациональную дробь
 * и содержащий методы для математических операций с ними.
 */
final public class Fraction {
    private final int numerator;
    private final int denominator;

    /**
     *
     * @param numerator числитель рациональной дроби
     * @param denominator знаменатель рациональной дроби
     * @throws IllegalArgumentException если {@code denominator} равен нулю
     */
    public Fraction(int numerator, int denominator) throws IllegalArgumentException {
        if (denominator == 0)
            throw new IllegalArgumentException("Знаменатель равен нулю.");
        int gcd = MathFunctions.gcdByEuclidAlgorithm(numerator, denominator);
        this.numerator = numerator / gcd;
        this.denominator = denominator / gcd;
    }

    /**
     *
     * @param numerator числитель рациональной дроби, являющейся целым числом равной {@code numerator}
     */
    public Fraction(int numerator) {
        this(numerator, 1);
    }

    /**
     * Сложение дроби с дробью {@code fraction}
     * @param fraction дробь, которая будет прибавлена
     * @return результат сложения
     */
    public Fraction add(Fraction fraction) {
        return new Fraction(
                numerator * fraction.denominator + fraction.numerator * denominator,
                denominator * fraction.denominator
        );
    }

    /**
     * Умножение дроби на дробь {@code fraction}
     * @param fraction множитель
     * @return результат умножения
     */
    public Fraction mult(Fraction fraction) {
        return new Fraction(
                numerator * fraction.numerator,
                denominator * fraction.denominator
        );
    }

    /**
     * Вычитание из дроби значение дроби {@code fraction}
     * @param fraction вычитаемое
     * @return результат вычитание
     */
    public Fraction sub(Fraction fraction) {
        return add(new Fraction(-fraction.numerator, fraction.denominator));
    }

    /**
     * Деление дроби на дробь {@code fraction}
     * @param fraction делитель
     * @return результат деления
     */
    public Fraction div(Fraction fraction) {
        return mult(new Fraction(fraction.denominator, fraction.numerator));
    }

    /**
     * Возведение дроби в степень равную значению дроби {@code fraction}
     * @param degree показатель степени
     * @return результат возведения в степень - рациональная дробь
     */
    public Fraction pow(Fraction degree) {
        return new Fraction(
                (int) Math.pow(numerator, degree.toDouble()),
                (int) Math.pow(denominator, degree.toDouble())
        );
    }

    /**
     *
     * @return делитель дроби
     */
    public int getDenominator() {
        return denominator;
    }

    /**
     *
     * @return числитель дроби
     */
    public int getNumerator() {
        return numerator;
    }

    /**
     * Возвращает представление дроби как строки
     * @return строка, как представление дроби
     */
    public String getString() {
        if (denominator == 1 || numerator == 0)
            return "%d".formatted(numerator);
        else
            return "%d/%d".formatted(numerator, denominator);
    }

    /**
     * Возвращает дробь как значение типа {@code double}
     * @return значение дроби
     */
    public double toDouble() {
        return 1.0 * numerator / denominator;
    }

    /**
     * Возвращает произвольную рациональную дробь не равную нулю,
     * числитель и знаменатель которой не больше {@code maxNumber}
     * @param maxNumber максимальное значение числителя и знаменателя дроби
     * @return не нулевая рациональная дробь
     */
    static public Fraction randomNonZeroSignFraction(int maxNumber)
    {
        int numerator = MathFunctions.intRandomSignedNonZero(maxNumber);
        int denominator = MathFunctions.intRandomUnsignedNonZero(maxNumber);
        return new Fraction(numerator, denominator);
    }

    /**
     * Возвращает произвольную дробь не равную нулю,
     * числитель и знаменатель которой не больше {@code maxNumber}
     * @param maxNumber максимальное значение числителя и знаменателя дроби
     * @return рациональная дробь
     */
    static public Fraction randomSignFraction(int maxNumber)
    {
        int numerator = MathFunctions.intRandomSigned(maxNumber);
        int denominator = MathFunctions.intRandomUnsignedNonZero(maxNumber);
        return new Fraction(numerator, denominator);
    }

    @Override
    public String toString() {
        return getString();
    }
}
