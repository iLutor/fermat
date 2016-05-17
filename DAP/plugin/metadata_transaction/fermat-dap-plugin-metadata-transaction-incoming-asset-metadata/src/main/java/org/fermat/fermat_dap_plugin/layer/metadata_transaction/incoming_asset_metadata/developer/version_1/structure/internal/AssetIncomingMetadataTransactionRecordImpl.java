package org.fermat.fermat_dap_plugin.layer.metadata_transaction.incoming_asset_metadata.developer.version_1.structure.internal;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPMessageSubject;
import org.fermat.fermat_dap_api.layer.all_definition.enums.MetadataTransactionStatus;
import org.fermat.fermat_dap_api.layer.dap_actor.DAPActor;
import org.fermat.fermat_dap_api.layer.dap_metadata_transaction.asset_incoming.interfaces.AssetIncomingMetadataTransactionRecord;

import java.util.Date;
import java.util.UUID;

/**
 * Luis Torres (lutor1106@gmail.com") 11/05/16
 */
public class AssetIncomingMetadataTransactionRecordImpl implements AssetIncomingMetadataTransactionRecord {

    //VARIABLE DECLARATION
    private final UUID id;
    private final DigitalAssetMetadata digitalAssetMetadata;
    private final MetadataTransactionStatus metadataTransactionStatus;
    private final Date date;
    private final int failureCount;
    private final DAPMessageSubject subject;



    //CONSTRUCTORS
    public AssetIncomingMetadataTransactionRecordImpl(UUID id, DigitalAssetMetadata digitalAssetMetadata, MetadataTransactionStatus metadataTransactionStatus, Date date, int failureCount, DAPMessageSubject subject) {
        this.id = id;
        this.digitalAssetMetadata = digitalAssetMetadata;
        this.metadataTransactionStatus = metadataTransactionStatus;
        this.date = date;
        this.failureCount = failureCount;
        this.subject = subject;
    }
    //PUBLIC METHODS
    @Override
    public UUID getRecordId() { return id;}

    @Override
    public DigitalAssetMetadata getAssetMetadata() {
        return digitalAssetMetadata;
    }

    @Override
    public MetadataTransactionStatus getStatus() {
        return metadataTransactionStatus;
    }

    @Override
    public Date getStartTime() {
        return date;
    }

    @Override
    public int getFailureCount() {
        return failureCount;
    }

    @Override
    public DAPMessageSubject getSubject() {
        return subject;
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}