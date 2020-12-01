package dk.lsd.datalayer;

import dk.cphbusiness.lsd.groupe.moonlogde.dto.VacantHotelRoomDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class DBTest {
    private static final String CONSTR = "jdbc:mysql://206.81.29.87:22/moonlodge";
    private static final String USER = "root";
    private static final String PASSWORD = "mmmrj";

    public static void main(String[] args) {



        DatabaseImpl db = new DatabaseImpl(CONSTR, USER, PASSWORD);


        Date dateFrom = java.util.Date.from(LocalDate.of( 2008 , 1 , 1 ).atStartOfDay(ZoneId.of( "Africa/Tunis" )).toInstant());
        Date dateTo = java.util.Date.from(LocalDate.of( 2009 , 1 , 1 ).atStartOfDay(ZoneId.of( "Africa/Tunis" )).toInstant());


        try {
            List<VacantHotelRoomDTO> rooms = db.getHotelRoomList("lyngby", dateFrom, dateTo, 1,1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
