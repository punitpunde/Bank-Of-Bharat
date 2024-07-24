
# Bank of Bharat ( Banking Application)

#### 👌BOB is backend application developing using Java, Spring Boot and Micorservices.

#### 👌It communicate with Bharat Payments for providing UPI fetures and secured transaction using Rest APIs.

#### 👌Working on FrontEnd using React.


## Functionalities
##### 👌 Featuring user authentication , account  and transaction management.
##### 👌 Integrated with Bharat Payments( UPI Payment application ) for Payment gateway.


## API Reference

#### Create account

```http
  POST finance/bharat/create-account
```

#### To update account details

```http
  PUT finance/bharat/update-account-details
```
####  delete account

```http
  DELETE finance/bharat/delete-account
```
####  get account details


```http
  GET finance/bharat/get-account-details/{accountNumber}/{IFSCCode}/{password}
```
####  do balance enquiry


```http
  GET finance/bharat/balance-enquiry
```

####  credit money


```http
  GET finance/bharat/credit-money
```


####  debit money


```http
  GET finance/bharat/debit-money
```
#### add money from UPI transaction

```http
  GET finance/bharat/add-money-to-account-from-upi
```

####  pay money using UPI

```http
  GET finance/bharat/pay-money-from-upi
```

####  update money while doing UPI transaction

```http
  GET finance/bharat/update/money
```


