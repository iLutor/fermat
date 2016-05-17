package org.fermat.fermat_dap_api.layer.all_definition.events;

import org.fermat.fermat_dap_api.layer.all_definition.enums.EventType;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import org.fermat.fermat_dap_api.layer.dap_actor.DAPActor;
import org.fermat.fermat_dap_api.layer.dap_metadata_transaction.MetadataTransactionRecord;
import org.fermat.fermat_dap_api.layer.dap_metadata_transaction.asset_incoming.interfaces.AssetIncomingMetadataTransactionRecord;

/**
 * Luis Torres (lutor1106@gmail.com") 11/05/16
 */
public class NewIncomingMetadataEvent extends AbstractDAPEvent  {

    //VARIABLE DECLARATION
    private AssetIncomingMetadataTransactionRecord record;


    private DAPActor actorFrom;
    private DAPActor actorTo;



    //CONSTRUCTORS
    public NewIncomingMetadataEvent(AssetIncomingMetadataTransactionRecord incomingRecord,DAPActor actorFrom,DAPActor actorTo) {
        super(EventType.NEW_INCOMING_ASSET_METADATA);
        this.record = incomingRecord;
        this.actorFrom = actorFrom;
        this.actorTo = actorTo;
    }

    public NewIncomingMetadataEvent() {
        super(EventType.NEW_INCOMING_ASSET_METADATA);
    }

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS
    public DAPActor getActorFrom() {
        return actorFrom;
    }

    public void setActorFrom(DAPActor actorFrom) {
        this.actorFrom = actorFrom;
    }

    public DAPActor getActorTo() {
        return actorTo;
    }

    public void setActorTo(DAPActor actorTo) {
        this.actorTo = actorTo;
    }

    public  AssetIncomingMetadataTransactionRecord getRecord() {
        return record;
    }

    public void setRecord( AssetIncomingMetadataTransactionRecord incomingRecord) {
        this.record = incomingRecord;
    }
    //INNER CLASSES
}