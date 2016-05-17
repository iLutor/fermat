package org.fermat.fermat_dap_plugin.layer.metadata_transaction.incoming_asset_metadata.developer.version_1.structure.events;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStopAgentException;
import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import org.fermat.fermat_dap_plugin.layer.metadata_transaction.incoming_asset_metadata.developer.version_1.structure.database.IncomingAssetMetadataDAO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ?? (??@gmail.com) on ??/??/16.
 */
public class IncomingAssetMetadataMonitorAgent extends FermatAgent {

    //VARIABLE DECLARATION
    private MetadataTransactionAgent metadataTransactionAgent;

    private final ErrorManager errorManager;
    private final IncomingAssetMetadataDAO dao;
    private final ExecutorService service = Executors.newSingleThreadExecutor();
    private final AssetTransmissionNetworkServiceManager assetTransmissionNetworkServiceManager;
    private final EventManager eventManager;

    //CONSTRUCTORS

    public IncomingAssetMetadataMonitorAgent(ErrorManager errorManager, IncomingAssetMetadataDAO dao, AssetTransmissionNetworkServiceManager assetTransmissionNetworkServiceManager, EventManager eventManager) {
        this.errorManager = errorManager;
        this.dao = dao;
        this.assetTransmissionNetworkServiceManager = assetTransmissionNetworkServiceManager;
        this.eventManager = eventManager;
    }

    //PUBLIC METHODS

    @Override
    public void start() throws CantStartAgentException {
        try {
            metadataTransactionAgent = new MetadataTransactionAgent(dao);
            service.submit(metadataTransactionAgent);
            super.start();
        } catch (Exception e) {
            throw new CantStartAgentException(FermatException.wrapException(e), null, null);
        }
    }

    @Override
    public void stop() throws CantStopAgentException {
        try {
            service.shutdown();
            super.stop();
        } catch (Exception e) {
            throw new CantStopAgentException(FermatException.wrapException(e), null, null);
        }
    }

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES

    private class MetadataTransactionAgent implements Runnable {

        private boolean agentRunning, toSend, toConfirm;
        private static final int WAIT_TIME = 20; //SECONDS
        private IncomingAssetMetadataDAO incomingAssetMetadataDAO;

        public MetadataTransactionAgent(IncomingAssetMetadataDAO incomingAssetMetadataDAO) {
            this.incomingAssetMetadataDAO = incomingAssetMetadataDAO;
            startAgent();
        }

        public void startAgent() {
            agentRunning = true;
            toSend = true;
            toConfirm = true;
        }
        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p/>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            try {
                doTheMainTask();
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(Plugins.INCOMING_ASSET_METADATA, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
        }

        private void doTheMainTask() {
        }

    }
}
