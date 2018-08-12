# puc-javaee-projeto-final


### 1. Executar Docker MongoDB e RabbitMQ
#### 1.1. MongoDB:
```
docker run -d -p 27017:27017 -v ~/docker_data/mongodb:/data/db mong
```

#### 1.2. RabbitMQ:
```
docker run -d --hostname rabbitmq --name rabbitmq-management -p 15672:15672 -p 5671:5671 -p 5672:5672 rabbitmq:management
```

### 2. Configurar e-mail de Origem
```
Alterar constantes fromEmail e password com os dados do e-mail de Origem no arquivo /src/main/java/com/javaee/allan/mercadoacoes/emailsender/EmailSender.java
```

### 3. Executar o projeto no Eclipse

### 4. Endereços Docker:
* MongoDB: mongodb://localhost:27017
* RabbitMQ: http://localhost:15672/#/queues

### 5. API's
#### 5.1. Empresas
##### 5.1.1. Criar empresa
```
POST http://localhost:8080/api/v1/companies
```

```
{
	"name": "COMPANY",
	"email": "rhribeiro@gmail.com"
}
```

Resposta:
```
{
    "id": "f51f2917-ca92-48a2-950c-aea4ce276891",
    "name": "COMPANY",
    "email": "rhribeiro@gmail.com",
    "timestamp": "2018-08-07 00:13:31.505"
}
```

##### 5.1.2. Listar empresas
```
GET http://localhost:8080/api/v1/companies
```

Resposta:
```
[
    
    {
        "id": "f51f2917-ca92-48a2-950c-aea4ce276891",
        "name": "COMPANY",
        "email": "rhribeiro@gmail.com",
        "timestamp": "2018-08-07 00:13:31.505"
    }
]
```

#### 5.2. Investidores
##### 5.2.1. Criar investidor
```
POST http://localhost:8080/api/v1/investors/
```
```
{
        "name": "Rodrigo Heleno Ribeiro",
        "email": "rhribeiro@gmail.com"
}
```

Resposta:
```
{
    "id": "160c8e0f-2cf8-4918-a2b7-60021947f5da",
    "name": "Rodrigo Heleno Ribeiro",
    "email": "rhribeiro@gmail.com",
    "timestamp": "2018-08-07 00:17:42.87"
}
```

##### 5.2.2. Listar investidores
```
GET http://localhost:8080/api/v1/investors/
```

Resposta:
```
[
    {
        "id": "160c8e0f-2cf8-4918-a2b7-60021947f5da",
        "name": "Rodrigo Heleno Ribeiro",
        "email": "rhribeiro@gmail.com",
        "timestamp": "2018-08-07 00:17:42.87"
    },
    {
        "id": "5d29ccbd-1a57-47c8-938b-65bcb80927a7",
        "name": "Rodrigo Heleno Ribeiro (hotmail)",
        "email": "allancristian@hotmail.com",
        "timestamp": "2018-08-07 00:18:04.131"
    }
]
```

#### 5.3. Emitir Ações (Empresa)
##### 5.3.1. Emitir ação para uma empresa cadastrada
```
POST http://localhost:8080/api/v1/stocks/emit/{companyId}
```
```
{
    "name": "AB1",
    "quantity": 10000,
    "initialPrice": 20
}
```

Resposta:
```
{
    "id": "6b545954-e0b3-4138-af9f-1a6dcb132502",
    "name": "AB1",
    "quantity": 10000,
    "quantityCompany": 10000,
    "initialPrice": 20,
    "timestamp": "2018-08-07 00:22:44.006"
}
```

##### 5.3.2. Exibir ações de uma empresa
```
GET http://localhost:8080/api/v1/stocks/emit/{companyId}
```

Resposta:
```
[
    {
        "id": "6b545954-e0b3-4138-af9f-1a6dcb132502",
        "name": "AB1",
        "quantity": 10000,
        "quantityCompany": 10000,
        "initialPrice": 20,
        "timestamp": "2018-08-11 22:22:44.006"
    },
    {
        "id": "1b325db6-f68b-4b12-8845-586639facca3",
        "name": "AB2",
        "quantity": 300000,
        "quantityCompany": 300000,
        "initialPrice": 15,
        "timestamp": "2018-08-11 22:23:31.838"
    }
]
```


#### 5.4. Comprar ações (investidor)
##### 5.4.1. Comprar ações
```
POST http://localhost:8080/api/v1/stocks/buy/
POST http://localhost:8080/api/v1/stocks/buy/{stockId}/
POST http://localhost:8080/api/v1/stocks/buy/{stockId}/{investorId}
```
```
{
    "stockId": {stockId},
    "investorId": {investorId},
    "quantity": 5000,
    "price": 20
}
```
* Obs: Informar os parâmetros stockId e investorId na URL ou no Corpo da requisição

Resposta:
```
{
    "id": "23dbdcef-2f07-4f23-bae8-f817c5f4b1ee"
}
```

##### 5.4.2. Exibir demandas (compras recebidas) de ações
```
GET http://localhost:8080/api/v1/stocks/buy/
```

Resposta:
```
[
    {
        "id": "b5ecf568-a197-46a1-878a-7cf158c69362",
        "quantity": 2000,
        "quantityBought": 0,
        "price": 19,
        "timestamp": "2018-08-11 22:38:33.477",
        "investor": {
            "id": "5d29ccbd-1a57-47c8-938b-65bcb80927a7",
            "name": "Rodrigo Heleno Ribeiro (hotmail)",
            "email": "allancristian@hotmail.com",
            "timestamp": "2018-08-07 00:18:04.131"
        },
        "stock": {
            "id": "6b545954-e0b3-4138-af9f-1a6dcb132502",
            "name": "AB1",
            "quantity": 10000,
            "quantityCompany": 0,
            "initialPrice": 20,
            "timestamp": "2018-08-11 22:22:44.006"
        }
    },
    {
        "id": "c7407a5b-51d5-4b46-bf3d-3163ab2baf41",
        "quantity": 7000,
        "quantityBought": 5000,
        "price": 20,
        "timestamp": "2018-08-11 22:33:19.088",
        "investor": {
            "id": "160c8e0f-2cf8-4918-a2b7-60021947f5da",
            "name": "Rodrigo Heleno Ribeiro",
            "email": "rhribeiro@gmail.com",
            "timestamp": "2018-08-07 00:17:42.87"
        },
        "stock": {
            "id": "6b545954-e0b3-4138-af9f-1a6dcb132502",
            "name": "AB1",
            "quantity": 10000,
            "quantityCompany": 0,
            "initialPrice": 20,
            "timestamp": "2018-08-11 22:22:44.006"
        }
    },
    {
        "id": "669a113f-c5e6-4ec0-9ddb-9c04c691a7f2",
        "quantity": 4000,
        "quantityBought": 0,
        "price": 17.5,
        "timestamp": "2018-08-11 22:38:51.479",
        "investor": {
            "id": "5d29ccbd-1a57-47c8-938b-65bcb80927a7",
            "name": "Rodrigo Heleno Ribeiro (hotmail)",
            "email": "allancristian@hotmail.com",
            "timestamp": "2018-08-11 22:18:04.131"
        },
        
        "stock": {
            "id": "6b545954-e0b3-4138-af9f-1a6dcb132502",
            "name": "AB1",
            "quantity": 10000,
            "quantityCompany": 0,
            "initialPrice": 20,
            "timestamp": "2018-08-11 22:22:44.006"
        }
    }
]
```

#### 5.5. Vender ações (investidor)
##### 5.5.1. Vender ações
```
POST http://localhost:8080/api/v1/stocks/sell/
POST http://localhost:8080/api/v1/stocks/sell/{stockId}/
POST http://localhost:8080/api/v1/stocks/sell/{stockId}/{investorId}
```
```
{
    "stockId": {stockId},
    "investorId": {investorId},
    "quantity": 50,
    "price": 19
}
```
* Obs: Informar os parâmetros stockId e investorId na URL ou no Corpo da requisição

##### 5.5.2. Exibir ofertas (vendas) de ações
```
GET http://localhost:8080/api/v1/stocks/sell/
```

```
[
    {
        "id": "80de4047-a049-4047-a665-322327b5311a",
        "companyOffer": true,
        "quantity": 50,
        "quantitySold": 0,
        "price": 10,
        "timestamp": "2018-08-11 22:25:52.357",
        "investor": null,
        "stock": {
            "id": "0ea96a40-9dc7-4cec-995b-1a2d13d0fe6f",
            "name": "AE1",
            "quantity": 10000,
            "quantityCompany": 10000,
            "initialPrice": 30,
            "timestamp": "2018-08-11 22:25:52.33"
        }
    },
    
    {
        "id": "bd0d3f36-65f3-4833-90c8-b7f684d18575",
        "companyOffer": true,
        "quantity": 50,
        "quantitySold": 0,
        "price": 15,
        "timestamp": "2018-08-11 22:23:31.864",
        "investor": null,
        "stock": {
            "id": "1b325db6-f68b-4b12-8845-586639facca3",
            "name": "Rodrigo",
            "quantity": 300000,
            "quantityCompany": 300000,
            "initialPrice": 15,
            "timestamp": "2018-08-11 22:23:31.838"
        }
    }
]
```

#### 5.6. Texto de e-mails (exemplos)
##### 5.6.1. Venda realizada com sucesso
```
Assunto:
Notificação de venda ação 6b545954-e0b3-4138-af9f-1a6dcb132502

Corpo do e-mail:
50 ações foram vendidas com sucesso no valor de 15.0 (preço unitário).
```

##### 5.6.2. Compra realizada com sucesso
```
Assunto:
Notificação de compra ação 6b545954-e0b3-4138-af9f-1a6dcb132502

Corpo do e-mail:
50 ações foram compradas com sucesso no valor de 15.0 (preço unitário).
```
