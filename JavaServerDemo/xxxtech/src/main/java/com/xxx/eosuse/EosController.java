package com.xxx.eosuse;

import com.github.kevinsawicki.http.HttpRequest;
import com.go.basetool.APIResultCode;
import com.google.gson.Gson;
import com.xxx.aos.chain.PackedTransaction;
import com.xxx.aos.chain.SignedTransaction;
import com.xxx.aos.crypto.ec.AosPublicKey;
import com.xxx.aos.aosapi.*;
import com.xxx.aos.aosTypes.AosBuyRam;
import com.xxx.aos.aosTypes.AosDelegate;
import com.xxx.aos.aosTypes.TypePublicKey;
import com.xxx.aos.util.AosTxUtils;
import com.xxx.aos.service.AosToolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class EosController {



    public static final String acountCreatorName = "";//
    public static String acountCreatorNamePrivateKey = "";//

    public static String aosBaseUrl = "http://127.0.0.1:8888/";//
    public static String aosChainBaseUrl = aosBaseUrl+"/v1/chain/";



    public static final Integer conTimeOut = 3000;
    public static final Integer reqTimeOut = 5000;
    public static final String SYSTEM_CONTRACT_NAME_EOS = "aosio";
    public static final Boolean SYSTEM_CONTRACT_DELEGATEBW_ISTRANSFER = false;


    //transfer demo
    @GetMapping(value = "testTransfer")
    @ResponseBody
    public APIResultCode testTransfer() {
        try {
            String testtesttestAccountPrivatekey = "";//测试账号私钥
            PushTxnResponse pushTxnResponse = AosToolService.getInstance().transfer("xxxxxxxxxxxx", "yyyyyyyyyyyy", "AOS", "aosio.token", "0.0001", "test", aosBaseUrl, testtesttestAccountPrivatekey);

        } catch (Exception e) {
            log.error("transfer", e);
        }
        return APIResultCode.SUCCESS;

    }

    //create account example
    @PostMapping(value = "create_free_account")
    @ResponseBody
    public APIResultCode create_free_account(@RequestBody CreateAccountByFreeCodeReq createAccountReq) throws Exception {
        if (createAccountReq.getActive_key().length() != 53 || createAccountReq.getOwner_key().length() != 53) {
            return APIResultCode.PARAMETER_ERROR;
        }

        //创建账号交易
        //get info
        StringBuilder url = new StringBuilder();
        url.append(aosChainBaseUrl).append("get_info");


        String result = HttpRequest.get(url.toString()).body("utf-8");
        EosChainInfo chain_info = new Gson().fromJson(result, EosChainInfo.class);
        EosNewAccount newAccountData = new EosNewAccount(
                acountCreatorName,
                createAccountReq.getAccount_name(),
                TypePublicKey.from(new AosPublicKey(createAccountReq.getOwner_key())),
                TypePublicKey.from(new AosPublicKey(createAccountReq.getActive_key())));
        SignedTransaction unsigned_tx = AosTxUtils.createTransaction(
                SYSTEM_CONTRACT_NAME_EOS,
                newAccountData.getActionName(),
                newAccountData.getAsHex(),
                AosTxUtils.getActivePermission(newAccountData.getCreatorName()),
                chain_info
        );


        //buyram
        AosBuyRam buyRam = new AosBuyRam(
                newAccountData.getCreatorName(),
                createAccountReq.getAccount_name(),
                "3.0000 AOS");
        String buyram_string = new Gson().toJson(buyRam);

        JsonToBinRequest json_obj = new JsonToBinRequest(
                SYSTEM_CONTRACT_NAME_EOS,
                buyRam.getActionName(),
                buyram_string
        );
        String tmp_obj = new Gson().toJson(json_obj);
        url = new StringBuilder().append(aosChainBaseUrl + "abi_json_to_bin");

        result = HttpRequest.post(url.toString()).readTimeout(conTimeOut).connectTimeout(reqTimeOut).send(tmp_obj).body();
        JsonToBinResponse bin_obj = new Gson().fromJson(result, JsonToBinResponse.class);
        unsigned_tx = AosTxUtils.addAction(unsigned_tx,
                SYSTEM_CONTRACT_NAME_EOS,
                buyRam.getActionName(),
                bin_obj.getBinargs(),
                AosTxUtils.getActivePermission(newAccountData.getCreatorName()),
                chain_info
        );


        //delegatebw
        AosDelegate delegate = new AosDelegate(
                newAccountData.getCreatorName(),
                createAccountReq.getAccount_name(),
                "5.0000 AOS",
                "5.0000 AOS",
                SYSTEM_CONTRACT_DELEGATEBW_ISTRANSFER);
        String delegate_string = new Gson().toJson(delegate);
        JsonToBinRequest json_obj1 = new JsonToBinRequest(
                SYSTEM_CONTRACT_NAME_EOS,
                delegate.getActionName(),
                delegate_string
        );
        String tmp_obj1 = new Gson().toJson(json_obj1);
        url = new StringBuilder();
        url.append(aosChainBaseUrl+ "abi_json_to_bin");

        result = HttpRequest.post(url.toString()).readTimeout(conTimeOut).connectTimeout(reqTimeOut).send(tmp_obj1).body();
        JsonToBinResponse bin_obj1 = new Gson().fromJson(result, JsonToBinResponse.class);
        unsigned_tx = AosTxUtils.addAction(unsigned_tx,
                SYSTEM_CONTRACT_NAME_EOS,
                delegate.getActionName(),
                bin_obj1.getBinargs(),
                AosTxUtils.getActivePermission(newAccountData.getCreatorName()),
                chain_info
        );


        //sign tx for new account
        SignedTransaction signed_tx = AosTxUtils.signTransaction(unsigned_tx, acountCreatorNamePrivateKey, chain_info.getChain_id());
        tmp_obj = new Gson().toJson(new PackedTransaction(signed_tx));
        //send tx for new account
        url = new StringBuilder();
        url.append(aosChainBaseUrl + "push_transaction");
        result = HttpRequest.post(url.toString()).readTimeout(conTimeOut).connectTimeout(reqTimeOut).send(tmp_obj).body();

        log.info("createacount result:" + result);
        if (result.indexOf("transaction_id") != -1) {
            return APIResultCode.SUCCESS;
        } else {
            return APIResultCode.UNKNOWN_ERROR;
        }
    }
}
