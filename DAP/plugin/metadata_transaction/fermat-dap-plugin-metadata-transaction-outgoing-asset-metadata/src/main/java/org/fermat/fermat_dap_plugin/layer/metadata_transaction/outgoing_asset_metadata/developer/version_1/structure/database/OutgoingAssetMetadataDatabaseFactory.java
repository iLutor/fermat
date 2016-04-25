package org.fermat.fermat_dap_plugin.layer.metadata_transaction.outgoing_asset_metadata.developer.version_1.structure.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;

/**
 * Created by Jose Briceño (josebricenor@gmail.com) on 18/04/16.
 */
public final class OutgoingAssetMetadataDatabaseFactory {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    public OutgoingAssetMetadataDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    public Database createDatabase() throws CantCreateDatabaseException {
        Database database;
        try {
            database = this.pluginDatabaseSystem.createDatabase(pluginId, OutgoingAssetMetadataDatabaseConstants.OUTGOING_ASSET_METADATA_DATABASE);
        } catch (CantCreateDatabaseException cantCreateDatabaseException) {
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        }

        try {
            DatabaseTableFactory digitalAssetMetadataTable;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            digitalAssetMetadataTable = databaseFactory.newTableFactory(pluginId, OutgoingAssetMetadataDatabaseConstants.OUTGOING_ASSET_METADATA_TABLE_NAME);
            digitalAssetMetadataTable.addColumn(OutgoingAssetMetadataDatabaseConstants.OUTGOING_ASSET_METADATA_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            digitalAssetMetadataTable.addColumn(OutgoingAssetMetadataDatabaseConstants.OUTGOING_ASSET_METADATA_MESSAGE_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            digitalAssetMetadataTable.addColumn(OutgoingAssetMetadataDatabaseConstants.OUTGOING_ASSET_METADATA_MESSAGE_COLUMN_NAME, DatabaseDataType.STRING, 250, Boolean.TRUE);
            digitalAssetMetadataTable.addColumn(OutgoingAssetMetadataDatabaseConstants.OUTGOING_ASSET_METADATA_ATTEMPTS_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.TRUE);
            digitalAssetMetadataTable.addColumn(OutgoingAssetMetadataDatabaseConstants.OUTGOING_ASSET_METADATA_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.TRUE);
            digitalAssetMetadataTable.addColumn(OutgoingAssetMetadataDatabaseConstants.OUTGOING_ASSET_METADATA_ACTOR_TO_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            digitalAssetMetadataTable.addColumn(OutgoingAssetMetadataDatabaseConstants.OUTGOING_ASSET_METADATA_ACTOR_FROM_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            digitalAssetMetadataTable.addColumn(OutgoingAssetMetadataDatabaseConstants.OUTGOING_ASSET_METADATA_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);

            digitalAssetMetadataTable.addIndex(OutgoingAssetMetadataDatabaseConstants.OUTGOING_ASSET_METADATA_ID_COLUMN_NAME);
            try {
                //Create the table
                databaseFactory.createTable(pluginId, digitalAssetMetadataTable);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "Creating " + OutgoingAssetMetadataDatabaseConstants.OUTGOING_ASSET_METADATA_TABLE_NAME + " table", "Exception not handled by the plugin, There is a problem and I cannot create the table.");
            }

            DatabaseTableFactory eventsRecorderTable = databaseFactory.newTableFactory(pluginId, OutgoingAssetMetadataDatabaseConstants.OUTGOING_ASSET_METADATA_EVENTS_RECORDED_TABLE_NAME);

            eventsRecorderTable.addColumn(OutgoingAssetMetadataDatabaseConstants.OUTGOING_ASSET_METADATA_EVENTS_RECORDED_ID_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.TRUE);
            eventsRecorderTable.addColumn(OutgoingAssetMetadataDatabaseConstants.OUTGOING_ASSET_METADATA_EVENTS_RECORDED_EVENT_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            eventsRecorderTable.addColumn(OutgoingAssetMetadataDatabaseConstants.OUTGOING_ASSET_METADATA_EVENTS_RECORDED_SOURCE_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            eventsRecorderTable.addColumn(OutgoingAssetMetadataDatabaseConstants.OUTGOING_ASSET_METADATA_EVENTS_RECORDED_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            eventsRecorderTable.addColumn(OutgoingAssetMetadataDatabaseConstants.OUTGOING_ASSET_METADATA_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);

            eventsRecorderTable.addIndex(OutgoingAssetMetadataDatabaseConstants.OUTGOING_ASSET_METADATA_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(pluginId, eventsRecorderTable);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "Creating " + OutgoingAssetMetadataDatabaseConstants.OUTGOING_ASSET_METADATA_EVENTS_RECORDED_TABLE_NAME + " table", "Exception not handled by the plugin, There is a problem and I cannot create the table.");
            }
        } catch (InvalidOwnerIdException invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "", "There is a problem with the ownerId of the database.");
        }
        return database;
    }


    public boolean databaseExists() {
        try {
            pluginDatabaseSystem.openDatabase(pluginId, OutgoingAssetMetadataDatabaseConstants.OUTGOING_ASSET_METADATA_DATABASE);
            return true;
        } catch (CantOpenDatabaseException | DatabaseNotFoundException e) {
            return false;
        }
    }
}
