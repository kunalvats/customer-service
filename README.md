## ⛄ Get started with Customer service

### Start service Locally - 
PORT - 8081

Swagger - Customer Service
```
http://localhost:8081/swagger-ui/index.html
```

Curl

### Create Customer
customerId is optional
```
curl -X 'POST' \
  'http://localhost:8081/api/customer/' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "customerId": 0,
  "customerName": "string",
  "mobileNumber": "string"
}'
```

### Get customer by customer id
```
curl -X 'GET' \
  'http://localhost:8081/api/customer/1' \
  -H 'accept: */*'
```

### Get all customers
```
curl -X 'GET' \
  'http://localhost:8081/api/customer/' \
  -H 'accept: */*'
```


### Get customer by status
status can be DUE/PAID
```
curl -X 'GET' \
  'http://localhost:8081/api/customer/status/DUE' \
  -H 'accept: */*'
```
