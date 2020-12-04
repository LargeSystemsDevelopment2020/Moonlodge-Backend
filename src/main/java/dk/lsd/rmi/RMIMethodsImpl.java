package dk.lsd.rmi;

import dk.cphbusiness.lsd.groupe.moonlogde.dto.BookingDTO;
import dk.cphbusiness.lsd.groupe.moonlogde.dto.VacantHotelRoomDTO;
import dk.cphbusiness.lsd.groupe.moonlogde.entitys.Room;
import dk.cphbusiness.lsd.groupe.moonlogde.interfaces.HotelManagerInterface;
import dk.lsd.service.HotelService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

public class RMIMethodsImpl extends UnicastRemoteObject implements HotelManagerInterface {

    HotelService service = new HotelService();

    public RMIMethodsImpl() throws RemoteException {
    }

    @Override
    public List<VacantHotelRoomDTO> getHotelRoomList(String city, long dateFrom, long dateTo, int numberGuests, int numberRooms) {
        try {
            return service.getHotelRoomList(city, dateFrom, dateTo, numberGuests, numberRooms);
        } catch (SQLException ex) {
            //Logging SQL exception
        }
        return null;
    }

    @Override
    public BookingDTO createBooking(List<Room> rooms, String passportNumber, long dateFrom, long dateTo, boolean arrivalIsLate) {
        try {
            return service.createBooking(rooms, passportNumber, dateFrom, dateTo, arrivalIsLate);
        } catch (SQLException ex) {
            //Logging SQL exception
        }
        return null;
    }

    @Override
    public List<BookingDTO> findBookings(String passportNumber) {
        try {
            return service.findBookings(passportNumber);
        } catch (SQLException ex) {
            //Logging SQL exception
        }
        return null;
    }

    @Override
    public boolean cancelBooking(long bookingId) {
        try {
            return service.cancelBooking(bookingId);
        } catch (SQLException ex) {
            //Logging SQL exception
        }
        return false;
    }
}
