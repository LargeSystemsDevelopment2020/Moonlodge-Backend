package dk.lsd.datalayer;

import dk.cphbusiness.lsd.groupe.moonlogde.dto.BookingDTO;
import dk.cphbusiness.lsd.groupe.moonlogde.dto.VacantHotelRoomDTO;
import dk.cphbusiness.lsd.groupe.moonlogde.entitys.*;

import java.util.Date;
import java.util.List;

public interface Database {

    public List<VacantHotelRoomDTO> getHotelRoomList(String city, Date dateFrom, Date dateTo, int numberGuests, int numberRooms);
    public BookingDTO createBooking(List<Room> rooms, String[] passportNumbers, Date dateFrom, Date dateTo, boolean arrivalIsLate);
    public List<BookingDTO> findBookings(String passportNumber);
    public boolean cancelBooking(long bookingId);

}
