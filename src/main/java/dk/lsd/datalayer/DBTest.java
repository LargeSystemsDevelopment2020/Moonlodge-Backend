package dk.lsd.datalayer;

import dk.cphbusiness.lsd.groupe.moonlogde.dto.BookingDTO;
import dk.cphbusiness.lsd.groupe.moonlogde.dto.VacantHotelRoomDTO;
import dk.cphbusiness.lsd.groupe.moonlogde.entitys.Hotel;
import dk.cphbusiness.lsd.groupe.moonlogde.entitys.Room;
import dk.cphbusiness.lsd.groupe.moonlogde.entitys.RoomType;
import dk.lsd.service.HotelService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBTest {
    private static final String CONSTR = "jdbc:mysql://localhost/moonlodge?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Rasmus123";

    public static void main(String[] args) {


        DatabaseImpl db = new DatabaseImpl(CONSTR, USER, PASSWORD);

        long date_before = 1217540000000L;

        try {

            //Get Vacant hotel rooms.
            List<VacantHotelRoomDTO> vrooms = db.getHotelRoomList("lyngby", 1217540000000L, 1261870000000L, 1,1);
            /*
            for (VacantHotelRoomDTO room:vrooms) {
                System.out.println(room.toString());
            }
             */


            //Get Bookings from passport number.
            List<BookingDTO> booking = db.findBookings("DK_khgig865845874598");
            System.out.println(booking.toString());


            //Create booking
            Hotel hotel = new Hotel("Scandic Eremitage", "Klampenborgvej 230", "Lyngby", 2.0, 2.3, null, null);
            hotel.setId("000007");
            Room room1 = new Room(RoomType.S, 3, 1230, hotel);
            room1.setId(1);
            List<Room> rooms = new ArrayList<Room>();
            rooms.add(room1);




            String passport = "Morten :)))";
           // BookingDTO bookingDTO = db.createBooking(rooms, arr, System.currentTimeMillis(),System.currentTimeMillis(), true);
           // System.out.println(bookingDTO);



            // MORTEN TEST
            HotelService service = new HotelService();
            BookingDTO bookingDTO =service.createBooking(rooms, passport, System.currentTimeMillis(),System.currentTimeMillis(), true);
            System.out.println(bookingDTO);

            //BookingDTO bookingz = service.createBooking(roomsz, arr, 1217540000000L, 1261870000000L, true);
            //System.out.println(bookingz);


        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }

    }
}
