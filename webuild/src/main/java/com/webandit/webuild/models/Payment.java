package com.webandit.webuild.models;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentMethodCreateParams;

public class Payment {

    // Set your secret key here
    private static final String SECRET_KEY = "sk_test_51OoUFtBZpJDBcUiHu12Mf2JS1XnbT15zVgV7SUGxKXKVEL1samRuq93jaQ0Pf4yAq6R4JSM23o4OyTEO35oJNC5n00Z3H4gECB";

    public static void main(String[] args) {
        Stripe.apiKey = SECRET_KEY;

        try {
            // Retrieve your account information
            Account account = Account.retrieve();
            System.out.println("Account ID: " + account.getId());
            // Print other account information as needed
        } catch (StripeException e) {
            e.printStackTrace();
        }

        // Process payment
        processPayment();
    }

    private static void processPayment() {
        try {
            // Set your secret key here
            Stripe.apiKey = SECRET_KEY;

            // Create a PaymentIntent with other payment details
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(1000L) // Amount in cents (e.g., $10.00)
                    .setCurrency("usd")
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);

            // If the payment was successful, display a success message
            System.out.println("Payment successful. PaymentIntent ID: " + intent.getId());
        } catch (StripeException e) {
            // If there was an error processing the payment, display the error message
            System.out.println("Payment failed. Error: " + e.getMessage());
        }
    }
}