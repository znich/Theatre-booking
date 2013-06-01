package by.academy.DAO.payment;

import by.academy.Model.PaymentData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 09.05.13
 * Time: 15:03
 * To change this template use File | Settings | File Templates.
 */
public interface PaymentDAO {
    public PaymentData getPaymentMethodById(int id);
    public List<PaymentData> getAllPayments();
    public int addPaymentMethod(PaymentData payment);
    public int editPaymentMethod(PaymentData payment);
    public int deletePaymentMethod(int id);
    public PaymentData createPayment(ResultSet rs) throws SQLException;
    public void closeConnection();
}
