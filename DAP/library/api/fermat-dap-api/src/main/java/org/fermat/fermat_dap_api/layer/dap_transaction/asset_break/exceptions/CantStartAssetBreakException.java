package org.fermat.fermat_dap_api.layer.dap_transaction.asset_break.exceptions;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 2/05/16.
 */

public class CantStartAssetBreakException extends DAPException {

    //CONSTRUCTORS

    public CantStartAssetBreakException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantStartAssetBreakException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantStartAssetBreakException(Exception cause, String context) {
        super(DEFAULT_MESSAGE, cause, context, null);
    }

    public CantStartAssetBreakException(Exception cause) {
        super(DEFAULT_MESSAGE, cause);
    }

    public CantStartAssetBreakException() {
        super(DEFAULT_MESSAGE);
    }
}
