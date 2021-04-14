package com.xxx.aos.aosapi;


import com.google.gson.annotations.Expose;
import com.xxx.aos.crypto.ec.AosPublicKey;


import java.util.ArrayList;
import java.util.List;


public class RequiredKeysResponse {

    @Expose
    private List<String> required_keys ;

    public List<AosPublicKey> getKeys() {
        if ( null == required_keys ){
            return new ArrayList<>();
        }

        ArrayList<AosPublicKey> retKeys = new ArrayList<>(required_keys.size());
        for ( String pubKey: required_keys ){
            retKeys.add( new AosPublicKey( pubKey));
        }

        return retKeys;
    }
}
