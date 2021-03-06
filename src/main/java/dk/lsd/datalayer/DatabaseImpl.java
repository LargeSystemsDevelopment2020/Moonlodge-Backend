package dk.lsd.datalayer;

import dk.cphbusiness.lsd.groupe.moonlogde.dto.BookingDTO;
import dk.cphbusiness.lsd.groupe.moonlogde.dto.HotelDTO;
import dk.cphbusiness.lsd.groupe.moonlogde.dto.RoomDTO;
import dk.cphbusiness.lsd.groupe.moonlogde.dto.VacantHotelRoomDTO;
import dk.cphbusiness.lsd.groupe.moonlogde.entitys.Hotel;
import dk.cphbusiness.lsd.groupe.moonlogde.entitys.Room;
import dk.cphbusiness.lsd.groupe.moonlogde.entitys.RoomType;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class DatabaseImpl implements Serializable {

    private String connectionString;
    private String username, password;
    static Logger log = Logger.getLogger(DatabaseImpl.class.getName());

    public DatabaseImpl(String conStr, String user, String pass) {
        connectionString = conStr;
        username = user;
        password = pass;
    }

    private Connection getConnection() throws SQLException {
        log.info("Connection: " + connectionString);
        log.info("Username: " + username);
        return DriverManager.getConnection(connectionString, username, password);
    }

    public boolean isGuest(String passportNr) throws SQLException {
        log.info("isGuest, with passportNr: "+passportNr);
        String sql = "select * from guest where passport_number = ?";
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, passportNr);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    log.info("isGuest, boolean: true");
                    return true;
                }
                log.info("isGuest, boolean: false");
                return false;
            }
        }
    }


    public String createGuest(String passportNumber) throws SQLException {
        log.info("createGuest, with passportNumber: "+passportNumber);
        String sql = "insert into guest (passport_number) values (?);";
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, passportNumber);

            stmt.executeUpdate();
            log.info("Customer created");
            return passportNumber;

        }
    }

    public long createSinlgeBooking(String passportNumbers, boolean arrivalIsLate) throws SQLException {
        log.info("createSinlgeBooking, with passportNumber: "+passportNumbers);
        String sql = "insert into booking(arrival_is_late, guest_passport_number) values(?, ?);";
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {


            stmt.setBoolean(1, arrivalIsLate);
            stmt.setString(2, passportNumbers);

            stmt.executeUpdate();

            // get the newly created id
            try (ResultSet resultSet = stmt.getGeneratedKeys()) {
                resultSet.next();
                long newId = resultSet.getLong(1);
                log.info("Booking created");
                return newId;
            }
        }
    }

    public long createRoomBooking(long dateFrom, long dateTo, long roomId, long bookingId) throws SQLException {
        log.info("createRoomBooking with booking id: "+bookingId);
        String sql = "insert into room_booking(date_of_arrival, date_of_departure, room_id, booking_id) values (?,?,?,?)";
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setFloat(1, dateFrom);
            stmt.setFloat(2, dateTo);
            stmt.setLong(3, roomId);
            stmt.setLong(4, bookingId);

            stmt.executeUpdate();
            log.info("Booking created");
            return roomId;

        }

    }


    public List<VacantHotelRoomDTO> getHotelRoomList(String city, long dateFrom, long dateTo, int numberGuests, int numberRooms) throws SQLException {
        log.info("getHotelRoomList, getting list of vacant hotelrooms");
        String sql = "SELECT room.id as room_id, room.max_capacity as room_capacity, room.price as room_price, room.type as room_type, hotel.id as hotel_id, hotel.name as hotel_name, hotel.address as hotel_address, hotel.city as hotel_city, hotel.distance_to_center as hotel_distance, hotel.raiting as hotel_raiting, hotel.head_quarter_id as headquarter_id " +
                "FROM room " +
                "INNER JOIN hotel ON room.hotel_id = hotel.id " +
                "WHERE room.max_capacity >= ? AND hotel.city LIKE ? AND room.id NOT IN ( " +
                "SELECT room_id  " +
                "FROM room_booking " +
                "WHERE date_of_arrival >= ? AND date_of_departure <= ?);";
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, numberGuests);
            stmt.setString(2, city);
            stmt.setLong(3, dateFrom);
            stmt.setLong(4, dateTo);

            List<VacantHotelRoomDTO> result = new ArrayList<VacantHotelRoomDTO>();

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {

                long roomId = resultSet.getLong("room_id");
                int roomCapacity = resultSet.getInt("room_capacity");
                int roomPrice = resultSet.getInt("room_price");
                String roomType = resultSet.getString("room_type");

                String hotelName = resultSet.getString("hotel_name");
                String hotelAddress = resultSet.getString("hotel_address");
                String hotelCity = resultSet.getString("hotel_city");
                double hotelDistance = resultSet.getDouble("hotel_distance");
                double hotelRaiting = resultSet.getInt("hotel_raiting");
                String hotelId = resultSet.getString("hotel_id");

                int headquarter = resultSet.getInt("headquarter_id");

                HotelDTO hotel = new HotelDTO(hotelId, hotelName, hotelAddress, hotelCity, hotelRaiting, hotelDistance, headquarter);

                VacantHotelRoomDTO vacantRoom = new VacantHotelRoomDTO(roomId, RoomType.valueOf(roomType), roomCapacity, roomPrice, hotel);

                result.add(vacantRoom);
            }
            log.info("Vacanthotel rooms have been found");
            return result;
        }

    }


    public List<BookingDTO> findBookings(String passportNumber) throws SQLException {
        log.info("findBookings with id: "+passportNumber);
        String sql = "select distinct booking.id as booking_id, hotel.name as hotel_name, hotel.address as hotel_address, hotel.city as hotel_city, hotel.distance_to_center as hotel_center_distance, hotel.raiting as hotel_raiting, hotel.head_quarter_id\n" +
                "from room " +
                "inner join room_booking on room_booking.room_id =room.id " +
                "inner join booking on booking.id = room_booking.booking_id " +
                "inner join hotel on hotel.id = room.hotel_id " +
                "where guest_passport_number = ?;";

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, passportNumber);

            List<BookingDTO> result = new ArrayList<BookingDTO>();

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                long bookingID = resultSet.getLong("booking_id");
                String hotelName = resultSet.getString("hotel_name");
                String hotelAddress = resultSet.getString("hotel_address");
                String hotelCity = resultSet.getString("hotel_city");
                double hotelRaiting = resultSet.getDouble("hotel_raiting");
                int hotelCenterDistance = resultSet.getInt("hotel_center_distance");

                List<RoomDTO> rooms = getRoomsFromBooking(bookingID);

                Hotel hotel = new Hotel(hotelName, hotelAddress, hotelCity, hotelRaiting, hotelCenterDistance, null, null);

                BookingDTO booking = new BookingDTO(bookingID, rooms, passportNumber, hotel);


                result.add(booking);
            }
            log.info("List of bookings has been found");
            return result;
        }
    }

    public List<RoomDTO> getRoomsFromBooking(long bookingId) throws SQLException {
        log.info("getRoomsFromBooking, find rooms on the bookingid: "+bookingId);
        String sql = "SELECT room.id as room_id, room.type as room_type, room.price as room_price, room.max_capacity as room_capacity, room_booking.booking_id as room_booking_id, room_booking.date_of_arrival as date_of_arrival, room_booking.date_of_departure as date_of_departure\n" +
                "FROM room\n" +
                "INNER JOIN room_booking ON room.id = room_booking.room_id\n" +
                "WHERE room_booking.booking_id = ?;";
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, bookingId);

            List<RoomDTO> result = new ArrayList<RoomDTO>();

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                long bookingID = resultSet.getLong("room_booking_id");

                long dateOfArrival = resultSet.getLong("date_of_arrival");
                long dateOfDeparture = resultSet.getLong("date_of_departure");
                long roomId = resultSet.getLong("room_id");
                String type = resultSet.getString("room_type");
                double price = resultSet.getDouble("room_price");
                int maxCapacity = resultSet.getInt("room_capacity");

                RoomDTO room = new RoomDTO(dateOfArrival, dateOfDeparture, roomId, type, price, maxCapacity);

                result.add(room);
            }
            log.info("Rooms found on bookingId");
            return result;
        }
    }

    public boolean cancelRoomBooking(long bookingId) throws SQLException {
        log.info("cancelRoomBooking on bookingId: "+bookingId);
        String sql = "delete from room_booking where booking_id = ?";
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, bookingId);

            stmt.execute();
        }
        log.info("Room booking has been canceled");
        return true;
    }

    public boolean cancelBooking(long bookingId) throws SQLException {
        log.info("cancelBooking on bookingId: "+bookingId);
        String sql = "delete from booking where id = ?";
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, bookingId);

            stmt.execute();
        }
        log.info("Booking has been canceled");
        return true;
    }
}