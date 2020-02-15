package cc.xpbootcamp.warmup.fibonacci;

public class FibGenerator {
    public long calculate(long i) {
        if (i < 0) {
            throw new GivenPositionIsNegativeException();
        }
        if (i == 0) {
            return 0;
        }
        if (i == 1) {
            return 1;
        }
        return calculate(i - 1) + calculate(i - 2);
    }
}
