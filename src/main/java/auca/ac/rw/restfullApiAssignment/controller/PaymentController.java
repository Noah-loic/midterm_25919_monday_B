package auca.ac.rw.restfullApiAssignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import auca.ac.rw.restfullApiAssignment.modal.Payment;
import auca.ac.rw.restfullApiAssignment.service.PaymentService;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    
    @Autowired
    private PaymentService paymentService;
    
    @PostMapping(value = "/process", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> processPayment(@RequestBody Payment payment) {
        String result = paymentService.processPayment(payment);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    
    @GetMapping(value = "/student/{studentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Payment>> getStudentPayments(@PathVariable String studentId) {
        return new ResponseEntity<>(paymentService.getStudentPayments(studentId), HttpStatus.OK);
    }
    
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Payment>> getAllPayments() {
        return new ResponseEntity<>(paymentService.getAllPayments(), HttpStatus.OK);
    }
}
