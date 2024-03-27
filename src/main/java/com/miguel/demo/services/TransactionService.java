package com.miguel.demo.services;

import com.miguel.demo.domain.transaction.Transaction;
import com.miguel.demo.domain.transaction.TransactionDTO;
import com.miguel.demo.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TransactionRepository repository;
    @Autowired
    private NotificationService notificationService;


    public Transaction createTransaction(TransactionDTO transaction) throws Exception {
        var payer = this.userService.findUserById(transaction.payerId());
        var payee = this.userService.findUserById(transaction.payeeId());

        userService.validateUser(payer, transaction.amout());

        boolean isAthorized = authrorizeTransaction();

        if(!isAthorized){
            throw new Exception("Transação não aotorizada.");
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.amout());
        newTransaction.setPayer(payer);
        newTransaction.setPayee(payee);
        newTransaction.setTransactionTime(LocalDateTime.now());

        payer.setBalance(payer.getBalance().subtract(transaction.amout()));
        payee.setBalance(payee.getBalance().add(transaction.amout()));

        this.repository.save(newTransaction);
        this.userService.saveUser(payee);
        this.userService.saveUser(payer);
        notificationService.sendNotification(payer, "Transação realiazada com sucesso!");
        notificationService.sendNotification(payer, "Transação recebida com sucesso!");
            
        return newTransaction;
    }
    public boolean authrorizeTransaction(){
        var response = restTemplate.getForEntity("https://run.moky.io/v3/8fafdd68-a89b-496f-496f-8c9a-3442cf3dae6", Map.class);

        if(response.getStatusCode() == HttpStatus.OK){
            String message = (String) response.getBody().get("message");
            return "Autorizado".equalsIgnoreCase(message);
        }else return false;
    }
}
