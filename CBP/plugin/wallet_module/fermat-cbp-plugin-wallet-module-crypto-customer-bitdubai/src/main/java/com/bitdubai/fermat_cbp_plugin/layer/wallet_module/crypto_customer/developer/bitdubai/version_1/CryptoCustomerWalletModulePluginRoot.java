package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.ActorExtraDataManager;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.interfaces.CryptoBrokerActorConnectionManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_ack_offline_payment.interfaces.BrokerAckOfflinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_ack_online_payment.interfaces.BrokerAckOnlinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_submit_offline_merchandise.interfaces.BrokerSubmitOfflineMerchandiseManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_submit_online_merchandise.interfaces.BrokerSubmitOnlineMerchandiseManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_ack_offline_merchandise.interfaces.CustomerAckOfflineMerchandiseManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_ack_online_merchandise.interfaces.CustomerAckOnlineMerchandiseManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_offline_payment.interfaces.CustomerOfflinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_online_payment.interfaces.CustomerOnlinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantPublishIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentityManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.interfaces.CustomerBrokerCloseManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.interfaces.CustomerBrokerNewManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.interfaces.CustomerBrokerUpdateManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetCryptoCustomerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings.CryptoCustomerWalletPreferenceSettings;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletAssociatedSettingImpl;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletModuleCryptoCustomerWalletManager;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletModuleNegotiationBankAccount;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_cer_api.layer.search.interfaces.CurrencyExchangeProviderFilterManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;


/**
 * Module for the Crypto Customer Wallet
 *
 * @author Nelson Ramirez
 * @version 1.0
 * @since 11/11/2015
 */
public class CryptoCustomerWalletModulePluginRoot extends AbstractPlugin implements
        LogManagerForDevelopers,
        CryptoCustomerWalletModuleManager {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.MIDDLEWARE, plugin = Plugins.WALLET_MANAGER)
    private WalletManagerManager walletManagerManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.NEGOTIATION, plugin = Plugins.NEGOTIATION_PURCHASE)
    CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.CRYPTO_CUSTOMER)
    private CryptoCustomerIdentityManager cryptoCustomerIdentityManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.CONTRACT, plugin = Plugins.CONTRACT_PURCHASE)
    CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.NEGOTIATION_TRANSACTION, plugin = Plugins.CUSTOMER_BROKER_NEW)
    CustomerBrokerNewManager customerBrokerNewManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.NEGOTIATION_TRANSACTION, plugin = Plugins.CUSTOMER_BROKER_UPDATE)
    CustomerBrokerUpdateManager customerBrokerUpdateManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.NEGOTIATION_TRANSACTION, plugin = Plugins.CUSTOMER_BROKER_CLOSE)
    CustomerBrokerCloseManager customerBrokerCloseManager;

    @NeededPluginReference(platform = Platforms.CURRENCY_EXCHANGE_RATE_PLATFORM, layer = Layers.SEARCH, plugin = Plugins.FILTER)
    CurrencyExchangeProviderFilterManager currencyExchangeProviderFilterManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.CRYPTO_CUSTOMER_ACTOR)
    ActorExtraDataManager actorExtraDataManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.BUSINESS_TRANSACTION, plugin = Plugins.CUSTOMER_ONLINE_PAYMENT)
    CustomerOnlinePaymentManager customerOnlinePaymentManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.BUSINESS_TRANSACTION, plugin = Plugins.CUSTOMER_OFFLINE_PAYMENT)
    CustomerOfflinePaymentManager customerOfflinePaymentManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.BUSINESS_TRANSACTION, plugin = Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE)
    CustomerAckOnlineMerchandiseManager customerAckOnlineMerchandiseManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.BUSINESS_TRANSACTION, plugin = Plugins.CUSTOMER_ACK_OFFLINE_MERCHANDISE)
    CustomerAckOfflineMerchandiseManager customerAckOfflineMerchandiseManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.BUSINESS_TRANSACTION, plugin = Plugins.BROKER_ACK_ONLINE_PAYMENT)
    BrokerAckOnlinePaymentManager brokerAckOnlinePaymentManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.BUSINESS_TRANSACTION, plugin = Plugins.BROKER_ACK_OFFLINE_PAYMENT)
    BrokerAckOfflinePaymentManager brokerAckOfflinePaymentManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.BUSINESS_TRANSACTION, plugin = Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE)
    BrokerSubmitOnlineMerchandiseManager brokerSubmitOnlineMerchandiseManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.BUSINESS_TRANSACTION, plugin = Plugins.BROKER_SUBMIT_OFFLINE_MERCHANDISE)
    BrokerSubmitOfflineMerchandiseManager brokerSubmitOfflineMerchandiseManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.ACTOR_CONNECTION     , plugin = Plugins.CRYPTO_BROKER     )
    private CryptoBrokerActorConnectionManager cryptoBrokerActorConnectionManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.BASIC_WALLET, plugin = Plugins.BITCOIN_WALLET)
    BitcoinWalletManager bitcoinWalletManager;


    //TODO Change for actorExtraDataManager
//    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.CRYPTO_CUSTOMER_ACTOR)
//    CryptoCustomerActorManager cryptoCustomerActorManager;

    private CryptoCustomerWalletManager walletManager;

    public CryptoCustomerWalletModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /**
     * Logging level for this plugin
     */
    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    private SettingsManager<CryptoCustomerWalletPreferenceSettings> settingsManager;


    @Override
    public CryptoCustomerWalletManager getCryptoCustomerWallet(String walletPublicKey) throws CantGetCryptoCustomerWalletException {
        try {
            if (walletManager == null)
                walletManager = new CryptoCustomerWalletModuleCryptoCustomerWalletManager(walletManagerManager,
                        cryptoBrokerActorConnectionManager,
                        customerBrokerPurchaseNegotiationManager,
                        cryptoCustomerIdentityManager,
                        customerBrokerContractPurchaseManager,
                        customerBrokerNewManager,
                        customerBrokerUpdateManager,
                        customerBrokerCloseManager,
                        currencyExchangeProviderFilterManager,
                        actorExtraDataManager,
                        customerOnlinePaymentManager,
                        customerOfflinePaymentManager,
                        customerAckOnlineMerchandiseManager,
                        customerAckOfflineMerchandiseManager,
                        brokerAckOnlinePaymentManager,
                        brokerAckOfflinePaymentManager,
                        brokerSubmitOnlineMerchandiseManager,
                        brokerSubmitOfflineMerchandiseManager,
                        getSettingsManager(),
                        bitcoinWalletManager,
                        errorManager,
                        this.getPluginVersionReference()

                );

            return walletManager;

        } catch (Exception e) {
            throw new CantGetCryptoCustomerWalletException(FermatException.wrapException(e));
        }
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<>();
        returnedClasses.add("CryptoCustomerWalletModulePluginRoot");
        returnedClasses.add("CryptoCustomerWalletModuleCryptoCustomerWalletManager");

        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {

        // I will check the current values and update the LogLevel in those which is different
        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {

            // if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
            if (CryptoCustomerWalletModulePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                CryptoCustomerWalletModulePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                CryptoCustomerWalletModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                CryptoCustomerWalletModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    /**
     * Static method to get the logging level from any class under root.
     *
     * @param className the class name
     *
     * @return the log level for this class
     */
    public static LogLevel getLogLevelByClass(String className) {
        try {
            // sometimes the classname may be passed dynamically with an $moreText I need to ignore whats after this.
            String[] correctedClass = className.split(Pattern.quote("$"));
            return CryptoCustomerWalletModulePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            // If I couldn't get the correct logging level, then I will set it to minimal.
            return DEFAULT_LOG_LEVEL;
        }
    }

    @Override
    public SettingsManager<CryptoCustomerWalletPreferenceSettings> getSettingsManager() {
        if (this.settingsManager != null)
            return this.settingsManager;

        this.settingsManager = new SettingsManager<>(
                pluginFileSystem,
                pluginId
        );

        return this.settingsManager;
    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        return null;
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {
        cryptoCustomerIdentityManager.createCryptoCustomerIdentity(name, profile_img);
    }

    @Override
    public void setAppPublicKey(String publicKey) {

    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }

    @Override
    public void start() throws CantStartPluginException {
        super.start();
        //preConfigureWallet();
    }

    private void preConfigureWallet() {
        final String customerWalletPublicKey = "crypto_customer_wallet";

        try {
            walletManager = getCryptoCustomerWallet(customerWalletPublicKey);
            //walletManager.isWalletConfigured(customerWalletPublicKey);

            List<CryptoCustomerIdentity> identities = walletManager.getListOfIdentities();

            if( identities.isEmpty() ) {

                createIdentity("Crypto Customer", "", new byte[0]);
                final CryptoCustomerIdentity cryptoCustomerIdentity = walletManager.getListOfIdentities().get(0);
                walletManager.associateIdentity(cryptoCustomerIdentity, customerWalletPublicKey);

                // ASSOCIATION BTC Wallet - Customer Wallet
                InstalledWallet installedWallet = getInstalledWallet(Platforms.CRYPTO_CURRENCY_PLATFORM);
                assert installedWallet != null;

                CryptoCustomerWalletAssociatedSettingImpl associatedWalletSetting = new CryptoCustomerWalletAssociatedSettingImpl();
                associatedWalletSetting.setCustomerPublicKey(customerWalletPublicKey);
                associatedWalletSetting.setId(UUID.randomUUID());
                associatedWalletSetting.setWalletPublicKey(installedWallet.getWalletPublicKey());
                associatedWalletSetting.setPlatform(installedWallet.getPlatform());
                associatedWalletSetting.setMoneyType(MoneyType.CRYPTO);
                associatedWalletSetting.setMerchandise(CryptoCurrency.BITCOIN);
                walletManager.saveWalletSettingAssociated(associatedWalletSetting, customerWalletPublicKey);

                // LOCATIONS
                walletManager.createNewLocation("Torres del Saladillo, Torre Barcelona, Maracaibo, Zulia, Venezuela", customerWalletPublicKey);
                walletManager.createNewLocation("C.C. Sambil, piso 4, local 5, Caracas, Venezuela", customerWalletPublicKey);

                // BANK ACCOUNTS
                walletManager.createNewBankAccount(new CryptoCustomerWalletModuleNegotiationBankAccount(FiatCurrency.US_DOLLAR,
                        "Bank: Banesco\nAccount Number: 1324-548-123456789\nType: Current"));


                new Thread() {
                    @Override
                    public void run() {
                        try {
                            cryptoCustomerIdentityManager.publishIdentity(cryptoCustomerIdentity.getPublicKey());
                        } catch (IdentityNotFoundException e) {
                        } catch (CantPublishIdentityException e) {
                        }
                    }
                }.start();
            }

        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_CUSTOMER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }

    }

    private InstalledWallet getInstalledWallet(Platforms platform) throws CantListWalletsException {
        final List<InstalledWallet> installedWallets = walletManager.getInstallWallets();
        for (InstalledWallet wallet : installedWallets) {
            if (wallet.getPlatform().equals(platform))
                return wallet;
        }
        return null;
    }
}