#curl --header "Content-Type: application/json" --request POST --data  '{"name": "Ivan","lastName": "Petrov","password" : "2342345"}' http://localhost:8080/register
#curl --header "Content-Type: application/json" --request POST --data  '{"name": "Ivan","password" : "2342345"}' http://localhost:8080/login
#curl --header "Content-Type: application/json" --request POST --data  '{"userIdRequest": "2"}' http://localhost:8080/account/create
#curl --header "Content-Type: application/json" --request POST --data  '{"idUser": "2"}' http://localhost:8080/card
#curl --header "Content-Type: application/json" --request POST --data  '{"accountId": "3","balance":"1500"}' http://localhost:8080/account/add
#curl --header "Content-Type: application/json" --request POST --data  '{"accountId": "2","balance":"300"}' http://localhost:8080/account/withdraw
curl --header "Content-Type: application/json" --request POST --data  '{"accountIdUser1": "2","accountNumberUser2":"71381552793559178688","sumMoney":"1100"}' http://localhost:8080/transaction

