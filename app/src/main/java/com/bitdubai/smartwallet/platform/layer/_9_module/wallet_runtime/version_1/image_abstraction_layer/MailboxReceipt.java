package com.bitdubai.smartwallet.platform.layer._9_module.wallet_runtime.version_1.image_abstraction_layer;

/**
 * Created by ciencias on 15.01.15.
 */
public class MailboxReceipt implements Throwaway, Displayable, Movable {
    @Override
    public EnvelopeStatus getPosition() {
        return null;
    }

    @Override
    public Void moveTo(Position pPosition) {
        return null;
    }
}
