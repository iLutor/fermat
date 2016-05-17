package org.fermat.fermat_dap_plugin.layer.metadata_transaction.incoming_asset_metadata.developer.version_1.structure.functional;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;

import org.fermat.fermat_dap_api.layer.all_definition.enums.MetadataTransactionStatus;
import org.fermat.fermat_dap_api.layer.all_definition.events.NewIncomingMetadataEvent;
import org.fermat.fermat_dap_api.layer.all_definition.events.ReceivedNewDigitalAssetMetadataNotificationEvent;
import org.fermat.fermat_dap_api.layer.dap_metadata_transaction.asset_incoming.interfaces.AssetIncomingMetadataTransactionRecord;
import org.fermat.fermat_dap_api.layer.metadata.interfaces.DigitalAssetMetadataManager;
import org.fermat.fermat_dap_plugin.layer.metadata_transaction.incoming_asset_metadata.developer.version_1.IncomingAssetMetadataPluginRoot;
import org.fermat.fermat_dap_plugin.layer.metadata_transaction.incoming_asset_metadata.developer.version_1.structure.database.IncomingAssetMetadataDAO;
import org.fermat.fermat_dap_plugin.layer.metadata_transaction.incoming_asset_metadata.developer.version_1.structure.events.IncomingAssetMetadataMonitorAgent;
import org.fermat.fermat_dap_plugin.layer.metadata_transaction.incoming_asset_metadata.developer.version_1.structure.events.IncomingAssetMetadataRecorderService;

/**
 * Luis Torres (lutor1106@gmail.com") 09/05/16
 */
public class IncomingAssetMetadataTransactionManager {

    ​
    //VARIABLE DECLARATION
    private final IncomingAssetMetadataDAO dao;
    private final DigitalAssetMetadataManager incomingMetadataManager;
            ​
    //CONSTRUCTORS
    public IncomingAssetMetadataTransactionManager(IncomingAssetMetadataDAO dao,DigitalAssetMetadataManager incomingMetadataManager) {
        this.dao = dao;
        this.incomingMetadataManager = incomingMetadataManager;
    }
    /**
     * Handle the incoming event, the confirm of the message reception.
     *
     * @param newIncomingMetadataEvent the event to be handle
     *
     * */
    public void handleEvent(NewIncomingMetadataEvent newIncomingMetadataEvent){
        dao.saveNewEvent();
        //AssetIncomingMetadataTransactionRecord assetIncomingMetadataTransactionRecord = dao.getAssetIncomingMetadataTransactionRecordById(newIncomingMetadataEvent.getRecord().getRecordId());
        AssetIncomingMetadataTransactionRecord assetIncomingMetadataTransactionRecord = dao.saveNewEvent(newIncomingMetadataEvent);
    }
    ​
    //PRIVATE METHODS
            ​
    //GETTER AND SETTERS
            ​
    //INNER CLASSES
}