package by.academy.DAO.payment;

import by.academy.Model.PaymentData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 09.05.13
 * Time: 15:18
 * To change this template use File | Settings | File Templates.
 */
public class ORACLEPaymentDAO implements PaymentDAO {
    private Connection connection = null;

    public ORACLEPaymentDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public PaymentData getPaymentMethodById(int id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<PaymentData> getAllPayments() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int addPaymentMethod(PaymentData payment) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int editPaymentMethod(PaymentData payment) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int deletePaymentMethod(int id) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PaymentData createPayment(ResultSet rs) throws SQLException {
        PaymentData payment = new PaymentData();

        payment.setId(rs.getInt("METHOD_ID"));
        payment.setName(rs.getString("METHOD_VALUE"));

        return payment;
    }
}
