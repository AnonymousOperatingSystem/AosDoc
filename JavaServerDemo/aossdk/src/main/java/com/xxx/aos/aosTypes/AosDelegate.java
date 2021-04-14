package com.xxx.aos.aosTypes;

import com.google.gson.annotations.Expose;

public class AosDelegate {

    @Expose
    private String from;

    @Expose
    private String receiver;

    @Expose
    private String stake_net_quantity;

    @Expose
    private String stake_cpu_quantity;

    @Expose
    private int transfer;

    public String getActionName() {
        return "delegatebw";
    }


    public AosDelegate(String payer, String receiver, String stake_net_quantity, String stake_cpu_quantity, Boolean transfer) {

        this.from = payer;
        this.receiver = receiver;
        this.stake_net_quantity = stake_net_quantity;
        this.stake_cpu_quantity = stake_cpu_quantity;
        if(transfer){
            this.transfer = 1;
        }
        else{
            this.transfer = 0;
        }
    }
}
