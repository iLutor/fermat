package org.fermat.fermat_dap_plugin.layer.metadata_transaction.incoming_asset_metadata.developer.version_1.structure.events;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;

import org.fermat.fermat_dap_api.layer.all_definition.events.NewIncomingMetadataEvent;
import org.fermat.fermat_dap_api.layer.all_definition.events.ReceivedNewDigitalAssetMetadataNotificationEvent;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import org.fermat.fermat_dap_plugin.layer.metadata_transaction.incoming_asset_metadata.developer.version_1.IncomingAssetMetadataPluginRoot;
import org.fermat.fermat_dap_plugin.layer.metadata_transaction.incoming_asset_metadata.developer.version_1.structure.functional.IncomingAssetMetadataTransactionManager;

/**
 * Created by ?? (??@gmail.com) on ??/??/16.
 */
public class IncomingAssetMetadataEventHandler implements FermatEventHandler {

    //VARIABLE DECLARATION
    private final IncomingAssetMetadataTransactionManager transactionManager;

    //CONSTRUCTORS
    public IncomingAssetMetadataEventHandler(IncomingAssetMetadataTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    //PUBLIC METHODS
    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        if (fermatEvent == null)
            throw new CantSaveEventException(null, "Handling an asset buyer event", "Illegal Argument, this method takes an fermatEvent and was passed an null");

        System.out.println("VAMM: INCOMING ASSET METADATA RECEIVED A NEW EVENT!");
        System.out.println("VAMM: Type: " + fermatEvent.getEventType() + " - Source: " + fermatEvent.getSource());


        if (fermatEvent instanceof NewIncomingMetadataEvent) {
            transactionManager.handleEvent((NewIncomingMetadataEvent) fermatEvent);
        }
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
