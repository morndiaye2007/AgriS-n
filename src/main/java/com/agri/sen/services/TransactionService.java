package com.agri.sen.services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.agri.sen.model.TransactionDTO;

import java.util.Map;

public interface TransactionService {

    TransactionDTO createTransaction(TransactionDTO transactionDTO);
    TransactionDTO  updateTransaction(TransactionDTO transactionDTO);
    void deleteTransaction(Long id);
    TransactionDTO getTransaction(Long id);
    Page<TransactionDTO> getAllTransactions(Map<String, String> searchParams, Pageable pageable);


}
