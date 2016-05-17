package org.fermat.fermat_dap_api.layer.all_definition.events;

import org.fermat.fermat_dap_api.layer.all_definition.enums.EventType;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;

/**
 * Created by Nerio on 15/10/15.
 */
public class ReceivedNewDigitalAssetMetadataNotificationEvent extends AbstractDAPEvent {


    //VARIABLE DECLARATION
    private DAPMessage message;
    private org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser actorAssetUser;

    //CONSTRUCTORS
    public ReceivedNewDigitalAssetMetadataNotificationEvent(EventType eventType) {
        super(eventType);
    }
    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    public DAPMessage getMessage() {return message;}

    public void setMessage(DAPMessage message) {this.message = message;}

    public org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser getActorAssetUser() {
        return actorAssetUser;
    }

    public void setActorAssetUser(org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser actorAssetUser) {
        this.actorAssetUser = actorAssetUser;
    }
}
