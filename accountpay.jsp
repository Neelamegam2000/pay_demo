<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>upi pay</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js" ></script>
<style>
body{
align:center;
}</style>
</head>
<body style="align:center;">
<form action ="/googlepay/api/v1/accountpay" method="POST" id="form">
    <h1>Payment</h1>
    upi_pin  :<input type="number"   id="upi_pin"     name="upi_pin"><br><br>
    Amount           :<input type="number"     id="amount" name="amount"><br><br>
    Account_number       :<input  type="number"   id="account_no"    name="account_no"><br><br>
    Holder_name:<input type="text" id="holder_name" name="holder_name"><br>
    ifsc_code:<input  type="number"   id="ifsc_code"    name="ifsc_code"><br><br>
    <%String val=request.getParameter("phone_number"); %>
    <input type="hidden" name="phone_number" id="phone_number" value=<%=val %>><br><br>
    <input type="hidden" name="obj1" id="obj1" readonly><br><br>
    <input type="submit" name="" onclick = "newpayment()"><br><br>
    <script>
        function newpayment(e){
        	var phone_number=document.getElementById("phone_number");
            var upi_pin= document.getElementById("upi_pin"); 
            var amount= document.getElementById("amount"); 
            var account_number= document.getElementById("account_no"); 
            var holder_name=document.getElementById("holder_name");
            var ifsc_code=document.getElementById("ifsc_code");
            var object = {phone_number:phone_number.value,upi_pin:upi_pin.value,amount:amount.value,account_number:account_number.value,holder_name:holder_name.value,ifsc_code:ifsc_code.value};
            var param = JSON.stringify(object);
            var obj1 = document.getElementById("obj1");
            obj1.setAttribute("value",param);
        }
        
    </script>
 </form>   
</body>
</html>