package org.fermat.fermat_dap_api.layer.dap_metadata_transaction.asset_incoming.interfaces;

import org.fermat.fermat_dap_api.layer.dap_metadata_transaction.MetadataTransactionRecord;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPMessageSubject;

/**
 * Luis Torres (lutor1106@gmail.com") 11/05/16
 */
public interface AssetIncomingMetadataTransactionRecord extends MetadataTransactionRecord {

    /**
     * The {@link DAPMessageSubject} associated with this record.
     *
     * @return
     */
    DAPMessageSubject getSubject();

}