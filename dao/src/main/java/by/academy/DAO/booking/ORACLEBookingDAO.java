package by.academy.DAO.booking;

import by.academy.DAO.user.ORACLEUserDAO;
import by.academy.DAO.payment.ORACLEPaymentDAO;
import by.academy.Model.BookingData;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 09.05.13
 * Time: 15:10
 * To change this template use File | Settings | File Templates.
 */
public class ORACLEBookingDAO implements BookingDAO {
    private Connection connection = null;

    public ORACLEBookingDAO(Connection connection) {
        this.connection = connection;
    }

    //-------------------------------------------------------------------------------------------------
    public BookingData getBookingById(int id, int langId) {
        String bookingQuery = "SELECT * FROM BOOKING INNER JOIN USERS ON BOOKING.USER_ID=USERS.USER_ID" +
                "INNER JOIN PAYMENT_METHOD ON (BOOKING.BOOKING_PAYMENT_METHOD = PAYMENT_METHOD.METHOD_ID AND PAYMENT_METHOD.LANG_ID=? )" +
                " WHERE  BOOKING_ID= ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        BookingData bookingData = new BookingData();
        try {
            ps = connection.prepareStatement(bookingQuery);
            ps.setInt(1, langId);
            ps.setInt(2, id);

            rs = ps.executeQuery();
            if (rs.next()) {
                bookingData = createBooking(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, ps);
        }
        return bookingData;


    }

    //----------------------------------------------------------------------------------------------------------------------
    @Override
    public List<BookingData> getAllBooking(int langId) {
        String bookingQuery = "SELECT * FROM BOOKING INNER JOIN USERS ON BOOKING.USER_ID=USERS_USER_ID" +
                "INNER JOIN PAYMENT_METHOD ON (BOOKING.BOOKING_PAYMENT_METHOD = PAYMENT_METHOD.METHOD_ID AND PAYMENT_METHOD.LANG_ID=? )";
        PreparedStatement ps = null;
        ResultSet rs = null;
        BookingData bookingData = new BookingData();
        ArrayList<BookingData> allBooking = new ArrayList<BookingData>();
        try {
            ps = connection.prepareStatement(bookingQuery);
            ps.setInt(1, langId);
            rs = ps.executeQuery();
            while (rs.next()) {
                bookingData = createBooking(rs);
                allBooking.add(bookingData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, ps);
        }
        return allBooking;
    }

    //----------------------------------------------------------------------------------------------------------------------
    public List<BookingData> getBookingByUser(int id, int langId) {
        String bookingQuery = "SELECT * FROM BOOKING INNER JOIN USERS ON BOOKING.USER_ID=USERS_USER_ID" +
                "INNER JOIN PAYMENT_METHOD ON (BOOKING.BOOKING_PAYMENT_METHOD = PAYMENT_METHOD.METHOD_ID AND PAYMENT_METHOD.LANG_ID=? )" +
                " WHERE BOOKING.USER_ID= ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        BookingData bookingData = new BookingData();
        ArrayList<BookingData> ticketsByEventList = new ArrayList<BookingData>();

        try {
            ps = connection.prepareStatement(bookingQuery);
            ps.setInt(1, langId);
            ps.setInt(2, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                bookingData = createBooking(rs);
                ticketsByEventList.add(bookingData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, ps);
        }

        return ticketsByEventList;
    }

    //----------------------------------------------------------------------------------------------------------------------
    @Override
    public int addBooking(BookingData booking) {

        String addQuery = "INSERT INTO BOOKING (USER_ID, BOOKING_MADE_DATE, BOOKING_EXPIRATION_DATE, BOOKING_TICKET_COUNT, BOOKING_PAYMENT_METHOD)" +
                " VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps = null;
        int row = 0;
        int bookingId = 0;

        try {
            ps = connection.prepareStatement(addQuery, new String[]{"BOOKING_ID"});


            ps.setInt(1, booking.getUser().getId());
            ps.setLong(2, booking.getMadeDate().getTime());
            ps.setLong(3, booking.getForDate().getTime());
            ps.setInt(4, booking.getTicketCount());
            ps.setInt(5, booking.getPaymentMethod().getId());
            ps.addBatch();


            row = ps.executeUpdate();
            if (row > 0)
                bookingId = getGeneratedKey(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(null, ps);
        }

        return bookingId;
    }

    //----------------------------------------------------------------------------------------------------------------------
    @Override
    public int editBooking(BookingData booking) {


        String queryBooking = "UPDATE BOOKING SET USER_ID=?,  BOOKING_MADE_DATE= ?, BOOKING_EXPIRATION_DATE = ?," +
                " BOOKING_TICKET_COUNT=?, BOOKING_PAYMENT_METHOD= ? WHERE BOOKING_ID = ?";
        int row = 0;

        PreparedStatement psBooking = null;
        try {
            psBooking = connection.prepareStatement(queryBooking);

            psBooking.setInt(1, booking.getUser().getId());
            psBooking.setLong(2, booking.getMadeDate().getTime());
            psBooking.setLong(3, booking.getForDate().getTime());
            psBooking.setInt(4, booking.getTicketCount());
            psBooking.setInt(5, booking.getPaymentMethod().getId());

            row = psBooking.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            closeAll(null, psBooking);
        }


        return row;
    }

    //----------------------------------------------------------------------------------------------------------------------
    @Override
    public int deleteBooking(int bookingId) {
        String deleteBookingQuery = "DELETE FROM BOOKING WHERE BOOKING_ID = " + bookingId;
        Statement statement;
        int rows = 0;
        try {
            statement = connection.createStatement();
            rows = statement.executeUpdate(deleteBookingQuery);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return rows;
    }

    //----------------------------------------------------------------------------------------------------------------------
    public BookingData createBooking(ResultSet rs) throws SQLException {
        BookingData booking = new BookingData();
        ORACLEUserDAO userDAO = new ORACLEUserDAO(connection);
        ORACLEPaymentDAO paymentDAO = new ORACLEPaymentDAO(connection);

        booking.setId(rs.getInt("BOOKING_ID"));
        booking.setUser(userDAO.createUser(rs));
        booking.setForDate(new Date(rs.getLong("BOOKING_EXPIRATION_DATE")));
        booking.setMadeDate(new Date(rs.getLong("BOOKING_MADE_DATE")));
        booking.setTicketCount(rs.getInt("BOOKING_TICKET_COUNT"));
        booking.setPaymentMethod(paymentDAO.createPayment(rs));

        return booking;
    }

    //-----------------------------------------------------------------------------------------------------------------------
    @Override
    public List<Integer> deleteExpiredBookings(Date currentDate) {

        String selectBookings = "SELECT BOOKING_ID FROM BOOKING WHERE BOOKING_EXPIRATION_DATE < ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Integer> bookingIdList = new ArrayList();
        boolean flag = false;
        try {
            ps = connection.prepareStatement(selectBookings);

            ps.setLong(1, currentDate.getTime());

            rs = ps.executeQuery();
            while (rs.next()) {

                Integer bookingId = rs.getInt(1);
                bookingIdList.add(bookingId);
            }

            String deleteBookings = "DELETE FROM  BOOKING WHERE BOOKING_EXPIRATION_DATE < ?";

            ps = connection.prepareStatement(deleteBookings);

            ps.setLong(1, currentDate.getTime());

            int row = ps.executeUpdate();

            if (bookingIdList.size() == row) {
                flag = true;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            closeAll(rs, ps);
        }
        if (flag) {
            return bookingIdList;
        } else
            return null;
    }

    //---------------------------------------------------------------------------------------------------------------------
    private int getGeneratedKey(PreparedStatement pStatement) throws SQLException {

        ResultSet rsProp = pStatement.getGeneratedKeys(); // получние автоматических ключей

        int propId = 0; // автосгенерированный ID добавленного поля

        while (rsProp.next()) {

            propId = rsProp.getInt(1);

        }
        rsProp.close();
        return propId;
    }

    //----------------------------------------------------------------------------------------------------------------------
    private void closeAll(ResultSet rs, PreparedStatement ps) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException ex) {
            }
        }
    }

    public void closeConnection () {
    	try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

