package rs.etf.sab.operations;

import java.util.Calendar;

public interface GeneralOperations {
    void	eraseAll();
    Calendar	getCurrentTime();
    void	setInitialTime(Calendar time);
    Calendar	time(int days);
}
