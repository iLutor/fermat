package org.fermat.fermat_dap_plugin.layer.metadata_transaction.incoming_asset_metadata.developer.version_1.structure.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.RecordsNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.*;

import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPMessageSubject;
import org.fermat.fermat_dap_api.layer.all_definition.enums.EventStatus;
import org.fermat.fermat_dap_api.layer.all_definition.enums.MetadataTransactionStatus;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetUserDeveloperIdentitiesException;
import org.fermat.fermat_dap_api.layer.dap_actor.DAPActor;
import org.fermat.fermat_dap_api.layer.dap_metadata_transaction.MetadataTransactionRecord;
import org.fermat.fermat_dap_api.layer.dap_metadata_transaction.asset_incoming.interfaces.AssetIncomingMetadataTransactionRecord;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import org.fermat.fermat_dap_plugin.layer.metadata_transaction.incoming_asset_metadata.developer.version_1.structure.internal.AssetIncomingMetadataTransactionRecordImpl;
import org.fermat.fermat_dap_plugin.layer.metadata_transaction.incoming_asset_metadata.developer.version_1.structure.internal.MetadataTransactionRecordImpl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by ?? (??@gmail.com) on ??/??/16.
 */
public class IncomingAssetMetadataDAO {

    //VARIABLE DECLARATION
    private final Database database;
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    //CONSTRUCTORS
    public IncomingAssetMetadataDAO(UUID pluginId, PluginDatabaseSystem pluginDatabaseSystem) throws CantExecuteDatabaseOperationException {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        database = openDatabase(pluginDatabaseSystem, pluginId);
    }

    //PUBLIC METHODS
    public void saveNewEvent(FermatEvent event) throws CantSaveEventException {
        String eventType = event.getEventType().getCode();
        String eventSource = event.getSource().getCode();
        String context = "Event Type : " + eventType + " - Event Source: " + eventSource;
        try {
            DatabaseTable databaseTable = this.database.getTable(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_EVENTS_RECORDED_TABLE_NAME);
            DatabaseTableRecord eventRecord = databaseTable.getEmptyRecord();
            UUID eventRecordID = UUID.randomUUID();

            eventRecord.setUUIDValue(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_EVENTS_RECORDED_ID_COLUMN_NAME, eventRecordID);
            eventRecord.setStringValue(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType);
            eventRecord.setStringValue(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, System.currentTimeMillis());
            databaseTable.insertRecord(eventRecord);
        } catch (CantInsertRecordException exception) {
            throw new CantSaveEventException(exception, context, "Cannot insert a record in Asset Appropriation Event Table");
        } catch (Exception exception) {
            throw new CantSaveEventException(FermatException.wrapException(exception), context, "Unexpected exception");
        }
    }

    /**
     * Change the status of a record to CANCELLED
     * @param assetIncomingMetadataTransactionRecord the one to be changed
     *
     * */
    public void cancelAssetIncomingMetadataTransactionRecord(AssetIncomingMetadataTransactionRecord assetIncomingMetadataTransactionRecord){
        try {
            updateStatusAssetIncomingMetadataTransactionRecord(assetIncomingMetadataTransactionRecord.getRecordId(), MetadataTransactionStatus.CANCELLED);
        } catch (CantUpdateRecordException e) {
            e.printStackTrace();
        } catch (CantPersistFileException e) {
            e.printStackTrace();
        } catch (RecordsNotFoundException e) {
            e.printStackTrace();
        } catch (CantLoadTableToMemoryException e) {
            e.printStackTrace();
        }
    }

    /**
     * Change the status of a record to SENT
     * @param assetIncomingMetadataTransactionRecord the one to be changed
     *
     * */
    public void updateSentAssetIncomingMetadataTransactionRecord(AssetIncomingMetadataTransactionRecord assetIncomingMetadataTransactionRecord){
        try {
            updateStatusAssetIncomingMetadataTransactionRecord(assetIncomingMetadataTransactionRecord.getRecordId(), MetadataTransactionStatus.SENT);
        } catch (CantUpdateRecordException e) {
            e.printStackTrace();
        } catch (CantPersistFileException e) {
            e.printStackTrace();
        } catch (RecordsNotFoundException e) {
            e.printStackTrace();
        } catch (CantLoadTableToMemoryException e) {
            e.printStackTrace();
        }
    }

    /**
     * Change the status of a record to RECEIVE
     * @param assetIncomingMetadataTransactionRecord the one to be changed
     *
     * */
    public void updateReceiveAssetIncomingMetadataTransactionRecord(AssetIncomingMetadataTransactionRecord assetIncomingMetadataTransactionRecord){
        try {
            updateStatusAssetIncomingMetadataTransactionRecord(assetIncomingMetadataTransactionRecord.getRecordId(), MetadataTransactionStatus.RECEIVE);
        } catch (CantUpdateRecordException e) {
            e.printStackTrace();
        } catch (CantPersistFileException e) {
            e.printStackTrace();
        } catch (RecordsNotFoundException e) {
            e.printStackTrace();
        } catch (CantLoadTableToMemoryException e) {
            e.printStackTrace();
        }
    }

    /**
     * Change the status of a record to CONFIRMED
     * @param assetIncomingMetadataTransactionRecord the one to be changed
     *
     * */
    public void confirmAssetIncomingMetadataTransactionRecord(AssetIncomingMetadataTransactionRecord assetIncomingMetadataTransactionRecord){
        try {
            updateStatusAssetIncomingMetadataTransactionRecord(assetIncomingMetadataTransactionRecord.getRecordId(), MetadataTransactionStatus.CONFIRMED);
        } catch (CantUpdateRecordException e) {
            e.printStackTrace();
        } catch (CantPersistFileException e) {
            e.printStackTrace();
        } catch (RecordsNotFoundException e) {
            e.printStackTrace();
        } catch (CantLoadTableToMemoryException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save a new MetadataTransactionRecord
     *
     *
     * Guardar toda la información que vas a necesitar para construir el récord ése IncomingMetadataTransactionRecord */
    public AssetIncomingMetadataTransactionRecord saveNewAssetIncomingMetadataTransactionRecord(AssetIncomingMetadataTransactionRecordImpl incomingMetadata) throws CantSaveEventException {

        String context = "Event Type : " + " - Event Source: ";
        long currentTime;

        try {
            DatabaseTable databaseTable = getIncomingAssetMetadataTable();
            DatabaseTableRecord eventRecord = databaseTable.getEmptyRecord();
            UUID eventRecordID = UUID.randomUUID();

            eventRecord.setUUIDValue(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_ID_COLUMN_NAME, eventRecordID);
            eventRecord.setUUIDValue(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_MESSAGE_ID_COLUMN_NAME, incomingMetadata.getRecordId());
            eventRecord.setStringValue(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_MESSAGE_COLUMN_NAME, XMLParser.parseObject(incomingMetadata));
            eventRecord.setIntegerValue(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_ATTEMPTS_COLUMN_NAME, 0);
            eventRecord.setFermatEnum(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_STATUS_COLUMN_NAME, MetadataTransactionStatus.PENDING);

            currentTime = System.currentTimeMillis();
                eventRecord.setLongValue(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_TIMESTAMP_COLUMN_NAME, currentTime);

            databaseTable.insertRecord(eventRecord);
            incomingMetadata = new AssetIncomingMetadataTransactionRecordImpl(eventRecordID, incomingMetadata.getRecordId(), MetadataTransactionStatus.PENDING, new Date(currentTime), 0, actorTo, actorFrom);

        } catch (CantInsertRecordException exception) {
            throw new CantSaveEventException(exception, context, "Cannot insert a record in INCOMING ASSET METADATA Table");
        } catch (Exception exception) {
            throw new CantSaveEventException(FermatException.wrapException(exception), context, "Unexpected exception");
        }

        return incomingMetadata;
    }/*
    public AssetIncomingMetadataTransactionRecord saveNewAssetIncomingMetadataTransactionRecord(AssetIncomingMetadataTransactionRecordImpl incomingMetadata) throws CantSaveEventException {

        String context = "Event Type : " + " - Event Source: ";
        long currentTime;

        try {
            DatabaseTable databaseTable = getIncomingAssetMetadataTable();
            DatabaseTableRecord eventRecord = databaseTable.getEmptyRecord();
            UUID eventRecordID = UUID.randomUUID();

            eventRecord.setUUIDValue(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_ID_COLUMN_NAME, eventRecordID);
            eventRecord.setUUIDValue(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_MESSAGE_ID_COLUMN_NAME, incomingMetadata.getRecordId());
            eventRecord.setStringValue(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_MESSAGE_COLUMN_NAME, XMLParser.parseObject(incomingMetadata));
            eventRecord.setIntegerValue(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_ATTEMPTS_COLUMN_NAME, 0);
            eventRecord.setFermatEnum(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_STATUS_COLUMN_NAME, MetadataTransactionStatus.PENDING);

            currentTime = System.currentTimeMillis();
            eventRecord.setLongValue(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_TIMESTAMP_COLUMN_NAME, currentTime);

            databaseTable.insertRecord(eventRecord);
            incomingMetadata = new AssetIncomingMetadataTransactionRecordImpl(eventRecordID, incomingMetadata.getRecordId(), MetadataTransactionStatus.PENDING, new Date(currentTime), 0, actorTo, actorFrom);

        } catch (CantInsertRecordException exception) {
            throw new CantSaveEventException(exception, context, "Cannot insert a record in INCOMING ASSET METADATA Table");
        } catch (Exception exception) {
            throw new CantSaveEventException(FermatException.wrapException(exception), context, "Unexpected exception");
        }

        return incomingMetadata;
    }*/

    /**
     * Update the status of the metadata
     *
     * */
    public void updateStatusAssetIncomingMetadataTransactionRecord(UUID idMetadataTransaction, MetadataTransactionStatus metadataTransactionStatus) throws CantUpdateRecordException, CantPersistFileException, RecordsNotFoundException, CantLoadTableToMemoryException {
        DatabaseTable table = getIncomingAssetMetadataTable();
        table.addUUIDFilter(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_ID_COLUMN_NAME, idMetadataTransaction, DatabaseFilterType.EQUAL);
        table.loadToMemory();
        if (table.getRecords().isEmpty()) throw new RecordsNotFoundException();
        DatabaseTableRecord record = table.getRecords().get(0);
        record.setStringValue(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_STATUS_COLUMN_NAME, metadataTransactionStatus.toString());
        table.updateRecord(record);
    }

    /**
     * Update the attempts count
     *
     * */
    public void updateAttemptAssetIncomingMetadataTransactionRecord(UUID idMetadataTransaction, int attempt) throws CantUpdateRecordException, CantPersistFileException, RecordsNotFoundException, CantLoadTableToMemoryException {
        DatabaseTable table = getIncomingAssetMetadataTable();
        table.addUUIDFilter(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_ID_COLUMN_NAME, idMetadataTransaction, DatabaseFilterType.EQUAL);
        table.loadToMemory();
        if (table.getRecords().isEmpty()) throw new RecordsNotFoundException();
        DatabaseTableRecord record = table.getRecords().get(0);
        record.setIntegerValue(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_ATTEMPTS_COLUMN_NAME, attempt);
        table.updateRecord(record);
    }

    /**
     * Return true if the table is empty of PENDING status records
     *
     * */
    public boolean isEmptyOfPending(){
        try {
            return isEmptyByStatus(MetadataTransactionStatus.PENDING);
        } catch (CantLoadTableToMemoryException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Return true if the table is empty of RECEIVE status records
     *
     * */
    public boolean isEmptyOfReceive(){
        try {
            return isEmptyByStatus(MetadataTransactionStatus.RECEIVE);
        } catch (CantLoadTableToMemoryException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Return true if the table is empty of a determinate status
     *
     * */
    public boolean isEmptyByStatus(MetadataTransactionStatus metadataTransactionStatus) throws CantLoadTableToMemoryException {
        DatabaseTable table = getIncomingAssetMetadataTable();
        table.addFermatEnumFilter(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_STATUS_COLUMN_NAME, metadataTransactionStatus, DatabaseFilterType.EQUAL);
        table.loadToMemory();
        if (table.getRecords().isEmpty()) return true;
        return false;
    }

    public AssetIncomingMetadataTransactionRecord getAssetIncomingMetadataTransactionRecordById(UUID idRecord) {
        AssetIncomingMetadataTransactionRecord assetIncomingMetadataTransactionRecord = null;
        DatabaseTable table;

        // Get Asset Users identities list.
        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get Metadata transaction, table not found.", "Plugin Identity", "Cant get AssetIncomingMetadataTransactionRecord, table not found.");
            }
            table.addUUIDFilter(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_ID_COLUMN_NAME, idRecord, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            // 3) Get Metadata Transaction Record.
            assetIncomingMetadataTransactionRecord = getAssetIncomingMetadataTransactionFromRecord(table.getRecords());
        } catch (CantLoadTableToMemoryException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Return the values.
        return assetIncomingMetadataTransactionRecord;
    }

    /**
     * Bring the last record of a related DigitalAssetMetadata
     *
     * */
    public AssetIncomingMetadataTransactionRecord getLastAssetIncomingMetadataTransactionRecordByDigitalAssetMetadata(DigitalAssetMetadata digitalAssetMetadata) {
        AssetIncomingMetadataTransactionRecord assetIncomingMetadataTransactionRecord = null;
        DatabaseTable table;

        // Get Asset Users identities list.
        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get Metadata transaction, table not found.", "Plugin Identity", "Cant get MetadataTransactionRecord, table not found.");
            }
            table.addUUIDFilter(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_MESSAGE_ID_COLUMN_NAME, digitalAssetMetadata.getMetadataId(), DatabaseFilterType.EQUAL);
            table.loadToMemory();
            // 3) Get Metadata Transaction Record.
            assetIncomingMetadataTransactionRecord = getAssetIncomingMetadataTransactionFromRecord(table.getRecords());
        } catch (CantLoadTableToMemoryException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Return the values.
        return assetIncomingMetadataTransactionRecord;
    }

    /**
     * Bring all the records with a PENDING status
     *
     * */
    public List<AssetIncomingMetadataTransactionRecordImpl> getPendingAssetIncomingMetadataTransactionRecord(){
        return getAssetIncomingMetadataTransactionRecordByStatus(MetadataTransactionStatus.PENDING);
    }

    /**
     * Bring all the records with a RECEIVE status
     *
     * */
    public List<AssetIncomingMetadataTransactionRecordImpl> getReceiveAssetIncomingMetadataTransactionRecord(){
        return getAssetIncomingMetadataTransactionRecordByStatus(MetadataTransactionStatus.RECEIVE);
    }

    /**
     * Bring all the records filter by status
     *
     * */
    public List<AssetIncomingMetadataTransactionRecordImpl> getAssetIncomingMetadataTransactionRecordByStatus(MetadataTransactionStatus metadataTransactionStatus) {
        List<AssetIncomingMetadataTransactionRecordImpl> assetIncomingMetadataTransactionRecord = null;
        DatabaseTable table;

        // Get Asset Users identities list.
        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get Metadata transaction, table not found.", "Plugin Identity", "Cant get MetadataTransactionRecord, table not found.");
            }
            table.addFermatEnumFilter(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_STATUS_COLUMN_NAME, metadataTransactionStatus, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            // 3) Get Metadata Transaction Record.
            assetIncomingMetadataTransactionRecord = getMetadataTransactionListFromRecord(table.getRecords());
        } catch (CantLoadTableToMemoryException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Return the values.
        return assetIncomingMetadataTransactionRecord;
    }

    //PRIVATE METHODS

    private Database openDatabase(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) throws CantExecuteDatabaseOperationException {
        try {
            return pluginDatabaseSystem.openDatabase(pluginId, IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_TABLE_NAME);
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantExecuteDatabaseOperationException(exception, "Opening the INCOMING ASSET METADATA Database", "Error in database plugin.");
        }
    }

    private DatabaseTable getIncomingAssetMetadataTable() {
        return database.getTable(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_TABLE_NAME);
    }

    private AssetIncomingMetadataTransactionRecordImpl getAssetIncomingMetadataTransactionFromRecord(List<DatabaseTableRecord> records) throws InvalidParameterException {
        AssetIncomingMetadataTransactionRecordImpl assetIncomingMetadataTransactionRecord = null;
        for (DatabaseTableRecord record : records) {

            assetIncomingMetadataTransactionRecord = new AssetIncomingMetadataTransactionRecordImpl(
                    record.getUUIDValue(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_ID_COLUMN_NAME),
                    (DigitalAssetMetadata) XMLParser.parseXML(record.getStringValue(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_ID_COLUMN_NAME), DigitalAssetMetadata.class),
                    MetadataTransactionStatus.getByCode(record.getStringValue(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_STATUS_COLUMN_NAME)),
                    new Date(record.getLongValue(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_TIMESTAMP_COLUMN_NAME)),
                    record.getIntegerValue(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_ATTEMPTS_COLUMN_NAME),
                    (DAPActor) XMLParser.parseXML(record.getStringValue(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_ACTOR_TO_COLUMN_NAME), DAPActor.class),
                    (DAPActor) XMLParser.parseXML(record.getStringValue(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_ACTOR_FROM_COLUMN_NAME), DAPActor.class),

            );

        }
        return assetIncomingMetadataTransactionRecord;
    }

    private List<AssetIncomingMetadataTransactionRecordImpl> getMetadataTransactionListFromRecord(List<DatabaseTableRecord> records) throws InvalidParameterException {
        List<AssetIncomingMetadataTransactionRecordImpl> assetIncomingMetadataTransactionRecordList = null;
        AssetIncomingMetadataTransactionRecordImpl assetIncomingMetadataTransactionRecord;
        for (DatabaseTableRecord record : records) {

            assetIncomingMetadataTransactionRecord = new AssetIncomingMetadataTransactionRecordImpl(
                    record.getUUIDValue(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_ID_COLUMN_NAME),
                    (DigitalAssetMetadata) XMLParser.parseXML(record.getStringValue(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_ID_COLUMN_NAME), DigitalAssetMetadata.class),
                    MetadataTransactionStatus.getByCode(record.getStringValue(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_STATUS_COLUMN_NAME)),
                    new Date(record.getLongValue(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_TIMESTAMP_COLUMN_NAME)),
                    record.getIntegerValue(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_ATTEMPTS_COLUMN_NAME),
                    (DAPActor) XMLParser.parseXML(record.getStringValue(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_ACTOR_TO_COLUMN_NAME), DAPActor.class),
                    (DAPActor) XMLParser.parseXML(record.getStringValue(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_ACTOR_FROM_COLUMN_NAME), DAPActor.class),
                    record.getStringValue(IncomingAssetMetadataDatabaseConstants.INCOMING_ASSET_METADATA_MESSAGE_COLUMN_NAME);
            );

            assetIncomingMetadataTransactionRecordList.add(assetIncomingMetadataTransactionRecord);
        }
        return assetIncomingMetadataTransactionRecordList;
    }

    //INNER CLASSES
}
