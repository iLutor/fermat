package org.fermat.fermat_dap_api.layer.all_definition.exceptions;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 2/05/16.
 */

public class CantNotifyEventException extends DAPException {

    //CONSTRUCTORS

    public CantNotifyEventException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantNotifyEventException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantNotifyEventException(Exception cause, String context) {
        super(DEFAULT_MESSAGE, cause, context, null);
    }

    public CantNotifyEventException(Exception cause) {
        super(DEFAULT_MESSAGE, cause);
    }

    public CantNotifyEventException() {
        super(DEFAULT_MESSAGE);
    }
}
