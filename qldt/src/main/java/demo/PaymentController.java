package demo;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import demo.DAO.PaymentDAO;
import demo.Entity.Payment;

@WebServlet("/payments/*")
public class PaymentController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PaymentDAO paymentDAO;

    public PaymentController() {
        this.paymentDAO = new PaymentDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            addPayment(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            String[] splits = pathInfo.split("/");
            if (splits.length == 2 && splits[1] != null) {
                getPaymentsByStudent(request, response, splits[1]);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    private void addPayment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String paymentId = UUID.randomUUID().toString(); // Tạo ID ngẫu nhiên cho mỗi thanh toán
        String studentId = request.getParameter("studentId");
        double amount;
        try {
            amount = Double.parseDouble(request.getParameter("amount"));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid amount");
            return;
        }
        String date = request.getParameter("date");

        Payment payment = new Payment(paymentId, studentId, amount, date);
        paymentDAO.addPayment(payment);

        response.sendRedirect(request.getContextPath() + "/paymentSuccess.jsp");
    }

    private void getPaymentsByStudent(HttpServletRequest request, HttpServletResponse response, String studentId) throws ServletException, IOException {
        List<Payment> payments = paymentDAO.getPaymentsByStudentId(studentId);

        request.setAttribute("payments", payments);
        request.getRequestDispatcher("/view/payments.jsp").forward(request, response);
    }
}

    

