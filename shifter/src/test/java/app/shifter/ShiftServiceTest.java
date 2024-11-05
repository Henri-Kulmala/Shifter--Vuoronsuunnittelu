package app.shifter;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import app.shifter.DTOs.ShiftDTO;
import app.shifter.DTOs.WorkdayDTO;
import app.shifter.domain.Shift;
import app.shifter.mappers.ShiftMapper;
import app.shifter.repositories.ShiftRepository;
import app.shifter.repositories.WorkdayRepository;
import app.shifter.service.ShiftService;
import app.shifter.service.WorkdayService;

@SpringBootTest
class ShiftServiceTest {

	@Mock
	private ShiftRepository shiftRepository;

    @Mock
    private ShiftMapper shiftMapper;

	@InjectMocks
	private ShiftService shiftService;

      @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

	 @Test
    public void getShiftByIdThatExist() {
        Long id = 11L;

        
        Shift shift = new Shift();
        shift.setShiftId(id);

        ShiftDTO shiftDTO = new ShiftDTO();
        shiftDTO.setShiftId(id);

        when(shiftRepository.findById(id)).thenReturn(Optional.of(shift));
        when(shiftMapper.shiftToShiftDTO(shift)).thenReturn(shiftDTO);

       
        ShiftDTO result = shiftService.getShiftById(id);

       
        assertNotNull(result);
        assertEquals(id, result.getShiftId());
    }


   

}
