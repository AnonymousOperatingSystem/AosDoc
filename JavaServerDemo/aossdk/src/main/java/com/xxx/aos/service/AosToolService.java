/*
 * Copyright (c) 2017-2018 PLACTAL.
 *
 * The MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xxx.aos.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.xxx.aos.dto.AosTransfer;
import com.xxx.aos.chain.Action;
import com.xxx.aos.chain.PackedTransaction;
import com.xxx.aos.chain.SignedTransaction;
import com.xxx.aos.crypto.ec.AosPrivateKey;
import com.xxx.aos.dto.TransferJson;
import com.xxx.aos.aosapi.*;
import com.xxx.aos.aosTypes.TypeChainId;
import com.xxx.aos.util.Consts;
import com.xxx.aos.util.NodeosApi;

import java.util.ArrayList;


public class AosToolService {

    private final NodeosApi mNodeosApi = new NodeosApi();

    private static AosToolService instance = null;

    private AosToolService(){

    }
    public static AosToolService getInstance() {
        if (null == instance) {
            instance = new AosToolService();
        }
        return instance;
    }

    public EosChainInfo getChainInfo(String baseUrl) {

        return mNodeosApi.readInfo("get_info", baseUrl);
    }

    public PushTxnResponse transfer(String from, String to, String symbolName, String contractName, String amount, String memo, String baseUrl, String privateKey) throws Exception {

        TransferJson transferJson = new TransferJson(from, to, amount + " " + symbolName, memo);

        PushTxnResponse pushTxnResponse = pushAction(contractName, "transfer", new Gson().toJson(transferJson), getActivePermission(from), baseUrl, privateKey);
        return pushTxnResponse;
    }

    private SignedTransaction createTransaction(String contract, String actionName, String dataAsHex,
                                                String[] permissions, EosChainInfo chainInfo) {
        Action action = new Action(contract, actionName);
        action.setAuthorization(permissions);
        action.setData(dataAsHex);

        SignedTransaction txn = new SignedTransaction();
        txn.addAction(action);
        txn.putSignatures(new ArrayList<>());


        if (null != chainInfo) {
            txn.setReferenceBlock(chainInfo.getHeadBlockId());
            txn.setExpiration(chainInfo.getTimeAfterHeadBlockTime(Consts.TX_EXPIRATION_IN_MILSEC));
        }

        return txn;
    }


    public SignedTransaction signTransaction(final SignedTransaction txn,
                                             final AosPrivateKey privKey, final TypeChainId id) throws IllegalStateException{

        SignedTransaction stxn = new SignedTransaction( txn );
        stxn.sign( privKey, id);
        return stxn;
    }

    private PackedTransaction signAndPackTransaction(SignedTransaction txnBeforeSign, EosChainInfo info,String transferprivatekey) {
        final SignedTransaction stxn;
        stxn = signTransaction(txnBeforeSign, new AosPrivateKey(transferprivatekey), new TypeChainId(info.getChain_id()));
        return new PackedTransaction(stxn);
    }


    public String[] getActivePermission(String accountName) {
        return new String[]{accountName + "@active"};
    }


    public JsonObject readAccountInfo(String accountName, String baseUrl) {
        return mNodeosApi.getAccountInfo(new AccountInfoRequest(accountName), baseUrl);
    }


    public PushTxnResponse pushAction(String contract, String action, String data, String[] permissions, String baseUrl, String transferprivatekey) {

        JsonToBinResponse jsonToBinResp = mNodeosApi.jsonToBin(new JsonToBinRequest(contract, action, data), baseUrl);
        EosChainInfo chainInfo = getChainInfo(baseUrl);
        SignedTransaction st = createTransaction(contract, action, jsonToBinResp.getBinargs(), permissions, chainInfo);
        PushTxnResponse pushTxnResponse = mNodeosApi.pushTransaction(signAndPackTransaction(st, chainInfo, transferprivatekey), baseUrl);
        pushTxnResponse.setBlockNum(chainInfo.getHeadBlockNum());
        return pushTxnResponse;
    }
}
