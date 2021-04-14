# 此项目为一个AOS的Java项目，包含了基本的创建账号和转账功能用例


### 首先需要配置开发环境，并启动该项目，环境需求如下
#### 1.项目使用spring boot 和 jdk14,并使用 'IntelijIdea 2020.3'打开此项目
#### 2.transfer and createAccount 例子在EosController类,你需要填写下面的privatekey 和 creator account
```
public class EosController {


    public static String aosBaseUrl = "http://127.0.0.1:8888/";//你的节点地址
    public static String aosChainBaseUrl = aosBaseUrl+"/v1/chain/";
    public static final String acountCreatorName = "";//创建账号的账号
    public static String acountCreatorNamePrivateKey = "";//账号私钥
```

项目启动后，可以使用curl(类似postman)方式进行简单测试
## 1.创建账号测试用例
```
curl http://127.0.0.1:8080/xxx/create_free_account -d '{"account_name":"aaaabbbbcccc", "active_key":"", "owner_key":""}' -X POST -H "Content-Type: application/json"
{"code":"0","en_msg":"成功","message":"成功","extraMsg":"success","data":null}
```

## 2.转账测试用例
```
curl -X GET http://127.0.0.1:8080/xxx/testTransfer
```
