package org.fermat.fermat_dap_plugin.layer.actor_connection.asset_issuer.developer.version_1.structure.events;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStopAgentException;
import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import org.fermat.fermat_dap_plugin.layer.actor_connection.asset_issuer.developer.version_1.structure.database.AssetIssuerDAO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/02/16.
 */
public final class AssetIssuerMonitorAgent extends FermatAgent {

    //VARIABLE DECLARATION
    private IssuerAgent issuerAgent;

    private final ErrorManager errorManager;
    private final AssetIssuerDAO dao;
    private final ExecutorService service = Executors.newSingleThreadExecutor();

    //CONSTRUCTORS

    public AssetIssuerMonitorAgent(ErrorManager errorManager, AssetIssuerDAO dao) {
        this.errorManager = errorManager;
        this.dao = dao;
    }

    //PUBLIC METHODS

    @Override
    public void start() throws CantStartAgentException {
        try {
            issuerAgent = new IssuerAgent();
            service.submit(issuerAgent);
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

    private class IssuerAgent implements Runnable {

        public IssuerAgent() {
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
                errorManager.reportUnexpectedPluginException(Plugins.ASSET_INCOMING, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
        }

        private void doTheMainTask() {
        }

    }
}
