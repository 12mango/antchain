package com.example.demo.service.Impl;

import java.io.IOException;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import com.alipay.mychain.sdk.api.MychainClient;
import com.alipay.mychain.sdk.api.callback.IEventCallback;
import com.alipay.mychain.sdk.api.env.ClientEnv;
import com.alipay.mychain.sdk.api.env.ISslOption;
import com.alipay.mychain.sdk.api.env.SignerOption;
import com.alipay.mychain.sdk.api.env.SslBytesOption;
import com.alipay.mychain.sdk.api.logging.AbstractLoggerFactory;
import com.alipay.mychain.sdk.api.logging.ILogger;
import com.alipay.mychain.sdk.api.utils.ConfidentialUtil;
import com.alipay.mychain.sdk.api.utils.Utils;
import com.alipay.mychain.sdk.common.VMTypeEnum;
import com.alipay.mychain.sdk.crypto.MyCrypto;
import com.alipay.mychain.sdk.crypto.hash.Hash;
import com.alipay.mychain.sdk.crypto.keyoperator.Pkcs8KeyOperator;
import com.alipay.mychain.sdk.crypto.keypair.Keypair;
import com.alipay.mychain.sdk.crypto.signer.SignerBase;
import com.alipay.mychain.sdk.domain.account.Identity;
import com.alipay.mychain.sdk.domain.event.EventModelType;
import com.alipay.mychain.sdk.errorcode.ErrorCode;
import com.alipay.mychain.sdk.message.Message;
import com.alipay.mychain.sdk.message.event.PushContractEvent;
import com.alipay.mychain.sdk.message.transaction.AbstractTransactionRequest;
import com.alipay.mychain.sdk.message.transaction.TransactionReceiptResponse;
import com.alipay.mychain.sdk.message.transaction.confidential.ConfidentialRequest;
import com.alipay.mychain.sdk.message.transaction.contract.*;
import com.alipay.mychain.sdk.type.BaseFixedSizeUnsignedInteger;
import com.alipay.mychain.sdk.utils.ByteUtils;
import com.alipay.mychain.sdk.utils.IOUtil;
import com.alipay.mychain.sdk.utils.RandomUtil;
import com.alipay.mychain.sdk.vm.EVMOutput;
import com.alipay.mychain.sdk.vm.EVMParameter;
import org.springframework.stereotype.Service;

@Service
public class ContractService {
    private static String contractCodeString = "60806040526000612af8556000612af9556000612afa556000612afb5534801561002857600080fd5b50610c15806100386000396000f30060806040526004361061006d576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063546e36f61461007257806359fd7e4c146100af5780636f01c90e146100ef5780636f71da011461012f578063e3b71d9e1461016c575b600080fd5b34801561007e57600080fd5b5061009960048036036100949190810190610832565b6101a9565b6040516100a69190610a5d565b60405180910390f35b3480156100bb57600080fd5b506100d660048036036100d1919081019061085b565b610218565b6040516100e69493929190610ac4565b60405180910390f35b3480156100fb57600080fd5b506101166004803603610111919081019061085b565b6102d4565b6040516101269493929190610a78565b60405180910390f35b34801561013b57600080fd5b506101566004803603610151919081019061079f565b610428565b60405161016391906109fb565b60405180910390f35b34801561017857600080fd5b50610193600480360361018e9190810190610832565b61058e565b6040516101a09190610a5d565b60405180910390f35b6000806000809150612afa5490505b612afb5481101561020e5783611388826103e8811015156101d557fe5b6006020160010154141561020157611388816103e8811015156101f457fe5b6006020160000154820191505b80806001019150506101b8565b8192505050919050565b60008060006060876000612af9546103e88110151561023357fe5b6005020160020181905550866000612af9546103e88110151561025257fe5b6005020160010181905550856000612af9546103e88110151561027157fe5b6005020160030181905550846000612af9546103e88110151561029057fe5b6005020160040190805190602001906102aa929190610690565b506001612af960008282540192505081905550878787879350935093509350945094509450949050565b600080606060008660008060009150612af85490505b612af95481101561032a57826000826103e88110151561030657fe5b6005020160030154141561031d576001915061032a565b80806001019150506102ea565b81151561036c576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161036390610a1d565b60405180910390fd5b8a611388612afb546103e88110151561038157fe5b600602016000018190555089611388612afb546103e8811015156103a157fe5b600602016001018190555088611388612afb546103e8811015156103c157fe5b600602016003018190555087611388612afb546103e8811015156103e157fe5b6006020160020190805190602001906103fb929190610690565b506001612afb60008282540192505081905550898b898b9650965096509650505050945094509450949050565b606060008460008060009150612afa5490505b612afb5481101561047c5782611388826103e88110151561045857fe5b6006020160010154141561046f576001915061047c565b808060010191505061043b565b8115156104be576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016104b590610a3d565b60405180910390fd5b612afa5493505b612afb5484101561057f5787611388856103e8811015156104e257fe5b600602016001015414801561050e575086611388856103e88110151561050457fe5b6006020160030154145b156105725788611388856103e88110151561052557fe5b60060201600401908051906020019061053f929190610690565b5085611388856103e88110151561055257fe5b60060201600501908051906020019061056c929190610690565b5061057f565b83806001019450506104c5565b88945050505050949350505050565b60008060008360008060009150612af85490505b612af9548110156105e257826000826103e8811015156105be57fe5b600502016003015414156105d557600191506105e2565b80806001019150506105a2565b811515610624576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161061b90610a1d565b60405180910390fd5b60009450612af85493505b612af95484101561068357866000856103e88110151561064b57fe5b60050201600301541415610676576000846103e88110151561066957fe5b6005020160010154850194505b838060010194505061062f565b8495505050505050919050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106106d157805160ff19168380011785556106ff565b828001600101855582156106ff579182015b828111156106fe5782518255916020019190600101906106e3565b5b50905061070c9190610710565b5090565b61073291905b8082111561072e576000816000905550600101610716565b5090565b90565b600082601f830112151561074857600080fd5b813561075b61075682610b3d565b610b10565b9150808252602083016020830185838301111561077757600080fd5b610782838284610b88565b50505092915050565b60006107978235610b7e565b905092915050565b600080600080608085870312156107b557600080fd5b600085013567ffffffffffffffff8111156107cf57600080fd5b6107db87828801610735565b94505060206107ec8782880161078b565b93505060406107fd8782880161078b565b925050606085013567ffffffffffffffff81111561081a57600080fd5b61082687828801610735565b91505092959194509250565b60006020828403121561084457600080fd5b60006108528482850161078b565b91505092915050565b6000806000806080858703121561087157600080fd5b600061087f8782880161078b565b94505060206108908782880161078b565b93505060406108a18782880161078b565b925050606085013567ffffffffffffffff8111156108be57600080fd5b6108ca87828801610735565b91505092959194509250565b60006108e182610b69565b8084526108f5816020860160208601610b97565b6108fe81610bca565b602085010191505092915050565b6000602182527fe8afa5e6b4bbe58aa8e8bf98e69caae5bc80e5a78be58b9fe99b86e8b584e98760208301527f91000000000000000000000000000000000000000000000000000000000000006040830152606082019050919050565b6000604582527fe8b584e98791e4bdbfe794a8e8aeb0e5bd95e4b8ade4b88de5ad98e59ca8e79b60208301527faee6a087e6b4bbe58aa8e79a84e8b584e98791e4bdbfe794a8e68385e586b5e860408301527faeb0e5bd950000000000000000000000000000000000000000000000000000006060830152608082019050919050565b6109f581610b74565b82525050565b60006020820190508181036000830152610a1581846108d6565b905092915050565b60006020820190508181036000830152610a368161090c565b9050919050565b60006020820190508181036000830152610a5681610969565b9050919050565b6000602082019050610a7260008301846109ec565b92915050565b6000608082019050610a8d60008301876109ec565b610a9a60208301866109ec565b8181036040830152610aac81856108d6565b9050610abb60608301846109ec565b95945050505050565b6000608082019050610ad960008301876109ec565b610ae660208301866109ec565b610af360408301856109ec565b8181036060830152610b0581846108d6565b905095945050505050565b6000604051905081810181811067ffffffffffffffff82111715610b3357600080fd5b8060405250919050565b600067ffffffffffffffff821115610b5457600080fd5b601f19601f8301169050602081019050919050565b600081519050919050565b6000819050919050565b6000819050919050565b82818337600083830152505050565b60005b83811015610bb5578082015181840152602081019050610b9a565b83811115610bc4576000848401525b50505050565b6000601f19601f83011690509190505600a265627a7a72305820887b541031e93ea99708d0f0d2b0fc9630cb398f706895a6dc680a7f7b761bfe6c6578706572696d656e74616cf50037";
    private static byte[] contractCode = ByteUtils.hexStringToBytes(contractCodeString); //CreditManager

    //upgrade
    private static String contractUpdateCodeString = "60806040526004361061006d576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680631b3c4fab1461007257806357fce39d14610187578063af7c102c146101b2578063b2628df8146101f3578063d448601914610242575b600080fd5b34801561007e57600080fd5b50610087610291565b60405180868152602001806020018060200185151515158152602001848152602001838103835287818151815260200191508051906020019080838360005b838110156100e15780820151818401526020810190506100c6565b50505050905090810190601f16801561010e5780820380516001836020036101000a031916815260200191505b50838103825286818151815260200191508051906020019080838360005b8381101561014757808201518184015260208101905061012c565b50505050905090810190601f1680156101745780820380516001836020036101000a031916815260200191505b5097505050505050505060405180910390f35b34801561019357600080fd5b5061019c610337565b6040518082815260200191505060405180910390f35b3480156101be57600080fd5b506101dd60048036038101908080359060200190929190505050610341565b6040518082815260200191505060405180910390f35b3480156101ff57600080fd5b50610228600480360381019080803590602001909291908035906020019092919050505061035e565b604051808215151515815260200191505060405180910390f35b34801561024e57600080fd5b506102776004803603810190808035906020019092919080359060200190929190505050610523565b604051808215151515815260200191505060405180910390f35b6000606080600080606080600080600033905060c89250600091506040805190810160405280600781526020017f6a72626c6f636b0000000000000000000000000000000000000000000000000081525094506040805190810160405280601a81526020017f32303231303533316a72626c6f636b636f6e7261637463616c6c000000000000815250935082858584849950995099509950995050505050509091929394565b6000600254905090565b600060036000838152602001908152602001600020549050919050565b6000600254331415156103d9576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260118152602001807f5065726d697373696f6e2064656e69656400000000000000000000000000000081525060200191505060405180910390fd5b6000548260015401131580156103f457506001548260015401135b80156104005750600082135b1515610474576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600e8152602001807f496e76616c69642076616c75652100000000000000000000000000000000000081525060200191505060405180910390fd5b8160036000858152602001908152602001600020600082825401925050819055508160016000828254019250508190555081837f9a46fdc9277c739031110f773b36080a9a2012d0b3eca1f5ed8a3403973e05576001546040518080602001838152602001828103825260048152602001807f64656d6f000000000000000000000000000000000000000000000000000000008152506020019250505060405180910390a36001905092915050565b6000816003600033815260200190815260200160002054121515156105b0576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260138152602001807f62616c616e6365206e6f7420656e6f756768210000000000000000000000000081525060200191505060405180910390fd5b6000821380156105c257506000548213155b1515610636576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600e8152602001807f496e76616c69642076616c75652100000000000000000000000000000000000081525060200191505060405180910390fd5b81600360003381526020019081526020016000206000828254039250508190555081600360008581526020019081526020016000206000828254019250508190555060019050929150505600a165627a7a72305820929f39f5dfc978f05e029b986659fd7542e1009cbbb133b2bc009f8876b59c910029";
    private static byte[] contractUpdateCode = ByteUtils.hexStringToBytes(contractUpdateCodeString); //CreditManager



    /**
     * contract id
     */
    private static String callContractId = "wuda1626576149338";

    private static final String account = "TimWong";
    private static Identity userIdentity;
    private static Keypair userKeypair;

    /**
     * sdk client
     */
    private static MychainClient sdk;

    /**
     * client key password
     */
    private static String keyPassword = "Kiss69080@";
    /**
     * user password
     */
    private static String userPassword = "Kiss69080@";
    /**
     * host ip
     */

    private static String host = "47.103.163.48";

    /**
     * server port
     */
    private static int port = 18130;
    /**
     * trustCa password.
     */
    private static String trustStorePassword = "mychain";
    /**
     * mychain environment
     */
    private static ClientEnv env;
    /**
     * mychain is tee Chain
     */
    private static boolean isTeeChain = false;
    /**
     * tee chain publicKeys
     */
    private static List<byte[]> publicKeys = new ArrayList<byte[]>();
    /**
     * tee chain secretKey
     */
    private static String secretKey = "123456";


    public static void initMychainEnv() throws IOException {
        // any user key for sign message
        String userPrivateKeyFile = "user.key";
        userIdentity = Utils.getIdentityByName(account);
        Pkcs8KeyOperator pkcs8KeyOperator = new Pkcs8KeyOperator();
        userKeypair = pkcs8KeyOperator.load(IOUtil.inputStreamToByte(ContractService.class.getClassLoader().getResourceAsStream(userPrivateKeyFile)), userPassword);

        // use publicKeys by tee
        if (isTeeChain) {
            Keypair keypair = new Pkcs8KeyOperator()
                    .loadPubkey(
                            IOUtil.inputStreamToByte(ContractService.class.getClassLoader().getResourceAsStream("test_seal_pubkey.pem")));
            byte[] publicKeyDer = keypair.getPubkeyEncoded();
            publicKeys.add(publicKeyDer);
        }

        env = buildMychainEnv();
        ILogger logger = AbstractLoggerFactory.getInstance(ContractService.class);
        env.setLogger(logger);
    }

    public static ClientEnv buildMychainEnv() throws IOException {
        InetSocketAddress inetSocketAddress = InetSocketAddress.createUnresolved(host, port);
        String keyFilePath = "client.key";
        String certFilePath = "client.crt";
        String trustStoreFilePath = "trustCa";
        // build ssl option
        ISslOption sslOption = new SslBytesOption.Builder()
                .keyBytes(IOUtil.inputStreamToByte(ContractService.class.getClassLoader().getResourceAsStream(keyFilePath)))
                .certBytes(IOUtil.inputStreamToByte(ContractService.class.getClassLoader().getResourceAsStream(certFilePath)))
                .keyPassword(keyPassword)
                .trustStorePassword(trustStorePassword)
                .trustStoreBytes(
                        IOUtil.inputStreamToByte(ContractService.class.getClassLoader().getResourceAsStream(trustStoreFilePath)))
                .build();

        List<InetSocketAddress> socketAddressArrayList = new ArrayList<InetSocketAddress>();
        socketAddressArrayList.add(inetSocketAddress);

        List<SignerBase> signerBaseList = new ArrayList<SignerBase>();
        SignerBase signerBase = MyCrypto.getInstance().createSigner(userKeypair);
        signerBaseList.add(signerBase);
        SignerOption signerOption = new SignerOption();
        signerOption.setSigners(signerBaseList);
        return ClientEnv.build(socketAddressArrayList, sslOption, signerOption);
    }

    public static void initSdk() {
        sdk = new MychainClient();
        boolean initResult = sdk.init(env);
        if (!initResult) {
            exit("initSdk", "sdk init failed.");
        }else{
            System.out.println("sdk init success");
        }
    }

    private static String getErrorMsg(int errorCode) {
        int minMychainSdkErrorCode = ErrorCode.SDK_INTERNAL_ERROR.getErrorCode();
        if (errorCode < minMychainSdkErrorCode) {
            return ErrorCode.valueOf(errorCode).getErrorDesc();
        } else {
            return ErrorCode.valueOf(errorCode).getErrorDesc();
        }
    }

    private static void exit(String tag, String msg) {
        exit(String.format("%s error : %s ", tag, msg));
    }

    private static void exit(String msg) {
        System.out.println(msg);
        System.exit(0);
    }

    private static void signRequest(AbstractTransactionRequest request) {
        // sign request
        long ts = sdk.getNetwork().getSystemTimestamp();
        request.setTxTimeNonce(ts, BaseFixedSizeUnsignedInteger.Fixed64BitUnsignedInteger
                .valueOf(RandomUtil.randomize(ts + request.getTransaction().hashCode())), true);
        request.complete();
        sdk.getConfidentialService().signRequest(env.getSignerOption().getSigners(), request);
    }

    private static void deployContract() {
        EVMParameter contractParameters = new EVMParameter();
        String contractId = "wuda" + System.currentTimeMillis();

        // build DeployContractRequest
        DeployContractRequest request = new DeployContractRequest(userIdentity,
                Utils.getIdentityByName(contractId), contractCode, VMTypeEnum.EVM,
                contractParameters, BigInteger.ZERO);

        TransactionReceiptResponse deployContractResult;
        if (isTeeChain) {
            signRequest(request);
            // generate transaction key
            byte[] transactionKey = ConfidentialUtil.keyGenerate(secretKey,
                    request.getTransaction().getHash().getValue());

            ConfidentialRequest confidentialRequest = new ConfidentialRequest(request, publicKeys, transactionKey);
            deployContractResult = sdk.getConfidentialService().confidentialRequest(confidentialRequest);
        } else {
            deployContractResult = sdk.getContractService().deployContract(request);
        }

        // deploy contract
        if (!deployContractResult.isSuccess()
                || deployContractResult.getTransactionReceipt().getResult() != 0) {
            exit("deployContract",
                    getErrorMsg((int) deployContractResult.getTransactionReceipt().getResult()));
        } else {
            System.out.println("deploy contract success.contact id is " + contractId);
        }
    }

    //√ 收钱:     function ReceiveMoney(uint _UserID, uint _Money, uint _EventID, bytes32 _Time) public returns(uint , uint , uint , bytes32)
    public static Hash callContractReceiveMoney(Integer UserId, Integer Money, Integer EventId, String time) throws IOException {

        initMychainEnv();
        initSdk();

        EVMParameter parameters = new EVMParameter("ReceiveMoney(uint256,uint256,uint256,string)");
        //parameters.addIdentity(Utils.getIdentityByName(accountName));
        //parameters.addUint(BigInteger.valueOf(amount));
        parameters.addUint(BigInteger.valueOf(UserId));
        parameters.addUint(BigInteger.valueOf(Money));
        parameters.addUint(BigInteger.valueOf(EventId));
        parameters.addString(time);

        // build CallContractRequest
        CallContractRequest request = new CallContractRequest(userIdentity,
                Utils.getIdentityByName(callContractId), parameters, BigInteger.ZERO, VMTypeEnum.EVM);

        TransactionReceiptResponse callContractResult;
        if (isTeeChain) {
            signRequest(request);
            // generate transaction key
            byte[] transactionKey = ConfidentialUtil.keyGenerate(secretKey,
                    request.getTransaction().getHash().getValue());

            ConfidentialRequest confidentialRequest = new ConfidentialRequest(request, publicKeys, transactionKey);

            callContractResult = sdk.getConfidentialService().confidentialRequest(confidentialRequest);
        } else {
            callContractResult = sdk.getContractService().callContract(request);
        }

        if (!callContractResult.isSuccess() || callContractResult.getTransactionReceipt().getResult() != 0) {
            System.out.println("callContract Error :"  + getErrorMsg((int) callContractResult.getTransactionReceipt().getResult()));
        } else {
            byte[] output = callContractResult.getTransactionReceipt().getOutput();
            if (output == null) {
                exit("call issue function", "output failed");
            } else {
                // decode return values
                EVMOutput contractReturnValues = new EVMOutput(ByteUtils.toHexString(output));
                //System.out.println("call callContractIssueCredit success, response value: " + contractReturnValues.getBoolean());
                //System.out.println("call callContractReceiveMoney success, response value of UserId: " + contractReturnValues.getUint());
                //System.out.println("call callContractReceiveMoney success, response value of Money: " + contractReturnValues.getUint());
                //System.out.println("call callContractReceiveMoney success, response value of EventId: " + contractReturnValues.getUint());
                // System.out.println("call callContractReceiveMoney success, response value of time: " + contractReturnValues.getBytes32());
                //System.out.println("txHash: " + callContractResult.getTxHash());

                return callContractResult.getTxHash();
            }
        }
        return null;
    }

    //√ 查询收钱 function QueryReceiveMoney (uint _EventID, uint _UserID) public OnlyEventInRM_Records(_EventID)returns(uint[], bytes32[], uint)
    private static void callContractQueryReceiveMoney(Integer EventId, Integer UseId) {
        EVMParameter parameters = new EVMParameter("QueryReceiveMoney(uint256,uint256)");
        parameters.addUint(BigInteger.valueOf(EventId));
        parameters.addUint(BigInteger.valueOf(UseId));

        // build CallContractRequest
        CallContractRequest request = new CallContractRequest(userIdentity,
                Utils.getIdentityByName(callContractId), parameters, BigInteger.ZERO, VMTypeEnum.EVM);

        TransactionReceiptResponse callContractResult;
        if (isTeeChain) {
            signRequest(request);
            // generate transaction key
            byte[] transactionKey = ConfidentialUtil.keyGenerate(secretKey,
                    request.getTransaction().getHash().getValue());

            ConfidentialRequest confidentialRequest = new ConfidentialRequest(request, publicKeys, transactionKey);

            callContractResult = sdk.getConfidentialService().confidentialRequest(confidentialRequest);
        } else {
            callContractResult = sdk.getContractService().callContract(request);
        }

        if (!callContractResult.isSuccess() || callContractResult.getTransactionReceipt().getResult() != 0) {
            System.out.println("callContract Error :"  + getErrorMsg((int) callContractResult.getTransactionReceipt().getResult()));
        } else {
            byte[] output = callContractResult.getTransactionReceipt().getOutput();
            if (output == null) {
                exit("call QueryCredit function", "output failed");
            } else {
                // decode return values
                EVMOutput contractReturnValues = new EVMOutput(ByteUtils.toHexString(output));
                System.out.println("call callContractQeuryReceiveMoney success, response value of ReceiveMoney is: " + contractReturnValues.getUint());
                // System.out.println("call callContractQeuryReceiveMoney success, response value of receiveTime is: " + contractReturnValues.getUint());
                // System.out.println("call callContractQeuryReceiveMoney success, response value of receiveNum is: " + contractReturnValues.getUint());
                System.out.println("txHash: " + callContractResult.getTxHash());
            }
        }
    }

    //√ 查询收款总额  function Sum_of_money_in_RM (uint _EventID) public OnlyEventInRM_Records(_EventID) returns (uint)
    private static void callContractSumReceiveMoney(Integer EventId) {
        EVMParameter parameters = new EVMParameter("Sum_of_money_in_RM(uint256)");
        parameters.addUint(BigInteger.valueOf(EventId));

        // build CallContractRequest
        CallContractRequest request = new CallContractRequest(userIdentity,
                Utils.getIdentityByName(callContractId), parameters, BigInteger.ZERO, VMTypeEnum.EVM);

        TransactionReceiptResponse callContractResult;
        if (isTeeChain) {
            signRequest(request);
            // generate transaction key
            byte[] transactionKey = ConfidentialUtil.keyGenerate(secretKey,
                    request.getTransaction().getHash().getValue());

            ConfidentialRequest confidentialRequest = new ConfidentialRequest(request, publicKeys, transactionKey);

            callContractResult = sdk.getConfidentialService().confidentialRequest(confidentialRequest);
        } else {
            callContractResult = sdk.getContractService().callContract(request);
        }

        if (!callContractResult.isSuccess() || callContractResult.getTransactionReceipt().getResult() != 0) {
            System.out.println("callContract Error :"  + getErrorMsg((int) callContractResult.getTransactionReceipt().getResult()));
        } else {
            byte[] output = callContractResult.getTransactionReceipt().getOutput();
            if (output == null) {
                exit("call QueryCredit function", "output failed");
            } else {
                // decode return values
                EVMOutput contractReturnValues = new EVMOutput(ByteUtils.toHexString(output));
                System.out.println("call callContractSumReceiveMoney success, response value of SumReceiveMoney is: " + contractReturnValues.getUint());
                System.out.println("txHash: " + callContractResult.getTxHash());
            }
        }
    }

    // 花钱  function SpendMoney(uint _Money, uint _EventID, uint _TransactionID, string _Time) public OnlyEventInRM_Records(_EventID) Verify_Money_Enough(_EventID,_Money) returns(uint ,uint , bytes32, uint)
    public static Hash callContractSpendMoney(Integer money, Integer EventId, Integer TransactionId, String time) throws IOException {

        initMychainEnv();
        initSdk();

        EVMParameter parameters = new EVMParameter("SpendMoney(uint256,uint256,uint256,string)");
        parameters.addUint(BigInteger.valueOf(money));
        parameters.addUint(BigInteger.valueOf(EventId));
        parameters.addUint(BigInteger.valueOf(TransactionId));
        parameters.addString(time);


        // build CallContractRequest
        CallContractRequest request = new CallContractRequest(userIdentity,
                Utils.getIdentityByName(callContractId), parameters, BigInteger.ZERO, VMTypeEnum.EVM);

        TransactionReceiptResponse callContractResult;
        if (isTeeChain) {
            signRequest(request);
            // generate transaction key
            byte[] transactionKey = ConfidentialUtil.keyGenerate(secretKey,
                    request.getTransaction().getHash().getValue());

            ConfidentialRequest confidentialRequest = new ConfidentialRequest(request, publicKeys, transactionKey);

            callContractResult = sdk.getConfidentialService().confidentialRequest(confidentialRequest);
        } else {
            callContractResult = sdk.getContractService().callContract(request);
        }

        if (!callContractResult.isSuccess() || callContractResult.getTransactionReceipt().getResult() != 0) {
            System.out.println("callContract Error :"  + getErrorMsg((int) callContractResult.getTransactionReceipt().getResult()));
        } else {
            byte[] output = callContractResult.getTransactionReceipt().getOutput();
            if (output == null) {
                exit("call callContractTransferCredit function", "output failed");
            } else {
                // decode return values
                EVMOutput contractReturnValues = new EVMOutput(ByteUtils.toHexString(output));
                //System.out.println("call callContractTransferCredit success, response value of EventId: " + contractReturnValues.getUint());
                //System.out.println("call callContractTransferCredit success, response value of spendmoneyonce: " + contractReturnValues.getUint());
                //System.out.println("txHash: " + callContractResult.getTxHash());
                return callContractResult.getTxHash();
            }
        }
        return null;
    }

    // 上传凭证  function UploadProvidence (bytes32 _ProvidenceHash, uint _EventID, uint _TransactionID, string _Time) public OnlyEventInSM_Records(_EventID) returns(string)
    public static Hash callContractUploadProvidence(String ProvidenceHash, Integer EventId, Integer TransactionId, String time) throws IOException {

        initMychainEnv();
        initSdk();

        EVMParameter parameters = new EVMParameter("UploadProvidence(string,uint256,uint256,string)");
        parameters.addString(ProvidenceHash);
        parameters.addUint(BigInteger.valueOf(EventId));
        parameters.addUint(BigInteger.valueOf(TransactionId));
        parameters.addString(time);


        // build CallContractRequest
        CallContractRequest request = new CallContractRequest(userIdentity,
                Utils.getIdentityByName(callContractId), parameters, BigInteger.ZERO, VMTypeEnum.EVM);

        TransactionReceiptResponse callContractResult;
        if (isTeeChain) {
            signRequest(request);
            // generate transaction key
            byte[] transactionKey = ConfidentialUtil.keyGenerate(secretKey,
                    request.getTransaction().getHash().getValue());

            ConfidentialRequest confidentialRequest = new ConfidentialRequest(request, publicKeys, transactionKey);

            callContractResult = sdk.getConfidentialService().confidentialRequest(confidentialRequest);
        } else {
            callContractResult = sdk.getContractService().callContract(request);
        }

        if (!callContractResult.isSuccess() || callContractResult.getTransactionReceipt().getResult() != 0) {
            System.out.println("callContract Error :"  + getErrorMsg((int) callContractResult.getTransactionReceipt().getResult()));
        } else {
            byte[] output = callContractResult.getTransactionReceipt().getOutput();
            if (output == null) {
                exit("call QueryCredit function", "output failed");
            } else {
                // decode return values
                EVMOutput contractReturnValues = new EVMOutput(ByteUtils.toHexString(output));
                //System.out.println("call callContractSumReceiveMoney success, response value of Result: " + contractReturnValues.getString());
                System.out.println("txHash: " + callContractResult.getTxHash());
                return callContractResult.getTxHash();
            }
        }
        return null;
    }

    // 查询花钱  function QuerySpendMoney (uint _EventID) public OnlyEventInSM_Records(_EventID) returns (uint[], bytes32[], uint, bytes32[])
    /* private static void callContractQuerySpendMoney(Integer EventId) {
        EVMParameter parameters = new EVMParameter("QuerySpendMoney(uint256)");
        parameters.addUint(BigInteger.valueOf(EventId));

        // build CallContractRequest
        CallContractRequest request = new CallContractRequest(userIdentity,
                Utils.getIdentityByName(callContractId), parameters, BigInteger.ZERO, VMTypeEnum.EVM);

        TransactionReceiptResponse callContractResult;
        if (isTeeChain) {
            signRequest(request);
            // generate transaction key
            byte[] transactionKey = ConfidentialUtil.keyGenerate(secretKey,
                    request.getTransaction().getHash().getValue());

            ConfidentialRequest confidentialRequest = new ConfidentialRequest(request, publicKeys, transactionKey);

            callContractResult = sdk.getConfidentialService().confidentialRequest(confidentialRequest);
        } else {
            callContractResult = sdk.getContractService().callContract(request);
        }

        if (!callContractResult.isSuccess() || callContractResult.getTransactionReceipt().getResult() != 0) {
            System.out.println("callContract Error :"  + getErrorMsg((int) callContractResult.getTransactionReceipt().getResult()));
        } else {
            byte[] output = callContractResult.getTransactionReceipt().getOutput();
            if (output == null) {
                exit("call QueryCredit function", "output failed");
            } else {
                // decode return values
                EVMOutput contractReturnValues = new EVMOutput(ByteUtils.toHexString(output));
                System.out.println("call callContractSumReceiveMoney success, response value of SumReceiveMoney is: " + contractReturnValues.getUint());
                System.out.println("txHash: " + callContractResult.getTxHash());
            }
        }
    }
*/

    //升级合约
    private static void updateContractDemo() {
        EVMParameter contractParameters = new EVMParameter();
        UpdateContractRequest request = new UpdateContractRequest(Utils.getIdentityByName(callContractId), contractUpdateCode, VMTypeEnum.EVM);
        UpdateContractResponse updateContractResponse = sdk.getContractService().updateContract(request);

        // deploy contract
        if (!updateContractResponse.isSuccess()
                || updateContractResponse.getTransactionReceipt().getResult() != 0) {
            exit("upgrade Contract",
                    getErrorMsg((int) updateContractResponse.getTransactionReceipt().getResult()));
        } else {
            System.out.println("upgrade contract success.contact id is " + callContractId);
        }
    }

    //调用升级新方法
    private static void callContractGetParamsTest() {
        EVMParameter parameters = new EVMParameter("GetParamsTest()");

        // build CallContractRequest
        CallContractRequest request = new CallContractRequest(userIdentity,
                Utils.getIdentityByName(callContractId), parameters, BigInteger.ZERO, VMTypeEnum.EVM);

        TransactionReceiptResponse callContractResult;
        if (isTeeChain) {
            signRequest(request);
            // generate transaction key
            byte[] transactionKey = ConfidentialUtil.keyGenerate(secretKey,
                    request.getTransaction().getHash().getValue());

            ConfidentialRequest confidentialRequest = new ConfidentialRequest(request, publicKeys, transactionKey);

            callContractResult = sdk.getConfidentialService().confidentialRequest(confidentialRequest);
        } else {
            callContractResult = sdk.getContractService().callContract(request);
        }

        if (!callContractResult.isSuccess() || callContractResult.getTransactionReceipt().getResult() != 0) {
            System.out.println("callContract Error :"  + getErrorMsg((int) callContractResult.getTransactionReceipt().getResult()));
        } else {
            byte[] output = callContractResult.getTransactionReceipt().getOutput();
            if (output == null) {
                exit("call callContractGetParamsTest function", "output failed");
            } else {
                // decode return values
                EVMOutput contractReturnValues = new EVMOutput(ByteUtils.toHexString(output));

                BigInteger bigInteger = contractReturnValues.getUint(); // 100
                String string1 = contractReturnValues.getString();       // "abc"
                String string2 = contractReturnValues.getString();
                boolean isOK = contractReturnValues.getBoolean();
                String id = contractReturnValues.getIdentity().toString();
                System.out.println("call callContractGetParamsTest function OK");
            }
        }
    }


    //冻结合约
    private static void freezeContractTest() {
        FreezeContractRequest request = new FreezeContractRequest(userIdentity, Utils.getIdentityByName(callContractId));
        FreezeContractResponse freezeContractResponse = sdk.getContractService().freezeContract(request);
        if (!freezeContractResponse.isSuccess()
                || freezeContractResponse.getTransactionReceipt().getResult() != 0) {
            exit("freeze Contract",
                    getErrorMsg((int) freezeContractResponse.getTransactionReceipt().getResult()));
        } else {
            System.out.println("freeze contract success.contact id is " + callContractId);
        }
    }

    //解冻合约
    private static void unFreezeContractTest() {
        UnFreezeContractRequest request = new UnFreezeContractRequest(userIdentity, Utils.getIdentityByName(callContractId));
        UnFreezeContractResponse unFreezeContractResponse = sdk.getContractService().unFreezeContract(request);
        if (!unFreezeContractResponse.isSuccess()
                || unFreezeContractResponse.getTransactionReceipt().getResult() != 0) {
            exit("freeze Contract",
                    getErrorMsg((int) unFreezeContractResponse.getTransactionReceipt().getResult()));
        } else {
            System.out.println("unFreeze contract success.contact id is " + callContractId);
        }
    }

    //订阅合约
    private static void listenContractTest(){
        //event handler
        IEventCallback handler = new IEventCallback() {
            @Override
            public void onEvent(Message message) {
                PushContractEvent eventContractMessage = (PushContractEvent) message;
                // code
            }
        };

        BigInteger eventId = sdk.getEventService().listenContract(userIdentity, handler, EventModelType.PUSH);
        if (eventId.longValue() == 0) {
            System.out.println("listenContract failed");
        }
    }

    /*
    public static void main(String[] args) throws Exception {
        //step 1:init mychain env.
        initMychainEnv();
        //step 2: init sdk client
        initSdk();

        //step 3 : deploy a contract using useridentity.
        //deployContract();

        //step 4 callContract.
        //String testAccount = "jraccount_1622277417038";
        //callContractIssueCredit(account,800);
        //callContractQueryCredit(testAccount);
        //callContractTransferCredit(testAccount,100);
        //callContractQueryCredit(account);

        //callContractQueryReceiveMoney(1, 1);
        //callContractReceiveMoney(1, 1000, 1, "2021-07-17 10:04:21");
        //callContractSumReceiveMoney(1);
        //callContractSpendMoney(10, 1, 1, "2021-07-17 10:04:21");
        callContractUploadProvidence("ads98fs", 1, 1, "2021-07-17 10:04:21");


        System.in.read();
        //step 5 : sdk shut down
        sdk.shutDown();
    }*/

}
