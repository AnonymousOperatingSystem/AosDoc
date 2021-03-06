

package com.xxx.aos.aosTypes;


import com.xxx.aos.crypto.digest.Sha256;
import com.xxx.aos.crypto.util.HexUtils;


public class TypeChainId {
    private Sha256 mId;

    public TypeChainId() {
        mId = Sha256.ZERO_HASH;
    }

    public TypeChainId(String sha256_string) {
        mId = new Sha256(HexUtils.toBytes(sha256_string));
    }

    public byte[] getBytes() {
        return mId.getBytes();
    }
}
