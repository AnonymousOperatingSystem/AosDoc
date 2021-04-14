#    AOS代码修改自EOS,除内部的零知识证明等匿名资产算法外,外部接口的基本使用方法和细节和EOS几乎完全一致。
# 下面我将罗列AOS及EOS相关的链接和文档


## 一、中心化交易所充值AOS的基本方法
### eg. 前提假设：下面以用户aaaaaaaaaaaa需要充值'100.0000 AOS'到中心化交易所为实例
#### 1. 首先，交易所需要提供一个aos账号给充值功能，假设账号名为'centralizede'
#### 2. 交易所需要为每个用户提供一个字符串(数字或小写字母)，用于唯一标记一个用户，假设为用户A提供的字符串为'ab23'

### 现在用户开始充值
### 第一步. 用户转账: 用户aaaaaaaaaaaa转账给'centralizede' '100.0000 AOS',并附带memo为'ab23'

### 第二步. 交易所定时获取最新交易记录: 交易所通过[get_actions](#get_actions)接口，将会获取到centralizede的最新交易记录，通过交易记录中的额度以及memo,来判断是谁转帐过来了（参照下面get_actions接口），在后面的[get_actions](#get_actions)将会获取到如下json片段
```
"data": {
    "from": "aaaaaaaaaaaa",
    "to": "centralizede",
    "quantity": "100.0000 AOS",
    "memo": "ab23"
},
```

### 第三步. 确定不可逆: 但还需等待该交易所在区块不可逆后（大约2.5分钟，再次查看这条交易，如果能通过[get_transaction](#get_transaction)接口（下面提供了实例）查询到这条记录trx_id（如下面的[20a5741360b6abce11e1c2e940c3b1afe4ec1d97be2900295b8ea678027191aa](#20a5741360b6abce11e1c2e940c3b1afe4ec1d97be2900295b8ea678027191aa)），且确定该交易所在块（get_transaction接口返回的block_num）已经小于[get_info](#get_info)（下面古同了实例）返回的最新的last_irreversible_block，则可确定此交易不可逆）
### 第四步. 交易不可逆后，则完成充值


#### <span id="get_actions">get_actions获取交易记录接口</span>
curl -X POST --url http://127.0.0.1:8888/v1/history/get_actions -d '{
  "pos": -1,
  "offset": -20,
  "account_name": "centralizede"
}'

返回如下
```
{
    "actions": [
        {
            "global_action_seq": 67424015,
            "account_action_seq": 10602,
            "block_num": 61109785,
            "block_time": "2021-04-11T10:14:16.500",
            "action_trace": {
                "action_ordinal": 3,
                "creator_action_ordinal": 1,
                "closest_unnotified_ancestor_action_ordinal": 1,
                "receipt": {
                    "receiver": "centralizede",
                    "act_digest": "32e2d92d078841ba51637e75bab23039b2e1aa78ab70e4f7a543ac7f00c02aaa",
                    "global_sequence": 67424015,
                    "recv_sequence": 2694308,
                    "auth_sequence": [
                        [
                            "aaaaaaaaaaaa",
                            8710
                        ]
                    ],
                    "code_sequence": 1,
                    "abi_sequence": 1
                },
                "receiver": "centralizede",
                "act": {
                    "account": "aosio.token",
                    "name": "transfer",
                    "authorization": [
                        {
                            "actor": "aaaaaaaaaaaa",
                            "permission": "active"
                        }
                    ],
                    "data": {
                        "from": "aaaaaaaaaaaa",
                        "to": "centralizede",
                        "quantity": "100.0000 AOS",
                        "memo": "ab23"
                    },
                    "hex_data": "10c254d0a93855434aa7c306d15cfd4520a107000000000004414f53000000000c3737323932317c35307c3635"
                },
                "context_free": false,
                "elapsed": 178,
                "console": "you just ",
                <span id="20a5741360b6abce11e1c2e940c3b1afe4ec1d97be2900295b8ea678027191aa">"trx_id": "20a5741360b6abce11e1c2e940c3b1afe4ec1d97be2900295b8ea678027191aa"</span>,
                "block_num": 61109785,
                "block_time": "2021-04-11T10:14:16.500",
                "producer_block_id": "03a476194417fe238a47a44751eb64a86839384c9450dd2802d315c1b74f7ad0",
                "account_ram_deltas": [
                    {
                        "account": "centralizede",
                        "delta": 68
                    }
                ],
                "except": null,
                "error_code": null
            }
        }
    ],
    "last_irreversible_block": 61256343
}
```

#### <span id="get_transaction">get_transaction获取某个交易txid接口</span>

curl -X POST --url http://127.0.0.1:8888/v1/history/get_transaction -d '{
  "id": "20a5741360b6abce11e1c2e940c3b1afe4ec1d97be2900295b8ea678027191aa"
}'

返回如下
```
{
    "id": "20a5741360b6abce11e1c2e940c3b1afe4ec1d97be2900295b8ea678027191aa",
    "trx": {
        "receipt": {
            "status": "executed",
            "cpu_usage_us": 682,
            "net_usage_words": 18,
            "trx": [
                1,
                {
                    "signatures": [
                        "SIG_K1_JxZpTKuLN5UfcbihHTG5FqZLKsrYNUbdeJELmwcFkKEE9u3XzHKyvLD6ihqPBv7JwMBYWua9WVyx6cCeTvyhPjDBBnBPJz"
                    ],
                    "compression": "none",
                    "packed_context_free_data": "",
                    "packed_trx": "33cc7260cb74fca9e3a8000000000100a6823403ea3035000000572d3ccdcd0110c254d01938554300000000a8ed32322d10c254d0a93855434aa7c306d15cfd4520a107000000000004414f53000000000c3737323932317c35307c363500"
                }
            ]
        },
        "trx": {
            "expiration": "2021-04-11T10:15:15",
            "ref_block_num": 29899,
            "ref_block_prefix": 2833492476,
            "max_net_usage_words": 0,
            "max_cpu_usage_ms": 0,
            "delay_sec": 0,
            "context_free_actions": [],
            "actions": [
                {
                    "account": "aosio.token",
                    "name": "transfer",
                    "authorization": [
                        {
                            "actor": "aaaaaaaaaaaa",
                            "permission": "active"
                        }
                    ],
                    "data": {
                        "from": "aaaaaaaaaaaa",
                        "to": "centralizede",
                        "quantity": "100.0000 AOS",
                        "memo": "ab23|50|65"
                    },
                    "hex_data": "10c254d0a93855434aa7c306d15cfd4520a107000000000004414f53000000000c3737323932317c35307c3635"
                }
            ],
            "transaction_extensions": [],
            "signatures": [
                "SIG_K1_JxZpTKuLN5UfcbihHTG5FqZLKsrYNUbdeJELmwcFkKEE9u3XzHKyvLD6ihqPBv7JwMBYWua9WVyx6cCeTvyhPjDBBnBPJz"
            ],
            "context_free_data": []
        }
    },
    "block_time": "2021-04-11T10:14:16.500",
    "block_num": 61109785,
    "last_irreversible_block": 61586307,
    "traces": [
        {
            "action_ordinal": 1,
            "creator_action_ordinal": 0,
            "closest_unnotified_ancestor_action_ordinal": 0,
            "receipt": {
                "receiver": "aosio.token",
                "act_digest": "32e2d92d078841ba51637e75bab23039b2e1aa78ab70e4f7a543ac7f00c02aaa",
                "global_sequence": 67424013,
                "recv_sequence": 1414388,
                "auth_sequence": [
                    [
                        "aaaaaaaaaaaa",
                        8708
                    ]
                ],
                "code_sequence": 1,
                "abi_sequence": 1
            },
            "receiver": "aosio.token",
            "act": {
                "account": "aosio.token",
                "name": "transfer",
                "authorization": [
                    {
                        "actor": "aaaaaaaaaaaa",
                        "permission": "active"
                    }
                ],
                "data": {
                    "from": "aaaaaaaaaaaa",
                    "to": "centralizede",
                    "quantity": "100.0000 AOS",
                    "memo": "ab23|50|65"
                },
                "hex_data": "10c254d0a93855434aa7c306d15cfd4520a107000000000004414f53000000000c3737323932317c35307c3635"
            },
            "context_free": false,
            "elapsed": 69,
            "console": "",
            "trx_id": "20a5741360b6abce11e1c2e940c3b1afe4ec1d97be2900295b8ea678027191aa",
            "block_num": 61109785,
            "block_time": "2021-04-11T10:14:16.500",
            "producer_block_id": "03a476194417fe238a47a44751eb64a86839384c9450dd2802d315c1b74f7ad0",
            "account_ram_deltas": [],
            "except": null,
            "error_code": null
        },
        {
            "action_ordinal": 2,
            "creator_action_ordinal": 1,
            "closest_unnotified_ancestor_action_ordinal": 1,
            "receipt": {
                "receiver": "aaaaaaaaaaaa",
                "act_digest": "32e2d92d078841ba51637e75bab23039b2e1aa78ab70e4f7a543ac7f00c02aaa",
                "global_sequence": 67424014,
                "recv_sequence": 4794,
                "auth_sequence": [
                    [
                        "aaaaaaaaaaaa",
                        8709
                    ]
                ],
                "code_sequence": 1,
                "abi_sequence": 1
            },
            "receiver": "aaaaaaaaaaaa",
            "act": {
                "account": "aosio.token",
                "name": "transfer",
                "authorization": [
                    {
                        "actor": "aaaaaaaaaaaa",
                        "permission": "active"
                    }
                ],
                "data": {
                    "from": "aaaaaaaaaaaa",
                    "to": "centralizede",
                    "quantity": "100.0000 AOS",
                    "memo": "ab23"
                },
                "hex_data": "10c254d0a93855434aa7c306d15cfd4520a107000000000004414f53000000000c3737323932317c35307c3635"
            },
            "context_free": false,
            "elapsed": 2,
            "console": "",
            "trx_id": "20a5741360b6abce11e1c2e940c3b1afe4ec1d97be2900295b8ea678027191aa",
            "block_num": 61109785,
            "block_time": "2021-04-11T10:14:16.500",
            "producer_block_id": "03a476194417fe238a47a44751eb64a86839384c9450dd2802d315c1b74f7ad0",
            "account_ram_deltas": [],
            "except": null,
            "error_code": null
        },
        {
            "action_ordinal": 3,
            "creator_action_ordinal": 1,
            "closest_unnotified_ancestor_action_ordinal": 1,
            "receipt": {
                "receiver": "centralizede",
                "act_digest": "32e2d92d078841ba51637e75bab23039b2e1aa78ab70e4f7a543ac7f00c02aaa",
                "global_sequence": 67424015,
                "recv_sequence": 2694308,
                "auth_sequence": [
                    [
                        "aaaaaaaaaaaa",
                        8710
                    ]
                ],
                "code_sequence": 1,
                "abi_sequence": 1
            },
            "receiver": "centralizede",
            "act": {
                "account": "aosio.token",
                "name": "transfer",
                "authorization": [
                    {
                        "actor": "aaaaaaaaaaaa",
                        "permission": "active"
                    }
                ],
                "data": {
                    "from": "aaaaaaaaaaaa",
                    "to": "centralizede",
                    "quantity": "100.0000 AOS",
                    "memo": "ab23|50|65"
                },
                "hex_data": "10c254d0a93855434aa7c306d15cfd4520a107000000000004414f53000000000c3737323932317c35307c3635"
            },
            "context_free": false,
            "elapsed": 178,
            "console": "you just100.0000 AOS",
            "trx_id": "20a5741360b6abce11e1c2e940c3b1afe4ec1d97be2900295b8ea678027191aa",
            "block_num": 61109785,
            "block_time": "2021-04-11T10:14:16.500",
            "producer_block_id": "03a476194417fe238a47a44751eb64a86839384c9450dd2802d315c1b74f7ad0",
            "account_ram_deltas": [
                {
                    "account": "centralizede",
                    "delta": 68
                }
            ]
        }
    ]
}
```


#### <span id="get_info">get_info获取节点当前最新信息接口</span>

curl -X POST --url http://127.0.0.1:8888/v1/chain/get_info

返回如下

```
{
    "server_version": "95da4496",
    "chain_id": "907345e081e731497946845186a03a50030c6c9ee14bacfcb1922feae873f31b",
    "head_block_num": 61586506,
    "last_irreversible_block_num": 61586175,
    "last_irreversible_block_id": "03abbaffa6488fa3a3e839ffc31a97f590b92c0f661c9259b029d3af62876e9e",
    "head_block_id": "03abbc4abc5668e247e4d6f518223dbf6bade3d1a37ebe254482425c8c464ef5",
    "head_block_time": "2021-04-14T07:45:33.000",
    "head_block_producer": "aosnairobi",
    "virtual_block_cpu_limit": 3800000000,
    "virtual_block_net_limit": 1048576000,
    "block_cpu_limit": 3799900,
    "block_net_limit": 1048576,
    "server_version_string": "push-dirty",
    "fork_db_head_block_num": 61586506,
    "fork_db_head_block_id": "03abbc4abc5668e247e4d6f518223dbf6bade3d1a37ebe254482425c8c464ef5"
}
```



## 二、EOS的核心源码及核心合约（深入研究者可以看看）
### 1.EOS souce code
https://github.com/EOSIO/eos
### 2.EOS Contract compile environment
https://github.com/EOSIO/eosio.cdt
### 3.EOS Core contract
https://github.com/EOSIO/eosio.contracts


## 三、EOS的http接口文档（内部采用了curl命令方式做为请求例子）
http://cw.hubwiz.com/card/c/eos-rpc-api/

## 四、命令行工具cleos（claos同样通用）文档
http://cw.hubwiz.com/card/c/cleos/1/1/1/


## 五、AOS使用Java(Sprint Boot)进行创建账号和转账的简单例子
### 1.项目使用 java14 和 sprint boot(包括 transfer 及 create account 例子)，你可以使用 Intelij Idea 2020.3 打开运行此项目
JavaServerDemo(和此文件同github目录下)


### 2.EOS的android开发实例（AOS同样通用）(包括transfer,create account,get balance,gennerate privateKey等用例)
https://github.com/swapnibble/EosCommander