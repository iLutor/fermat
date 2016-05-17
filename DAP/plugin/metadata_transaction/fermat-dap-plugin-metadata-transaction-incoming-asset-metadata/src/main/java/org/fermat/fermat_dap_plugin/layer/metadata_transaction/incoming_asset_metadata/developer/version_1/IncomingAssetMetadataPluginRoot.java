package org.fermat.fermat_dap_plugin.layer.metadata_transaction.incoming_asset_metadata.developer.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.MetadataTransactionStatus;
import org.fermat.fermat_dap_api.layer.all_definition.events.DigitalAssetMetadataConfirmSentEvent;
import org.fermat.fermat_dap_api.layer.all_definition.events.NewIncomingMetadataEvent;
import org.fermat.fermat_dap_api.layer.dap_actor.DAPActor;
import org.fermat.fermat_dap_api.layer.dap_metadata_transaction.MetadataTransactionRecord;
import org.fermat.fermat_dap_api.layer.dap_metadata_transaction.asset_incoming.interfaces.AssetIncomingMetadataTransactionManager;
import org.fermat.fermat_dap_api.layer.dap_metadata_transaction.asset_incoming.interfaces.AssetIncomingMetadataTransactionRecord;
import org.fermat.fermat_dap_api.layer.dap_metadata_transaction.exceptions.CantStartMetadataTransactionException;
import org.fermat.fermat_dap_api.layer.dap_metadata_transaction.exceptions.CantUpdateMetadataTransactionException;
import org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantDeliverDatabaseException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import org.fermat.fermat_dap_api.layer.metadata.interfaces.DigitalAssetMetadataManager;
import org.fermat.fermat_dap_plugin.layer.metadata_transaction.incoming_asset_metadata.developer.version_1.structure.database.IncomingAssetMetadataDatabaseConstants;
import org.fermat.fermat_dap_plugin.layer.metadata_transaction.incoming_asset_metadata.developer.version_1.structure.events.IncomingAssetMetadataRecorderService;
import org.fermat.fermat_dap_plugin.layer.metadata_transaction.incoming_asset_metadata.developer.version_1.developer_utils.IncomingAssetMetadataDeveloperDatabaseFactory;
import org.fermat.fermat_dap_plugin.layer.metadata_transaction.incoming_asset_metadata.developer.version_1.structure.database.IncomingAssetMetadataDAO;
import org.fermat.fermat_dap_plugin.layer.metadata_transaction.incoming_asset_metadata.developer.version_1.structure.database.IncomingAssetMetadataDatabaseFactory;
import org.fermat.fermat_dap_plugin.layer.metadata_transaction.incoming_asset_metadata.developer.version_1.structure.events.IncomingAssetMetadataMonitorAgent;
import org.fermat.fermat_dap_plugin.layer.metadata_transaction.incoming_asset_metadata.developer.version_1.structure.functional.IncomingAssetMetadataTransactionManager;
import org.fermat.fermat_dap_plugin.layer.metadata_transaction.incoming_asset_metadata.developer.version_1.structure.internal.AssetIncomingMetadataTransactionRecordImpl;
import org.fermat.fermat_dap_plugin.layer.metadata_transaction.incoming_asset_metadata.developer.version_1.structure.internal.MetadataTransactionRecordImpl;


import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by ?? (??@gmail.com) on ??/??/16.
 */
public class IncomingAssetMetadataPluginRoot extends AbstractPlugin implements
        AssetIncomingMetadataTransactionManager,
        DatabaseManagerForDevelopers {

    //VARIABLE DECLARATION
    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.METADATA, plugin = Plugins.DIGITAL_ASSET)
    private DigitalAssetMetadataManager assetMetadataManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.NETWORK_SERVICE, plugin = Plugins.ASSET_TRANSMISSION)
    AssetTransmissionNetworkServiceManager assetTransmissionNetworkServiceManager;

    private IncomingAssetMetadataDAO dao;
    private IncomingAssetMetadataMonitorAgent agent;
    private IncomingAssetMetadataRecorderService recorderService;
    private IncomingAssetMetadataTransactionManager incomingAssetMetadataTransactionManager;

    //CONSTRUCTORS
    public IncomingAssetMetadataPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    //PUBLIC METHODS

    @Override
    public void start() throws CantStartPluginException {
        try {
            createDatabase();
            dao = new IncomingAssetMetadataDAO(pluginId, pluginDatabaseSystem);
            agent = new IncomingAssetMetadataMonitorAgent(errorManager, dao, assetTransmissionNetworkServiceManager,eventManager);
            recorderService = new IncomingAssetMetadataRecorderService(eventManager, pluginId, dao, this,incomingAssetMetadataTransactionManager);
            incomingAssetMetadataTransactionManager = new IncomingAssetMetadataTransactionManager(dao,assetMetadataManager);
            super.start();
        } catch (Exception e) {
            throw new CantStartPluginException(FermatException.wrapException(e));
        }
    }

    @Override
    public AssetIncomingMetadataTransactionRecord sendMetadata(DAPActor actorFrom, DAPActor actorTo, DigitalAssetMetadata metadataToSend) throws CantStartMetadataTransactionException {

        AssetIncomingMetadataTransactionRecord assetIncomingMetadataTransactionRecord = null;

        try {
            assetIncomingMetadataTransactionRecord = dao.saveNewAssetIncomingMetadataTransactionRecord(actorFrom, actorTo, metadataToSend);
        } catch (CantSaveEventException e) {
            e.printStackTrace();
        }

        return assetIncomingMetadataTransactionRecord;
    }

    @Override
    public void notifyReception(AssetIncomingMetadataTransactionRecord record) throws CantUpdateMetadataTransactionException {
        dao.confirmAssetIncomingMetadataTransactionRecord(record);
    }

    @Override
    public void cancelTransaction(AssetIncomingMetadataTransactionRecord record) throws CantUpdateMetadataTransactionException {
        dao.cancelAssetIncomingMetadataTransactionRecord(record);
    }

    @Override
    public AssetIncomingMetadataTransactionRecord getRecord(UUID recordId) {
        AssetIncomingMetadataTransactionRecordImpl assetIncomingMetadataTransactionRecord;
        assetIncomingMetadataTransactionRecord = (AssetIncomingMetadataTransactionRecordImpl) dao.getAssetIncomingMetadataTransactionRecordById(recordId);
        return assetIncomingMetadataTransactionRecord;
    }

    @Override
    public AssetIncomingMetadataTransactionRecord getLastTransaction(DigitalAssetMetadata assetMetadata) {
        AssetIncomingMetadataTransactionRecordImpl assetIncomingMetadataTransactionRecord;
        assetIncomingMetadataTransactionRecord = (AssetIncomingMetadataTransactionRecordImpl) dao.getLastAssetIncomingMetadataTransactionRecordByDigitalAssetMetadata(assetMetadata);
        return assetIncomingMetadataTransactionRecord;
    }

    @Override
    public void stop() {
        super.stop();
    }

    //PRIVATE METHODS
    private void createDatabase() throws CantCreateDatabaseException {
        IncomingAssetMetadataDatabaseFactory databaseFactory = new IncomingAssetMetadataDatabaseFactory(pluginDatabaseSystem, pluginId);
        if (!databaseFactory.databaseExists()) {
            databaseFactory.createDatabase();
        }
    }

    //GETTER AND SETTERS
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return IncomingAssetMetadataDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory, pluginId);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return IncomingAssetMetadataDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        Database database;
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_DATABASE);
            return IncomingAssetMetadataDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, database, developerDatabaseTable);
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            FermatException e = new CantDeliverDatabaseException("Cannot open the database", cantOpenDatabaseException, "DeveloperDatabase: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.INCOMING_ASSET_METADATA, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            FermatException e = new CantDeliverDatabaseException("Database does not exists", databaseNotFoundException, "DeveloperDatabase: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.INCOMING_ASSET_METADATA, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (Exception exception) {
            FermatException e = new CantDeliverDatabaseException("Unexpected Exception", exception, "DeveloperDatabase: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.INCOMING_ASSET_METADATA, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        // If we are here the database could not be opened, so we return an empty list
        return Collections.EMPTY_LIST;
    }

    //INNER CLASSES
}
