package com.xxx.aos.aosapi;

import lombok.Data;

@Data
public class GetBalanceReq {
    String code;
    String account;
    String symbol;
}
