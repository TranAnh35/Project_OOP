package demo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import demo.Entity.Payment;
import demo.Data.DataBase;

public class PaymentDAO {

    public void addPayment(Payment payment) {
        try (Connection conn = DataBase.connecDb()) {
            String sql = "INSERT INTO payment (paymentId, studentId, amount, date) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, payment.getPaymentId());
            pstmt.setString(2, payment.getStudentId());
            pstmt.setDouble(3, payment.getAmount());
            pstmt.setString(4, payment.getDate());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addPayment(String studentId, String courseId, double amount) {
        try (Connection conn = DataBase.connecDb()) {
            String sql = "INSERT INTO payment (studentId, courseId, amount) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, studentId);
            pstmt.setString(2, courseId);
            pstmt.setDouble(3, amount);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getPaymentId(String studentId, String courseId){
        try (Connection conn = DataBase.connecDb()) {
            String sql = "SELECT paymentId FROM payment WHERE studentId = ? AND courseId = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, studentId);
            pstmt.setString(2, courseId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                return rs.getInt("paymentId");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void deletePayment(String studentId, String courseId){
        int id = getPaymentId(studentId, courseId);
        try (Connection conn = DataBase.connecDb()) {
            String sql = "DELETE FROM payment WHERE paymentId = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Payment> getPaymentsByStudentId(String studentId) {
        List<Payment> payments = new ArrayList<>();
        try (Connection conn = DataBase.connecDb()) {
            String sql = "SELECT * FROM payment WHERE studentId = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(rs.getString("paymentId"));
                payment.setStudentId(rs.getString("studentId"));
                payment.setAmount(rs.getDouble("amount"));
                payment.setDate(rs.getString("date"));
                payments.add(payment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return payments;
    }

    public double getTotalPaymentByStudentId(String studentId) {
        double total = 0;
        try (Connection conn = DataBase.connecDb()) {
            String sql = "SELECT SUM(amount) FROM payment WHERE studentId = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }
}
