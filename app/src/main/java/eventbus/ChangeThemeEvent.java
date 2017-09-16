package eventbus;

/**
 * Created by MyPC on 2017/3/25.
 */

public class ChangeThemeEvent {
    private int number;

    public ChangeThemeEvent(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
