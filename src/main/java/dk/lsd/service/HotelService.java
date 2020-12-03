package dk.lsd.service;

import dk.cphbusiness.lsd.groupe.moonlogde.dto.BookingDTO;
import dk.cphbusiness.lsd.groupe.moonlogde.dto.VacantHotelRoomDTO;
import dk.cphbusiness.lsd.groupe.moonlogde.entitys.Room;
import dk.lsd.datalayer.DatabaseImpl;

import java.sql.SQLException;
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
        BookingDTO bookingDTO = datalayer.createBooking(passportNumbers, arrivalIsLate);
        for(Room room : rooms){
            datalayer.createRoomBooking(dateFrom, dateTo, room.getId(), bookingDTO.getId());
        }
        BookingDTO newBookingDTO = datalayer.findBooking(bookingDTO.getId());
        newBookingDTO.setRooms(datalayer.getRoomsFromBooking(bookingDTO.getId()));

        bookingDTO.setRooms(newBookingDTO.getRooms());
        bookingDTO.setPassportNumbers(newBookingDTO.getPassportNumbers());
        bookingDTO.setHotel(newBookingDTO.getHotel());

        return bookingDTO;
    }

    public List<BookingDTO> findBookings(String passportNumber) throws SQLException {
        List<BookingDTO> bookings = datalayer.findBookings(passportNumber);

        for(BookingDTO bookingDTO : bookings){
            bookingDTO.setRooms(datalayer.getRoomsFromBooking(bookingDTO.getId()));
        }

        return bookings;
    }

    public boolean cancelBooking(long bookingId) throws SQLException {
        datalayer.cancelRoomBooking(bookingId);
        datalayer.cancelBooking(bookingId);
        return true;
    }
}
