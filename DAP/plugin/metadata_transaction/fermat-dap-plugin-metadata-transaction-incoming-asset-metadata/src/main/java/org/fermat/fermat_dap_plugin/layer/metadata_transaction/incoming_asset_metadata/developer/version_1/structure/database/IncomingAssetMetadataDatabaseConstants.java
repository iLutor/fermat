package org.fermat.fermat_dap_plugin.layer.metadata_transaction.incoming_asset_metadata.developer.version_1.structure.database;

/**
 * Created by ?? (??@gmail.com) on ??/??/16.
 */
public class IncomingAssetMetadataDatabaseConstants {
    //DATABASE
    public static final String INCOMING_ASSET_METADATA_DATABASE = "incoming_asset_metadata_database";

    //INCOMING ASSET METADATA TABLE
    public static final String INCOMING_ASSET_METADATA_TABLE = "incoming_asset_metadata";
    public static final String INCOMING_ASSET_METADATA_COLUMN_NAME = "id";

    //EVENTS RECORDED TABLE
    public static final String INCOMING_ASSET_METADATA_EVENTS_RECORDED_TABLE_NAME = "events_recorded";

    public static final String INCOMING_ASSET_METADATA_EVENTS_RECORDED_ID_COLUMN_NAME = "event_id";
    public static final String INCOMING_ASSET_METADATA_EVENTS_RECORDED_EVENT_COLUMN_NAME = "event";
    public static final String INCOMING_ASSET_METADATA_EVENTS_RECORDED_SOURCE_COLUMN_NAME = "source";
    public static final String INCOMING_ASSET_METADATA_EVENTS_RECORDED_STATUS_COLUMN_NAME = "status";
    public static final String INCOMING_ASSET_METADATA_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME = "timestamp";

    public static final String INCOMING_ASSET_METADATA_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN = INCOMING_ASSET_METADATA_EVENTS_RECORDED_ID_COLUMN_NAME;
}
