package org.fermat.fermat_dap_api.layer.swap_transaction.asset_for_bitcoin.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.SwapStatus;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantNotifyEventException;
import org.fermat.fermat_dap_api.layer.swap_transaction.exceptions.CantCheckSwapProgressException;
import org.fermat.fermat_dap_api.layer.swap_transaction.exceptions.CantStartAssetSwapException;

import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 2/05/16.
 */
public interface AssetForBitcoinManager extends FermatManager {
    /**
     * Through this method an asset is swaped for its bitcoin value.
     *
     * @param metadata        the metadata that we are going to convert
     * @param bitcoinWalletPk the public key from the bitcoin wallet where we'll send
     *                        the asset bitcoins
     * @return {@link UUID} that represents the id for this transaction, this id shall be
     * used for check the transaction status.
     * @throws CantStartAssetSwapException
     */
    UUID swapAsset(DigitalAssetMetadata metadata, String bitcoinWalletPk) throws CantStartAssetSwapException;

    /**
     * Call this method to check the current status for a transaction.
     *
     * @param transactionId {@link UUID} the ID for this transaction
     * @return the current status for this transaction
     * @throws CantCheckSwapProgressException
     */
    SwapStatus getStatus(UUID transactionId) throws CantCheckSwapProgressException;

    /**
     * Whenever an event is raised by this plugin. Its reception should be notified
     * using the id in the event.
     *
     * @param eventId the ID of the event that has been raised.
     * @throws CantNotifyEventException
     */
    void notifyReception(UUID eventId) throws CantNotifyEventException;
}
