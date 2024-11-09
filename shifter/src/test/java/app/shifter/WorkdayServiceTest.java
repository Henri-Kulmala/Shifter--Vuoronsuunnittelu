package app.shifter;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import app.shifter.DTOs.WorkdayDTO;
import app.shifter.domain.Workday;
import app.shifter.mappers.WorkdayMapper;
import app.shifter.repositories.WorkdayRepository;
import app.shifter.service.WorkdayService;

@SpringBootTest
public class WorkdayServiceTest {

	@Autowired
	private WorkdayService workdayService;

	@MockBean
	private WorkdayMapper workdayMapper;

	@MockBean
	private WorkdayRepository workdayRepository;

	@Test
	public void getWorkdayByDate_ShouldReturnWorkdayDTO_WhenDateExists() {
		LocalDate date = LocalDate.of(2024, 11, 3);

		Workday workday = new Workday();
		workday.setDate(date);

		when(workdayRepository.findByDate(date)).thenReturn(Optional.of(workday));

		WorkdayDTO workdayDTO = new WorkdayDTO();
		workdayDTO.setDate(date);
		when(workdayMapper.workdayToWorkdayDTO(workday)).thenReturn(workdayDTO);

		WorkdayDTO result = workdayService.getWorkdayByDate(date);

		assertNotNull(result);
		assertEquals(date, result.getDate());
	}

	@Test
	public void getWorkdayByDate_ShouldReturnNull_WhenDateDoesNotExist() {
		LocalDate date = LocalDate.of(2024, 11, 3);

		when(workdayRepository.findByDate(date)).thenReturn(Optional.empty());

		when(workdayMapper.workdayToWorkdayDTO(any())).thenReturn(null);

		WorkdayDTO result = workdayService.getWorkdayByDate(date);

		assertNull(result);
	}

}
