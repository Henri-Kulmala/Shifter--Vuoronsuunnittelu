package app.shifter.interfaces;

import java.util.List;

import app.shifter.domain.Breaks;

public interface BreakService {

    Breaks createBreaks(Breaks breaks);
    List<Breaks> getAllBreaks();
    Breaks getBreaksByType(String breakType);
    Breaks getBreaksById(Long breakId);
    void deleteBreaks(Long breakId);

}
