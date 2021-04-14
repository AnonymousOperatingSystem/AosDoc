package com.xxx.aos.aosTypes;


public class TypeWaitWeight implements AosType.Packer {
    private int    mWaitSec; // uint32_t
    private short  mWeight;

    public TypeWaitWeight( long uint32WaitSec, int uint16Weight){
        mWaitSec= (int)( uint32WaitSec & 0xFFFFFFFF );
        mWeight = (short)( uint16Weight & 0xFFFF );
    }

    @Override
    public void pack(AosType.Writer writer) {
        writer.putIntLE( mWaitSec );
        writer.putShortLE( mWeight);
    }
}
