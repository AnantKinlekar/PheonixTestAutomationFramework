package com.api.utils;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.response.LogicalResponse;

import java.util.Map;

public class VaultDemo {
    public static void main(String[] args) throws VaultException {
        System.out.println(System.getenv("VAULT_SERVER"));
//        VaultConfig vaultConfig = new VaultConfig().address("http://3.110.100.188:8200/")
//                .token("root")
//                .build();
//
//        Vault vault = new Vault(vaultConfig);
//        LogicalResponse logicalResponse = vault.logical().read("secret/pheonix/qa/database");
//        Map<String, String> data = logicalResponse.getData();
//        System.out.println(data.get("DB_URL"));
//        System.out.println(data.get("DB_USERNAME"));
//        System.out.println(data.get("DB_PASSWORD"));
    }
}
