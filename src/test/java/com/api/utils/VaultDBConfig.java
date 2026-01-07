package com.api.utils;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.response.LogicalResponse;
import java.util.Map;

public class VaultDBConfig {

    private static final VaultConfig vaultConfig;
    private static final Vault vault;

    static {
        try {
            vaultConfig = new VaultConfig().address(System.getenv("VAULT_SERVER"))
                    .token(System.getenv("VAULT_TOKEN"))
                    .build();
            vault = new Vault(vaultConfig);
        } catch (VaultException e) {
            throw new RuntimeException(e);
        }
    }
    private VaultDBConfig() {

    }

    public static String getSecret(String key){

        LogicalResponse logicalResponse = null;
        try {
            logicalResponse = vault.logical().read("secret/pheonix/qa/database");
        } catch (VaultException e) {
            e.printStackTrace();
            return null; //if something goes wrong return null
        }
        Map<String, String> dataMap = logicalResponse.getData();
        return dataMap.get(key);
    }
}
