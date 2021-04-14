package com.xxx.aos.dto;

import com.google.gson.annotations.Expose;
import com.xxx.aos.crypto.util.HexUtils;
import com.xxx.aos.aosTypes.AosByteWriter;
import com.xxx.aos.aosTypes.AosType;
import com.xxx.aos.aosTypes.TypeAccountName;
import com.xxx.aos.aosTypes.TypeAsset;


public class AosTransfer implements AosType.Packer {
    @Expose
    private TypeAccountName from;

    @Expose
    private TypeAccountName to;

    @Expose
    private TypeAsset quantity;

    @Expose
    private String memo;

    public AosTransfer(String from, String to, long quantity, String memo ) {
        this( new TypeAccountName(from), new TypeAccountName(to), quantity, memo );
    }

    public AosTransfer(TypeAccountName from, TypeAccountName to, long quantity, String memo ) {
        this.from = from;
        this.to = to;
        this.quantity = new TypeAsset(quantity);
        this.memo = memo != null ? memo : "";
    }

    public String getActionName() {
        return "transfer";
    }


    @Override
    public void pack(AosType.Writer writer) {

        from.pack(writer);
        to.pack(writer);

        writer.putLongLE(quantity.getAmount());

        writer.putString(memo);
    }

    public String getAsHex() {
        AosType.Writer writer = new AosByteWriter(128);
        pack(writer);

        return HexUtils.toHex( writer.toBytes() );
    }
}
