package dk.lsd.rmi;

import dk.cphbusiness.lsd.groupe.moonlogde.dto.BookingDTO;
import dk.cphbusiness.lsd.groupe.moonlogde.dto.VacantHotelRoomDTO;
import dk.cphbusiness.lsd.groupe.moonlogde.entitys.Room;
import dk.cphbusiness.lsd.groupe.moonlogde.interfaces.HotelManagerInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.util.List;

public class RMIMethodsImpl extends UnicastRemoteObject implements HotelManagerInterface {
    public RMIMethodsImpl() throws RemoteException {
    }

    @Override
    public List<VacantHotelRoomDTO> getHotelRoomList(String city, Date dateFrom, Date dateTo, int numberGuests, int numberRooms) {
        return null;
    }

    @Override
    public BookingDTO createBooking(List<Room> rooms, String[] passportNumbers, Date dateFrom, Date dateTo, boolean arrivalIsLate) {
        return null;
    }

    @Override
    public List<BookingDTO> findBookings(String passportNumber) {
        return null;
    }

    @Override
    public boolean cancelBooking(long bookingId) {
        return false;
    }
}
