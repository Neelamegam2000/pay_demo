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
<form action ="/googlepay/api/v1/payment" method="POST" id="form">
    upi_pin  :<input type="number"   id="upi_pin"     name="upi_pin"><br><br>
    Amount           :<input type="number"     id="amount" name="amount"><br><br>
    receiver_upi_id        :<input  type="text"    id="receiver"    name="receiver"><br><br>
    <%String val=request.getParameter("phone_number"); %>
    <input type="hidden" name="phone_number" id="phone_number" value=<%=val %>><br><br>
    <input type="hidden" name="obj1" id="obj1" readonly><br><br>
    <input type="submit" name="" onclick = "newpayment()"><br><br>
    <script>
        function newpayment(e){
        	var upi_pin=document.getElementById("upi_pin");
        	var phone_number=document.getElementById("phone_number");
            var amount= document.getElementById("amount"); 
            var receiver= document.getElementById("receiver"); 
            var object = {phone_number:phone_number.value,upi_pin:upi_pin.value,amount:amount.value,receiver:receiver.value};
            var param = JSON.stringify(object);
            var obj1 = document.getElementById("obj1");
            obj1.setAttribute("value",param);
        }
        
    </script>
 </form>   
</body>
</html>