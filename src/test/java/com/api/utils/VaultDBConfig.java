package com.api.utils;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.response.LogicalResponse;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class VaultDBConfig {
    private static final Logger LOGGER = LogManager.getLogger(VaultDBConfig.class);

    private static final VaultConfig vaultConfig;
    private static final Vault vault;

    static {
        try {
            vaultConfig = new VaultConfig().address(System.getenv("VAULT_SERVER"))
                    .token(System.getenv("VAULT_TOKEN"))
                    .build();
            vault = new Vault(vaultConfig);
        } catch (VaultException e) {
            LOGGER.error("Something went wrong with the vault config", e);
            throw new RuntimeException(e);
        }
    }
    private VaultDBConfig() {

    }

    @Step("Retrieving the secret from the Vault")
    public static String getSecret(String key){

        LogicalResponse logicalResponse = null;
        try {
            logicalResponse = vault.logical().read("secret/pheonix/qa/database");
        } catch (VaultException e) {
            LOGGER.error("Something went wrong reading the vault response", e);
            e.printStackTrace();
            return null; //if something goes wrong return null
        }
        Map<String, String> dataMap = logicalResponse.getData();
        LOGGER.info("Secret Found in the Vault");
        return dataMap.get(key);
    }
}
