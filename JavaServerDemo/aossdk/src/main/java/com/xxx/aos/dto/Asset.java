package com.xxx.aos.dto;

import lombok.Data;

@Data
public class Asset {
    private String tokenName;
    private String coinmarketId;
    private String contractAddress;
    private String chainType;
}
