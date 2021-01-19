package dk.lsd.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.rmi.RemoteException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dk.cphbusiness.lsd.groupe.moonlogde.dto.BookingDTO;
import dk.lsd.rmi.RMIMethodsImpl;

public class ITHotelService {
	
	private static List<BookingDTO> bookings;
	private static BookingDTO booking;
	private RMIMethodsImpl rmi;
	
	@BeforeAll
	public static void init() {
		booking = new BookingDTO(123456L, null, "passportnr-test", null );
		bookings = new ArrayList<>();
		bookings.add(booking);
	}
	
	@BeforeEach
	public void setup() throws SQLException, RemoteException {
		rmi = mock(RMIMethodsImpl.class);
		when(rmi.findBookings(anyString())).thenReturn(bookings);
	}
	
	@Test
	public void findBookingMustReturnValidBooking( ) throws SQLException {
		int size = 1;
		assertEquals(size, rmi.findBookings("passportnr-test").size());
	}
}
