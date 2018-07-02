package sy.edu.au.nodemcu;

public enum VModels {
    five,
    four,
    two,
    one,
    three,
    forward,
    backward,
    right,
    left,
    stop;

    public static VModels get(int i) {
        return values()[i];
    }

    private final static int DURATION = 1000; // 1 second

    public int duration() {
        switch (this) {
            case two:
                return 2 * DURATION;
            case three:
                return 3 * DURATION;
            case four:
                return 4 * DURATION;
            case five:
                return 5 * DURATION;

        }
        //all others
        return DURATION;
    }

    /**
     * Either number or Command
     *
     * @return
     */
    public boolean isDuration() {
        switch (this) {
            case one:
            case two:
            case three:
            case four:
            case five:
                return true;
            default:
                return false;
        }
    }

    public CommandType commandType() {
        switch (this) {
            case backward:
                return CommandType.backward;
            case forward:
                return CommandType.forward;
            case left:
                return CommandType.left;
            case right:
                return CommandType.right;
            default:
                return CommandType.stop;
        }
    }

    public static int length = values().length;
}