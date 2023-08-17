package com.org.ps.martek.service;

import com.org.ps.martek.dto.PaymentRequest;
import com.org.ps.martek.dto.PaymentResponse;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(String orderId);
}
