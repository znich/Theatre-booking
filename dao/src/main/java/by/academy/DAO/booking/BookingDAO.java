package by.academy.DAO.booking;

import by.academy.Model.BookingData;


import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 09.05.13
 * Time: 15:01
 * To change this template use File | Settings | File Templates.
 */
public interface BookingDAO {
    public List<BookingData>  getAllBooking(int langId);
    public BookingData getBookingById(int id, int langId);
    public List<BookingData> getBookingByUser(int id, int langId);
    public int addBooking(BookingData booking);
    public int editBooking(BookingData booking) ;
    public int deleteBooking (int id);
    public List<Integer> deleteExpiredBookings (Date currentDate);
}
