package mathGeneratorBot.tasksGenerator.mathClasses;

/**
 * Объекты класса представлют собой рациональную дробь с целыми числителем и знаменателем.
 * Класс содержит методы для выполнения метматических операций над дробями.
 */
final public class Fraction {
    private final int numerator;
    private final int denominator;

    /**
     * @param numerator числитель рациональной дроби
     * @param denominator знаменатель рациональной дроби
     * @throws IllegalArgumentException если {@code denominator} равен нулю
     */
    public Fraction(int numerator, int denominator) throws IllegalArgumentException {
        if (denominator == 0)
            throw new IllegalArgumentException("Знаменатель равен нулю.");
        int gcd = MathFunctions.gcdByEuclidAlgorithm(numerator, denominator);
        this.numerator = (denominator / Math.abs(denominator)) * numerator / gcd;
        this.denominator = Math.abs(denominator) / gcd;
    }

    /**
     *
     * @param numerator числитель рациональной дроби, являющейся целым числом равной {@code numerator}
     */
    public Fraction(int numerator) {
        this(numerator, 1);
    }

    /**
     * @param fraction дробь, которая будет прибавлена
     * @return результат сложения - новый объект класса {@code Fraction}.
     */
    public Fraction add(Fraction fraction) {
        return new Fraction(
                numerator * fraction.denominator + fraction.numerator * denominator,
                denominator * fraction.denominator
        );
    }

    /**
     * @param fraction множитель
     * @return результат умножения - новый объект класса {@code Fraction}.
     */
    public Fraction multiplication(Fraction fraction) {
        return new Fraction(
                numerator * fraction.numerator,
                denominator * fraction.denominator
        );
    }

    /**
     * @param fraction вычитаемое
     * @return результат вычитание - новый объект класса {@code Fraction}.
     */
    public Fraction sub(Fraction fraction) {
        return add(new Fraction(-fraction.numerator, fraction.denominator));
    }

    /**
     * @param fraction делитель
     * @return результат деления - новый объект класса {@code Fraction}.
     */
    public Fraction div(Fraction fraction) {
        return multiplication(new Fraction(fraction.denominator, fraction.numerator));
    }

    /**
     * @param degree показатель степени
     * @return результат возведения в степень - новый объект класса {@code Fraction}.
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
     * Возвращает представление дроби как строки.
     * @return строка, как представление дроби.
     */
    public String getString() {
        if (denominator == 1 || numerator == 0)
            return "%d".formatted(numerator);
        else
            return "%d/%d".formatted(numerator, denominator);
    }

    /**
     * Возвращает дробь как значение типа {@code double}.
     * @return значение дроби.
     */
    public double toDouble() {
        return 1.0 * numerator / denominator;
    }

    /**
     * Возвращает произвольную рациональную дробь не равную нулю,
     * числитель и знаменатель которой не больше {@code maxNumber}
     * @param maxNumber максимальное значение числителя и знаменателя дроби.
     * @return не нулевая рациональная дробь.
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
     * @param maxNumber максимальное значение числителя и знаменателя дроби.
     * @return рациональная дробь.
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

    @Override
    public boolean equals(Object other) {
        Fraction otherFraction;
        if (other instanceof Fraction)
            otherFraction = (Fraction) other;
        else
            return false;
        return (numerator == otherFraction.numerator) && (denominator == otherFraction.denominator);
    }
}
