package dk.lsd.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dk.cphbusiness.lsd.groupe.moonlogde.dto.VacantHotelRoomDTO;
import dk.lsd.service.HotelService;


public class HotelServiceTest {
	private HotelService service;
	private List<VacantHotelRoomDTO> vaccancies;
	
	@BeforeEach
	public void setup() throws SQLException {
		service = mock(HotelService.class);
		vaccancies = new ArrayList<>();

	}
	
	@Nested
	@DisplayName("Nested tests for the getHotelRoomList method in HotelService.class")
	class HotelRoomList {
		
		@Test
		@DisplayName("Testing HotelService method getHotelRoomList(String, long, long, int, int)")
		public void returnsAListOfVacantHotelRoomDTOTest() throws SQLException  {
			when(service.getHotelRoomList(anyString(), anyLong(), anyLong(), anyInt(), anyInt())).thenReturn(vaccancies);
			List<VacantHotelRoomDTO> expected = service.getHotelRoomList("test-string", 1606777200000L, 1607122800000L, 5, 2);
			assertEquals(expected.size(), vaccancies.size(), "should return size 0 for both lists");
		}
		
		@Test
		@DisplayName("The method should throw an SQLException if there is no database connection")
		public void methodThrowsSqlException() throws SQLException {
			//service = new HotelService();
			when(service.getHotelRoomList(anyString(), anyLong(), anyLong(), anyInt(), anyInt())).thenThrow(SQLException.class);
			assertThrows(SQLException.class, () -> service.getHotelRoomList("test-string", 1606777200000L, 1607122800000L, 5, 2));
		}
	}

	

}
