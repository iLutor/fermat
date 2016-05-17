package org.fermat.fermat_dap_plugin.layer.metadata_transaction.incoming_asset_metadata.developer.version_1.structure.events;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.fermat.fermat_dap_api.layer.all_definition.enums.EventType;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantStartServiceException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.interfaces.AssetTransactionService;
import org.fermat.fermat_dap_plugin.layer.metadata_transaction.incoming_asset_metadata.developer.version_1.IncomingAssetMetadataPluginRoot;
import org.fermat.fermat_dap_plugin.layer.metadata_transaction.incoming_asset_metadata.developer.version_1.structure.database.IncomingAssetMetadataDAO;
import org.fermat.fermat_dap_plugin.layer.metadata_transaction.incoming_asset_metadata.developer.version_1.structure.functional.IncomingAssetMetadataTransactionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ?? (??@gmail.com) on ??/??/16.
 */
public class IncomingAssetMetadataRecorderService implements AssetTransactionService {

    //VARIABLE DECLARATION
    private ServiceStatus serviceStatus;

    {
        serviceStatus = ServiceStatus.CREATED;
    }

    private final EventManager eventManager;
    private final UUID pluginId;
    private final IncomingAssetMetadataDAO dao;
    private List<FermatEventListener> listenersAdded;
    private IncomingAssetMetadataPluginRoot incomingAssetMetadataPluginRoot;
    private final IncomingAssetMetadataTransactionManager incomingAssetMetadataTransactionManager;

    {
        listenersAdded = new ArrayList<>();
    }

    //CONSTRUCTORS

    public IncomingAssetMetadataRecorderService(EventManager eventManager, UUID pluginId, IncomingAssetMetadataDAO dao, IncomingAssetMetadataPluginRoot incomingAssetMetadataPluginRoot,IncomingAssetMetadataTransactionManager incomingAssetMetadataTransactionManager) {
        this.eventManager = eventManager;
        this.pluginId = pluginId;
        this.dao = dao;
        this.incomingAssetMetadataPluginRoot = incomingAssetMetadataPluginRoot;
        this.incomingAssetMetadataTransactionManager= incomingAssetMetadataTransactionManager;
    }

    //PUBLIC METHODS

    @Override
    public void start() throws CantStartServiceException {
        String context = "Plugin ID: " + pluginId + " Event Manager: " + eventManager;
        try {
            FermatEventListener fermatEventListener;
            fermatEventListener = eventManager.getNewListener(EventType.RECEIVE_NEW_DAP_MESSAGE);
            fermatEventListener.setEventHandler(new IncomingAssetMetadataEventHandler(incomingAssetMetadataTransactionManager));
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

        } catch (Exception e) {
            throw new CantStartServiceException(e, context, "An unexpected exception happened while trying to start the AssetAppropriationRecordeService.");
        }
        serviceStatus = ServiceStatus.STARTED;
    }


    void receiveNewEvent(FermatEvent event) throws CantSaveEventException {
        String context = "pluginId: " + pluginId + " - event: " + event;
        try {
            dao.saveNewEvent(event);
        } catch (Exception e) {
            throw new CantSaveEventException(FermatException.wrapException(e), context, CantSaveEventException.DEFAULT_MESSAGE);
        }
    }

    @Override
    public void stop() {
        removeRegisteredListeners();
        serviceStatus = ServiceStatus.STOPPED;
    }


    //PRIVATE METHODS

    private void addListener(FermatEventListener listener) {
        eventManager.addListener(listener);
        listenersAdded.add(listener);
    }


    private void removeRegisteredListeners() {
        for (FermatEventListener fermatEventListener : listenersAdded) {
            eventManager.removeListener(fermatEventListener);
        }
        listenersAdded.clear();
    }
    //GETTER AND SETTERS

    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
    }

    public List<FermatEventListener> getListenersAdded() {
        return Validate.verifyGetter(listenersAdded);
    }
    //INNER CLASSES
}
