package dk.lsd.service;

import dk.cphbusiness.lsd.groupe.moonlogde.dto.BookingDTO;
import dk.cphbusiness.lsd.groupe.moonlogde.dto.RoomDTO;
import dk.cphbusiness.lsd.groupe.moonlogde.dto.VacantHotelRoomDTO;
import dk.cphbusiness.lsd.groupe.moonlogde.entitys.Room;
import dk.lsd.datalayer.DatabaseImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HotelService {

    private static final String CONSTR = "jdbc:mysql://localhost/moonlodge?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Rasmus123";

    DatabaseImpl datalayer = new DatabaseImpl(CONSTR, USER, PASSWORD);

    public List<VacantHotelRoomDTO> getHotelRoomList(String city, long dateFrom, long dateTo, int numberGuests, int numberRooms) throws SQLException {
        return datalayer.getHotelRoomList(city, dateFrom, dateTo, numberGuests, numberRooms);
    }

    public BookingDTO createBooking(List<Room> rooms, String[] passportNumbers, long dateFrom, long dateTo, boolean arrivalIsLate) throws SQLException {

        String passportNumber = passportNumbers[0];

        BookingDTO booking = new BookingDTO();

        if (!datalayer.isGuest(passportNumber)) {
            passportNumber = datalayer.createGuest(passportNumber);
        }

        long bookingId = datalayer.createSinlgeBooking(passportNumber, arrivalIsLate);

        List<RoomDTO> newRooms = new ArrayList<>();
        for (Room room : rooms) {
            long roomId = datalayer.createRoomBooking(dateFrom, dateTo, room.getId(), bookingId);
            newRooms.add(new RoomDTO(dateFrom, dateTo, roomId, room.getRoomType().toString(), room.getPrice(), room.getMaxCapacity()));
        }

        booking.setRooms(newRooms);

        return booking;
    }

    public List<BookingDTO> findBookings(String passportNumber) throws SQLException {
        List<BookingDTO> bookings = datalayer.findBookings(passportNumber);

        for (BookingDTO bookingDTO : bookings) {
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
