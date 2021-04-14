package com.xxx.eosuse;

import lombok.Data;

@Data
public class CreateAccountByFreeCodeReq {
    String account_name;
    String active_key;
    String owner_key;
}
