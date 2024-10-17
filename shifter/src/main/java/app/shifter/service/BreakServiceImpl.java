package app.shifter.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.shifter.domain.Breaks;
import app.shifter.interfaces.BreakService;
import app.shifter.repositories.BreaksRepository;

@Service
public class BreakServiceImpl implements BreakService {

    @Autowired
    private BreaksRepository breaksRepository;

    @Override
    public Breaks createBreak(Breaks breaks) {
        return breaksRepository.save(breaks);
    }

    @Override
    public List<Breaks> getAllBreaks() {
        return breaksRepository.findAll();
    }

    @Override
    public Breaks getBreaksById(Long breakId) {
        return breaksRepository.findByBreakId(breakId);
    }

    @Override
    public void deleteBreaks(Long breakId) {
        breaksRepository.deleteById(breakId);
    }

}

