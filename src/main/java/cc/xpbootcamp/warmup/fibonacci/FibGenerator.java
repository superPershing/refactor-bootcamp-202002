package cc.xpbootcamp.warmup.fibonacci;

public class FibGenerator {
    private static final long BEGIN_POSITION = 1;
    private static final long F0 = 0;
    private static final long F1 = 1;

    public long calculate(long position) {
        if (position < 1) {
            throw new GivenPositionIsNegativeException();
        }
        long temp0 = F0;
        long temp1 = F1;
        long result = temp1;
        long index = BEGIN_POSITION;
        while (index < position) {
            result = temp0 + temp1;
            temp0 = temp1;
            temp1 = result;
            index++;
        }
        return result;
    }

}
