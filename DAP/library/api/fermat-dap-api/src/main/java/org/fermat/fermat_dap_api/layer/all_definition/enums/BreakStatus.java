package org.fermat.fermat_dap_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 2/05/16.
 */
public enum BreakStatus implements FermatEnum {

    //ENUM DECLARATION

    ;

    //VARIABLE DECLARATION

    private String code;

    //CONSTRUCTORS

    BreakStatus(String code) {
        this.code = code;
    }

    //PUBLIC METHODS

    public static BreakStatus getByCode(String code) throws InvalidParameterException {
        for (BreakStatus fenum : values()) {
            if (fenum.getCode().equals(code)) return fenum;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the BreakStatus enum.");
    }

    //PRIVATE METHODS

    //GETTER AND SETTERS

    @Override
    public String getCode() {
        return code;
    }
}
