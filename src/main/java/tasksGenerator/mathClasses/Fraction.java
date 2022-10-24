package tasksGenerator.mathClasses;

final public class Fraction {
    private final int numerator;
    private final int denominator;

    public Fraction(int numerator, int denominator) throws IllegalArgumentException {
        if (denominator == 0)
            throw new IllegalArgumentException("Знаменатель равен нулю.");
        int gcd = MathFunctions.gcdByEuclidAlgorithm(numerator, denominator);
        this.numerator = numerator / gcd;
        this.denominator = denominator / gcd;
    }

    public Fraction(int numerator) {
        this(numerator, 1);
    }

    public Fraction add(Fraction fraction) {
        return new Fraction(
                numerator * fraction.denominator + fraction.numerator * denominator,
                denominator * fraction.denominator
        );
    }

    public Fraction mult(Fraction fraction) {
        return new Fraction(
                numerator * fraction.numerator,
                denominator * fraction.denominator
        );
    }

    public Fraction sub(Fraction fraction) {
        return add(new Fraction(-fraction.numerator, fraction.denominator));
    }

    public Fraction div(Fraction fraction) {
        return mult(new Fraction(fraction.denominator, fraction.numerator));
    }

    public Fraction pow(int degree) {
        return new Fraction(
                (int) Math.pow(numerator, degree),
                (int) Math.pow(denominator, degree)
        );
    }

    public int getDenominator() {
        return denominator;
    }

    public int getNumerator() {
        return numerator;
    }

    public String getString() {
        if (denominator == 1 || numerator == 0)
            return "%d".formatted(numerator);
        else
            return "%d/%d".formatted(numerator, denominator);
    }

    public double toDouble() {
        return 1.0 * numerator / denominator;
    }

    static public Fraction randomNonZeroSignFraction(int maxNumber)
    {
        int numerator = MathFunctions.intRandomSignedNonZero(maxNumber);
        int denominator = MathFunctions.intRandomUnsignedNonZero(maxNumber);
        return new Fraction(numerator, denominator);
    }

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
