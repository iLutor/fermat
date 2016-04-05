package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_local_community.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Eleazar (eorono@protonmail.com) on 2/04/16.
 */
public class ChatActorLocalAlreadyExistException extends FermatException {

    private static final String DEFAULT_MESSAGE = "ACTOR ALREADY EXISTS EXCEPTION";

    public ChatActorLocalAlreadyExistException (String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public ChatActorLocalAlreadyExistException (Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
