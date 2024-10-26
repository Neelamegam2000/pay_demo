<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Payment</title>
</head>
<body>
 <%String val=request.getParameter("phone_number"); %>
 <form action ="/googlepay/phonenumberpay.jsp" method="POST" id="form">
    <input type="hidden" name="phone_number" id="phone_number" value=<%=val %>><br><br>
    <input type="submit" name=""  value=phonenumberpay><br><br>
</form>
<form action ="/googlepay/upipay.jsp" method="POST" id="form">
    <input type="hidden" name="phone_number" id="phone_number" value=<%=val %>><br><br>
    <input type="submit" name="" value=upipay><br><br>
</form>
<form action ="/googlepay/accountpay.jsp" method="POST" id="form">
    <input type="hidden" name="phone_number" id="phone_number" value=<%=val %>><br><br>
    <input type="submit" name="" value=accountpay><br><br>
</form>
</body>
</html>