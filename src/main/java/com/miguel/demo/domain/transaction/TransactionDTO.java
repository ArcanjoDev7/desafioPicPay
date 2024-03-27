package com.miguel.demo.domain.transaction;

import java.math.BigDecimal;

public record TransactionDTO(
        BigDecimal amout,

        Long payerId,

        Long payeeId)
{
}
