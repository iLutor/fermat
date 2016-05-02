package org.fermat.fermat_dap_api.layer.swap_transaction.exceptions;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 2/05/16.
 */

public class CantCheckSwapProgressException extends DAPException {

    //CONSTRUCTORS

    public CantCheckSwapProgressException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantCheckSwapProgressException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantCheckSwapProgressException(Exception cause, String context) {
        super(DEFAULT_MESSAGE, cause, context, null);
    }

    public CantCheckSwapProgressException(Exception cause) {
        super(DEFAULT_MESSAGE, cause);
    }

    public CantCheckSwapProgressException() {
        super(DEFAULT_MESSAGE);
    }
}
