package com.xxx.aos.dto;

import lombok.Data;

import java.util.List;

@Data
public class TransactionInner {
    List<Action> actions;
}
