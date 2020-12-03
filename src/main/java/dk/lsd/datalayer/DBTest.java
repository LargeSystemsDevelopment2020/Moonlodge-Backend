package dk.lsd.datalayer;

import dk.cphbusiness.lsd.groupe.moonlogde.dto.BookingDTO;
import dk.cphbusiness.lsd.groupe.moonlogde.dto.VacantHotelRoomDTO;

import java.sql.SQLException;
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
            List<VacantHotelRoomDTO> rooms = db.getHotelRoomList("lyngby", 1217540000000L, 1261870000000L, 1,1);
            /*
            for (VacantHotelRoomDTO room:rooms) {
                System.out.println(room.toString());
            }
             */


            //Get Bookings from passport number.
            List<BookingDTO> booking = db.findBookings("DK_khgig865845874598");
            //System.out.println(booking.toString());

            //Create booking
            String[] arr = {"RLJTEhehhSTSS"};
            //BookingDTO bookingDTO = db.createBooking(null, arr, System.currentTimeMillis(),System.currentTimeMillis(), true);




        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }

    }
}
