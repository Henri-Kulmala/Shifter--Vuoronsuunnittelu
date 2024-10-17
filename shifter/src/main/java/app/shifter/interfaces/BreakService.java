package app.shifter.interfaces;

import java.util.List;

import app.shifter.domain.Breaks;

public interface BreakService {

    Breaks createBreak(Breaks breaks);
    List<Breaks> getAllBreaks();
    Breaks getBreaksById(Long breakId);
    void deleteBreaks(Long breakId);

}
