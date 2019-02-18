package com.princeli.micro.services.mvc.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionStatus;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-02-18 11:41
 **/
@Component("myTxName")
public class MyPlatformTransactionManager implements PlatformTransactionManager {

    @Override
    public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
        return new DefaultTransactionStatus(null,true,true,definition.isReadOnly(), true, null);
    }

    @Override
    public void commit(TransactionStatus status) throws TransactionException {
        System.out.println("Commit()...");
    }

    @Override
    public void rollback(TransactionStatus status) throws TransactionException {
        System.out.println("Rollback()...");
    }
}