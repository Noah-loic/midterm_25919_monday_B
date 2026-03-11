package auca.ac.rw.restfullApiAssignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import auca.ac.rw.restfullApiAssignment.modal.*;
import auca.ac.rw.restfullApiAssignment.repository.PaymentRepository;
import java.util.List;

@Service
public class PaymentService {
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    public String processPayment(Payment payment) {
        payment.setStatus(EPaymentStatus.COMPLETED);
        payment.setReceiptNumber("RCP" + System.currentTimeMillis());
        paymentRepository.save(payment);
        return "Payment processed successfully";
    }
    
    public List<Payment> getStudentPayments(String studentId) {
        return paymentRepository.findByStudent_StudentId(studentId);
    }
    
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
