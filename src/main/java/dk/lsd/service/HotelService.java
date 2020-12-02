package dk.lsd.service;

import dk.cphbusiness.lsd.groupe.moonlogde.dto.BookingDTO;
import dk.cphbusiness.lsd.groupe.moonlogde.dto.VacantHotelRoomDTO;
import dk.cphbusiness.lsd.groupe.moonlogde.entitys.Room;
import dk.lsd.datalayer.DatabaseImpl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class HotelService {

    private final String DB_URL = "";
    private final String DB_USER = "";
    private final String DB_PASS = "";

    DatabaseImpl datalayer = new DatabaseImpl(DB_URL, DB_USER, DB_PASS);

    public List<VacantHotelRoomDTO> getHotelRoomList(String city, long dateFrom, long dateTo, int numberGuests, int numberRooms) throws SQLException {
        return datalayer.getHotelRoomList(city, dateFrom, dateTo, numberGuests, numberRooms);
    }

    public BookingDTO createBooking(List<Room> rooms, String[] passportNumbers, long dateFrom, long dateTo, boolean arrivalIsLate) throws SQLException {
        return datalayer.createBooking(rooms, passportNumbers, dateFrom, dateTo, arrivalIsLate);
    }

    public List<BookingDTO> findBookings(String passportNumber) throws SQLException {
        return datalayer.findBookings(passportNumber);
    }

    public boolean cancelBooking(long bookingId) throws SQLException {
        return datalayer.cancelBooking(bookingId);
    }
}
