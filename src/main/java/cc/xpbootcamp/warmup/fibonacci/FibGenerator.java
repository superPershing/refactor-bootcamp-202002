package cc.xpbootcamp.warmup.fibonacci;

public class FibGenerator {
    private static final long BEGIN_POSITION = 1;
    private static final long F0 = 0;
    private static final long F1 = 1;

    public long calculate(long position) {
        if (position < BEGIN_POSITION) {
            throw new GivenPositionIsNegativeException();
        }
        return fibIter(F1, F0, position);
    }

    private long fibIter(long f1, long f0, long counter) {
        if (counter == 0) {
            return f0;
        }
        return fibIter(f1 + f0, f1, counter - 1);
    }

}
