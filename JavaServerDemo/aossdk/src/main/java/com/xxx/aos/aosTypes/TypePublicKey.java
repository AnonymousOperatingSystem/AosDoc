package com.xxx.aos.aosTypes;


import com.xxx.aos.crypto.ec.AosPublicKey;


public class TypePublicKey implements AosType.Packer {
    private static final byte PACK_VAL_CURVE_PARAM_TYPE_K1 = 0;
    private static final byte PACK_VAL_CURVE_PARAM_TYPE_R1 = 1;

    private final AosPublicKey mPubKey;

    public static TypePublicKey from(AosPublicKey publicKey) {
        return new TypePublicKey(publicKey);
    }

    public TypePublicKey( AosPublicKey publicKey ) {
        mPubKey = publicKey;
    }


    @Override
    public void pack(AosType.Writer writer) {
        writer.putVariableUInt( mPubKey.isCurveParamK1() ? PACK_VAL_CURVE_PARAM_TYPE_K1 : PACK_VAL_CURVE_PARAM_TYPE_R1 );

        writer.putBytes( mPubKey.getBytes());
    }
}
