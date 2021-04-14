package com.xxx.aos.dto;

import lombok.Data;

@Data
public class Trx {
    private String id;
    private TransactionInner transaction;
}
