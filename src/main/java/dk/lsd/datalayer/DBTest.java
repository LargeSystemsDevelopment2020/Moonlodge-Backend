package dk.lsd.datalayer;

import dk.cphbusiness.lsd.groupe.moonlogde.dto.BookingDTO;
import dk.cphbusiness.lsd.groupe.moonlogde.dto.VacantHotelRoomDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class DBTest {
    private static final String CONSTR = "jdbc:mysql://localhost/moonlodge?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Rasmus123";

    public static void main(String[] args) {


        DatabaseImpl db = new DatabaseImpl(CONSTR, USER, PASSWORD);



        try {

            //Get Vacant hotel rooms.
            //List<VacantHotelRoomDTO> rooms = db.getHotelRoomList("lyngby", dateFrom, dateTo, 1,1);



            //Get Bookings from passport number.
            List<BookingDTO> booking = db.findBookings("DK_khgig865845874598");
            System.out.println(booking.size());





        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }

    }
}
