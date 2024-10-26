<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>balance</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js" ></script>
<style>
body{
align:center;
}</style>
</head>
<body>
 <%String val=request.getParameter("phone_number1"); %> 
 <h3>BALANCE</h3>
<form action ="/googlepay/api/v1/balance" method="POST" id="form">
    upi_pin  :<input type="number"   id="upi_pin"     name="upi_pin"><br><br>
    <input type="hidden" name="phone_number" id="phone_number" value=<%=val %>><br><br>
    <input type="hidden" name="obj1" id="obj1" readonly><br><br>
    <input type="submit" name="" onclick = "balance()"><br><br>
    <script>
        function balance(e){
        	var upi_pin=document.getElementById("upi_pin");
        	var phone_number=document.getElementById("phone_number");
            var object = {phone_number:phone_number.value,upi_pin:upi_pin.value};
            var param = JSON.stringify(object);
            var obj1 = document.getElementById("obj1");
            obj1.setAttribute("value",param);
        }
        
    </script>
 </form>   
</body>
</html>