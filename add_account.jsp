<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>add_account</title>
<style>
body{
align:center;
}</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js" ></script>
</head>
<body style="align:center;">
<form action ="/googlepay/api/v1/add_account?" method="POST" id="form">
<h1>Account Details</h1>
    Account_number   :<input type="number"   id="account_number"     name="account_number"><br><br>
    Account_details  :<input type="text"     id="account_details" name="account_details"><br><br>
    Upi_id           :<input  type="text"    id="upi_id"    name="upi_id"><br><br>
    Upi_pin          :<input  type="number"   id="upi_pin"  name="upi_pin"><br><br>
    <input  type="hidden"    id="phone_number" name="phone_number" value="${phone_number}" ><br><br>
    <input type="hidden" name="obj" id="obj" readonly><br><br>
    Active_status    :<select name="active_status" id="active_status">
    <option value="active">active</option>
    <option value="unactive">unactive</option>
    </select>
    Balance          :<input type="number" id="balance" name="balance"><br><br>
    <input type="submit" name="" onclick = "addaccount()">
    <script>
        function addaccount(e){
        	var phone_number=document.getElementById("phone_number");
            var account_number= document.getElementById("account_number"); 
            var account_details= document.getElementById("account_details"); 
            var upi_id= document.getElementById("upi_id"); 
            var upi_pin= document.getElementById("upi_pin"); 
            var active_status=document.getElementById("active_status");
            var balance=document.getElementById("balance");
            var object = {account_number:account_number.value,account_details:account_details.value,upi_id:upi_id.value,upi_pin:upi_pin.value,active_status:active_status.value,balance:balance.value,phone_number:phone_number.value};
     
            var param = JSON.stringify(object);
            
            var obj = document.getElementById("obj");
            obj.setAttribute("value",param);
        }
        
    </script>
 </form>   
</body>
</html>