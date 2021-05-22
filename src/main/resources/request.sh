#curl --header "Content-Type: application/json" --request POST --data  '{"name": "Dimas","lastName": "Ivanov","password" : "20304050"}' http://localhost:8080/register
#curl --header "Content-Type: application/json" --request POST --data  '{"name": "Colaj","password" : "3123"}' http://localhost:8080/login
#curl --header "Content-Type: application/json" --request POST --data  '{"userIdRequest": "41"}' http://localhost:8080/account/create
curl --header "Content-Type: application/json" --request POST --data  '{"idUser": "2"}' http://localhost:8080/card
#curl --header "Content-Type: application/json" --request POST --data  '{"accountId": "3","balance":"1500"}' http://localhost:8080/account/add
#curl --header "Content-Type: application/json" --request POST --data  '{"accountId": "2","balance":"1000"}' http://localhost:8080/account/withdraw
#curl --header "Content-Type: application/json" --request POST --data  '{"accountIdUser1": "3","accountNumberUser2":"37292599907170658484","sumMoney":"500"}' http://localhost:8080/transaction


