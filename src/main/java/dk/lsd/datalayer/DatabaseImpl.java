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


    public List<VacantHotelRoomDTO> getHotelRoomList(String city, long dateFrom, long dateTo, int numberGuests, int numberRooms) throws SQLException {
        var sql = "SELECT room.id as room_id, room.max_capacity as room_capacity, room.price as room_price, room.type as room_type, hotel.id as hotel_id, hotel.name as hotel_name, hotel.address as hotel_address, hotel.city as hotel_city, hotel.distance_to_center as hotel_distance, hotel.raiting as hotel_raiting, hotel.head_quarter_id as headquater_id\n" +
                "FROM room " +
                "INNER JOIN hotel ON room.hotel_id = hotel.id " +
                "WHERE room.max_capacity >= ? AND hotel.city LIKE '%' AND room.id NOT IN ( " +
                "SELECT room_id  " +
                "FROM room_booking " +
                "WHERE date_of_arrival >= 1280613600000 AND date_of_departure <= 1285884000000);";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, numberGuests);

            var result = new ArrayList<VacantHotelRoomDTO>();


            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {


                long roomId = resultSet.getLong("room_id");
                long roomCapacity = resultSet.getLong("room_capacity");

                String hotelId = resultSet.getString("hotel_id");


                System.out.println(resultSet.getString("hotel_id"));

            }
            return result;
        }
    }

    public BookingDTO createBooking(String[] passportNumbers, boolean arrivalIsLate) throws SQLException {
        var sql = "insert into booking(arrival_is_late, guest_passport_number) values (?,?)";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setBoolean(1, arrivalIsLate);
            stmt.setString(2, passportNumbers[0]);

            stmt.executeUpdate();

            try (var resultSet = stmt.getGeneratedKeys()) {
                resultSet.next();
                int newId = resultSet.getInt(1);

                BookingDTO booking = new BookingDTO();
                booking.setId(newId);

                return booking;
            }
        }
    }

    public void createRoomBooking(long dateFrom, long dateTo, long roomId, long bookingId) throws SQLException {
        var sql = "insert into room_booking(date_of_arrival, date_of_departure, room_id, booking_id) values (?,?,?,?)";
        try (var con = getConnection();
            var stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setFloat(1, dateFrom);
            stmt.setFloat(2, dateTo);
            stmt.setLong(3, roomId);
            stmt.setLong(4, bookingId);

            stmt.executeUpdate();

//------------------Room Booking Tabel i SQL har ikke ID kolonne!!!
//            try (var resultSet = stmt.getGeneratedKeys()) {
//                resultSet.next();
//                return resultSet.getInt(1);
//                //return newId;
//            }
        }

    }

    public List<BookingDTO> findBookings(String passportNumber) throws SQLException {
        //var sql = "select * from booking WHERE guest_passport_number = ?;";
        var sql = "select booking.id as booking_id, hotel.name as hotel_name, hotel.address as hotel_address, hotel.city as hotel_city, hotel.distance_to_center as hotel_center_distance, hotel.raiting as hotel_raiting, hotel.head_quarter_id\n" +
                "from room " +
                "inner join room_booking on room_booking.room_id =room.id " +
                "inner join booking on booking.id = room_booking.booking_id " +
                "inner join hotel on hotel.id = room.hotel_id " +
                "where guest_passport_number = ?;";

        try (var con = getConnection();
             var stmt = con.prepareStatement(sql)) {
            stmt.setString(1, passportNumber);

            var result = new ArrayList<BookingDTO>();


            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {

                long bookingID = resultSet.getLong("booking_id");
                String hotelName = resultSet.getString("hotel_name");
                String hotelAddress = resultSet.getString("hotel_address");
                String hotelCity = resultSet.getString("hotel_city");
                double hotelRaiting = resultSet.getDouble("hotel_raiting");
                int hotelCenterDistance = resultSet.getInt("hotel_center_distance");



                List<RoomDTO> rooms = getRoomsFromBooking(bookingID);

                Hotel hotel = new Hotel( hotelName, hotelAddress, hotelCity, hotelRaiting, hotelCenterDistance, null, null);




                BookingDTO booking = new BookingDTO(bookingID, rooms, new String[] {passportNumber}, hotel);

                result.add(booking);
            }

            return result;
        }
    }


    public List<RoomDTO> getRoomsFromBooking(long bookingId) throws SQLException {

        var sql = "SELECT room.id as room_id, room.type as room_type, room.price as room_price, room.max_capacity as room_capacity, room_booking.booking_id as room_booking_id, room_booking.date_of_arrival as date_of_arrival, room_booking.date_of_departure as date_of_departure\n" +
                "FROM room\n" +
                "INNER JOIN room_booking ON room.id = room_booking.room_id\n" +
                "WHERE room_booking.booking_id = ?;";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, bookingId);

            var result = new ArrayList<RoomDTO>();


            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {

                long bookingID = resultSet.getLong("room_booking_id");

                long dateOfArrival = resultSet.getLong("date_of_arrival");
                long dateOfDeparture = resultSet.getLong("date_of_departure");
                long roomId = resultSet.getLong("room_id");
                String type =resultSet.getString("room_type");
                double price = resultSet.getDouble("room_price");
                int maxCapacity = resultSet.getInt("room_capacity");


                RoomDTO room = new RoomDTO(dateOfArrival, dateOfDeparture, roomId, type, price,maxCapacity);


                result.add(room);
            }

            return result;
        }
    }

    public boolean cancelRoomBooking(long bookingId) throws SQLException {
        var sql = "delete from room_booking where booking_id = ?";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, bookingId);

            stmt.execute();
        }
        return true;
    }

    public boolean cancelBooking(long bookingId) throws SQLException {
        var sql = "delete from booking where id = ?";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, bookingId);

            stmt.execute();
        }
        return true;
    }
}
