package org.fermat.fermat_dap_api.layer.dap_transaction.asset_break.exceptions;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 2/05/16.
 */

public class CantCheckAssetBreakProgressException extends DAPException {

    //CONSTRUCTORS

    public CantCheckAssetBreakProgressException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantCheckAssetBreakProgressException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantCheckAssetBreakProgressException(Exception cause, String context) {
        super(DEFAULT_MESSAGE, cause, context, null);
    }

    public CantCheckAssetBreakProgressException(Exception cause) {
        super(DEFAULT_MESSAGE, cause);
    }

    public CantCheckAssetBreakProgressException() {
        super(DEFAULT_MESSAGE);
    }
}
