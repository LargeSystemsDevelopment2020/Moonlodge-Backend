package dk.lsd.datalayer;

import dk.cphbusiness.lsd.groupe.moonlogde.dto.BookingDTO;
import dk.cphbusiness.lsd.groupe.moonlogde.dto.RoomDTO;
import dk.cphbusiness.lsd.groupe.moonlogde.dto.VacantHotelRoomDTO;
import dk.cphbusiness.lsd.groupe.moonlogde.entitys.Hotel;
import dk.cphbusiness.lsd.groupe.moonlogde.entitys.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DatabaseImpl {

    private String connectionString;
    private String username, password;

    public DatabaseImpl(String conStr, String user, String pass) {
        connectionString = conStr;
        username = user;
        password = pass;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString, username, password);
    }


    public List<VacantHotelRoomDTO> getHotelRoomList(String city, Date dateFrom, Date dateTo, int numberGuests, int numberRooms) throws SQLException {
        var sql = "select * from room WHERE city = ?;";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql)) {
            stmt.setString(1, city);

            var result = new ArrayList<VacantHotelRoomDTO>();


            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {


                var rooms = (List<RoomDTO>) resultSet.getArray("rooms");
                var hotel = resultSet.getObject("hotel", Hotel.class);

                VacantHotelRoomDTO vacantRoom = new VacantHotelRoomDTO(rooms, hotel);
                result.add(vacantRoom);
            }
            return result;
        }
    }

    public BookingDTO createBooking(List<Room> rooms, String[] passportNumbers, Date dateFrom, Date dateTo, boolean arrivalIsLate) throws SQLException {
        var sql = "insert into booking(arrival_is_late, guest_passport_number) values (?,?)";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {


            stmt.setBoolean(1, arrivalIsLate);
            stmt.setString(2, passportNumbers[0]);


            stmt.executeUpdate();

            // get the newly created id
            try (var resultSet = stmt.getGeneratedKeys()) {
                resultSet.next();
                int newId = resultSet.getInt(1);

                BookingDTO booking = new BookingDTO();
                booking.setId(newId);

                return booking;
            }
        }
    }

        private int createRoomBooking(Date dateFrom, Date dateTo, int roomId, int bookingId) throws SQLException {
            var sql = "insert into room_booking(date_of_arrival, date_of_departure, room_id, booking_id) values (?,?,?,?)";
            try (var con = getConnection();
                 var stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                java.text.SimpleDateFormat sdf =
                        new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                stmt.setString(1, sdf.format(dateFrom));
                stmt.setString(2, sdf.format(dateTo));
                stmt.setInt(3, roomId);
                stmt.setInt(4, bookingId);

                try (var resultSet = stmt.getGeneratedKeys()) {
                    resultSet.next();
                    int newId = resultSet.getInt(1);

                    return newId;
                }

            }
        }



    public List<BookingDTO> findBookings(String passportNumber) throws SQLException {
        var sql = "select * from booking WHERE passportNumber = ?;";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql)) {
            stmt.setString(1, passportNumber);

            var result = new ArrayList<BookingDTO>();


            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {

                long bookingID = resultSet.getLong("id");
                List<RoomDTO> rooms = (List<RoomDTO>) resultSet.getBlob("rooms");
                String[] passportNumbers = resultSet.getObject("passportNumbers", String[].class);
                Hotel hotel = resultSet.getObject("hotel", Hotel.class);

                BookingDTO booking = new BookingDTO(bookingID, rooms, passportNumbers, hotel);

                result.add(booking);
            }

            return result;
        }
    }


    public boolean cancelBooking(long bookingId) throws SQLException {
        var sql = "delete from bookings where bookingId = ?";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, bookingId);


            // execute the preparedstatement
            stmt.execute();
        }
        return true;
    }
}
