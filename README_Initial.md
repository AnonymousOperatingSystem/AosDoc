# Initial

## One. AOS base info, you can know AOS from EOS,because they use almost same technology
### 1.EOS souce code
https://github.com/EOSIO/eos
### 2.EOS Contract compile environment
https://github.com/EOSIO/eosio.cdt
### 3.EOS Core contract
https://github.com/EOSIO/eosio.contracts


## Two. EOS http rpc api docment
http://cw.hubwiz.com/card/c/eos-rpc-api/

## Three. claos is same with cleos,the cleos command document
http://cw.hubwiz.com/card/c/cleos/1/1/1/


## Four. AOS develop by java
### 1.a project with java14 and sprint boot(includes transfer and create account example)you can open by Intelij Idea 2020.3
https://github.com:aosopendoc/AosServerByJava.git


### 2.a project running on Android example(includes almost all use case:transfer,create account,get balance,gennerate privateKey)
https://github.com/swapnibble/EosCommander



## Five. How to charge at ‘Centralized exchange’
eg. user A want to charge '100.0000 AOS' to account 'centralizede' proviede by ‘Centralized exchange’
### 1. gen a unique id(for exmaple 1234) for user A, must unique for every user
### 2. user A transfer '100.0000 AOS' to account 'centralizede', must with memo '1234'
### 3. ‘Centralized exchange’ get_actions (use timer) to detech weather has new transfer（distinguish the transfer is from who by memo）,and deal this transfer,and then charge to the ‘Centralized exchange’ for this user A
```
curl -X POST --url http://127.0.0.1:8888/v1/history/get_actions -d '{
  "pos": -1,
  "offset": -20,
  "account_name": "centralizede"
}'

you will get result as follows(use chenkaiken11 as example):

{"actions":[{"global_action_seq":67424015,"account_action_seq":10602,"block_num":61109785,"block_time":"2021-04-11T10:14:16.500","action_trace":{"action_ordinal":3,"creator_action_ordinal":1,"closest_unnotified_ancestor_action_ordinal":1,"receipt":{"receiver":"cryptocasino","act_digest":"32e2d92d078841ba51637e75bab23039b2e1aa78ab70e4f7a543ac7f00c02af1","global_sequence":67424015,"recv_sequence":2694308,"auth_sequence":[["chenkaiken11",8710]],"code_sequence":1,"abi_sequence":1},"receiver":"cryptocasino","act":{"account":"aosio.token","name":"transfer","authorization":[{"actor":"chenkaiken11","permission":"active"}],"data":{"from":"chenkaiken11","to":"cryptocasino","quantity":"50.0000 AOS","memo":"772921|50|65"},"hex_data":"10c254d01938554340a7c306d15cfd4520a107000000000004414f53000000000c3737323932317c35307c3635"},"context_free":false,"elapsed":178,"console":"you just call ontransfer: chenkaiken11  --- cryptocasino  50.0000 AOS---772921|50|65---","trx_id":"20a5741360b6abce11e1c2e940c3b1afe4ec1d97be2900295b8ea678027191c3","block_num":61109785,"block_time":"2021-04-11T10:14:16.500","producer_block_id":"03a476194417fe238a47a44751eb64a86839384c9450dd2802d315c1b74f7ad0","account_ram_deltas":[{"account":"cryptocasino","delta":68}],"except":null,"error_code":null}}],"last_irreversible_block":61256343}
```