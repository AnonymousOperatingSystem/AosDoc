# AosServerByJava api interface

## 1.create account example
curl http://127.0.0.1:8080/xxx/create_free_account -d '{"account_name":"aaaabbbbcccc", "active_key":"", "owner_key":""}' -X POST -H "Content-Type: application/json"
{"code":"0","en_msg":"成功","message":"成功","extraMsg":"success","data":null}


## 2.transfer aos example,transfer demo
curl -X GET http://127.0.0.1:8080/xxx/testTransfer


### environment
#### 1.this project using spring boot and jdk14,you can open this project by 'IntelijIdea 2020.3'
#### 2.transfer and createAccount example at EosController class,but you need fill the followe privatekey and creator account
```
public class EosController {


    public static String aosBaseUrl = "http://127.0.0.1:8888/";//you node addaddress
    public static String aosChainBaseUrl = aosBaseUrl+"/v1/chain/";
    public static final String acountCreatorName = "";//accout for creating new account
    public static String acountCreatorNamePrivateKey = "";//account private key
```
