
package com.xxx.aos.chain;

import com.google.gson.annotations.Expose;
import com.xxx.aos.aosTypes.TypeAccountName;
import com.xxx.aos.aosTypes.TypeScopeName;
import lombok.Data;



@Data
public class DataAccessInfo {
    //public enum Type { read, write };

    @Expose
    private String type; // access type

    @Expose
    private TypeAccountName code;

    @Expose
    private TypeScopeName scope;

    @Expose
    private long   sequence; // uint64_t
}
