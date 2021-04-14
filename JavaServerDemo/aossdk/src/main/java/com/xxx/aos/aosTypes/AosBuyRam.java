package com.xxx.aos.aosTypes;

public class AosBuyRam {

    private String payer;

    private String receiver;

    private String quant;

    public String getActionName() {
        return "buyram";
    }

    public AosBuyRam(String payer, String receiver, String quant ) {
//        this.payer = new TypeAccountName(payer);
//        this.receiver = new TypeAccountName(receiver);
        this.payer = payer;
        this.receiver = receiver;
        this.quant = quant;
    }
}
