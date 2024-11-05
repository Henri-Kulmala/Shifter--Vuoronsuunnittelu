package app.shifter;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import app.shifter.DTOs.WorkdayDTO;
import app.shifter.repositories.WorkdayRepository;
import app.shifter.service.WorkdayService;

@SpringBootTest
class WorkdayServiceTest {

	@Mock
	private WorkdayRepository workdayRepository;

	@InjectMocks
	private WorkdayService workdayService;

	@Test
	public void getWorkdayByDate() {

		LocalDate date = LocalDate.of(2024, 11, 3);
		WorkdayDTO workday = new WorkdayDTO();
		workday.setDate(date);

		WorkdayDTO result = workdayService.getWorkdayByDate(date);

		assertNotNull(result);
		assertEquals(date, result.getDate());
	}


    @Test
    public void getWorkdayByDate_ShouldReturnNull_WhenDateDoesNotExist() {
        // Given
        LocalDate date = LocalDate.of(2024, 11, 3);
        
        when(workdayRepository.findByDate(date)).thenReturn(Optional.empty());

        // When
        WorkdayDTO result = workdayService.getWorkdayByDate(date);

        // Then
        assertNull(result);
    }

}
