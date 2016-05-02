package org.fermat.fermat_dap_api.layer.dap_transaction.asset_break.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.BreakStatus;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantNotifyEventException;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_break.exceptions.CantCheckAssetBreakProgressException;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_break.exceptions.CantStartAssetBreakException;

import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 2/05/16.
 */
public interface AssetBreakManager extends FermatManager {

    /**
     * Use this method to start the break of an asset.
     *
     * @param assetToBreak the asset that we are going to break
     * @param btcWalletPk  the public key of the bitcoin wallet where the bitcoins will go after the break.
     * @return {@link UUID} the id associated with this transaction
     * @throws CantStartAssetBreakException
     */
    UUID breakAsset(DigitalAssetMetadata assetToBreak, String btcWalletPk) throws CantStartAssetBreakException;

    /**
     * Use this method to check the current status for an already started transaction.
     *
     * @param transactionId the id of the transaction to check
     * @return the current status
     * @throws CantCheckAssetBreakProgressException
     */
    BreakStatus checkStatus(UUID transactionId) throws CantCheckAssetBreakProgressException;

    /**
     * @param eventId the ID of the event to notify
     * @throws CantNotifyEventException
     */
    void notifyReception(UUID eventId) throws CantNotifyEventException;

}
