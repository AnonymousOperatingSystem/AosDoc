///create account example
curl http://127.0.0.1:8080/xxx/create_free_account -d '{"account_name":"aaaabbbbcccc", "active_key":"", "owner_key":""}' -X POST -H "Content-Type: application/json"
{"code":"0","en_msg":"成功","message":"成功","extraMsg":"success","data":null}


//transfer aos example
//transfer demo
curl -X GET http://127.0.0.1:8080/xxx/testTransfer
