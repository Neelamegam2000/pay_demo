package googlepay;
import java.io.*;

//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.sql.*;

import org.json.JSONArray;
import org.json.JSONException;
//import org.json.JSONException;
import org.json.JSONObject;
//import org.json.simple.JSONValue;

//import com.google.gson.Gson;


@WebServlet("/api/v1/contacts")

public class contacts extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
  
    
    //GET METHOD
    
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
	}

	//POST METHOD
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
			PrintWriter out=res.getWriter();
			res.setContentType("text/html");
		/*String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator())); 
		out.print(body);*/
		/*StringBuilder sb = new StringBuilder();
	    BufferedReader reader = req.getReader();
	    try {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            sb.append(line).append('\n');
	        }
	    } finally {
	        reader.close();
	    }
	    String phone_number=null;
	    try{
	    	String string = sb.toString();
	    JSONObject json = new JSONObject(string);  
	    phone_number=json.getString("phoneno");
	    }catch(JSONException ex){
	    	out.print(ex);
	    }*/
		out.println("<h1>Recent Contacts</h1>");
		String string=null;
		String phone_number=null;
		string = req.getParameter("obj");
		string=string.toString();
        JSONObject jsonObject1=null;
		try {
			jsonObject1 = new JSONObject(string);
		} catch (JSONException e2) {
			e2.printStackTrace();
		}
	    try {
			phone_number=jsonObject1.getString("phone_number");
			if(phone_number==null){phone_number=(String) req.getAttribute("phone_number");}
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		try{
			//Connection
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/google_pay","root","");
			Statement s=con.createStatement();
			String sql="select made_on,sender_name,g.name as receiver_name,sender_profile,g.image as receiver_profile from(select made_on,g.name as sender_name,g.image as sender_profile,z.receiver_upi_id from(select max(t.made_on) as made_on,t.sender_upi_id,t.receiver_upi_id from(select a.upi_id from google_pay_user as g,accounts as a where g.phone_no='"+phone_number+"' and g.phone_no=a.phone_number) as k,google_pay_user as g,accounts as a,transaction as t where (t.sender_upi_id=k.upi_id or t.receiver_upi_id=k.upi_id) and t.receiver_upi_id=a.upi_id and a.phone_number=g.phone_no and g.google_account_type='user' group by g.name order by t.made_on desc)as z,accounts as a,google_pay_user as g where (z.sender_upi_id=a.upi_id and a.phone_number=g.phone_no))as x,accounts as a,google_pay_user as g where x.receiver_upi_id=a.upi_id and a.phone_number=g.phone_no";
			ResultSet rs= s.executeQuery(sql);
			JSONObject jsonObject = new JSONObject();
			JSONArray array= new JSONArray();
			while(rs.next()) {
				   JSONObject record = new JSONObject();
				   record.put("SENDER NAME", rs.getString("sender_name"));
				   record.put("RECEIVER NAME", rs.getString("receiver_name"));
				   /*byte[] sender_profile=rs.getBytes("sender_profile");
				   ImageIcon sender_profile1=new ImageIcon(sender_profile);
				   Image sender_image=sender_profile1.getImage();
				   Image sender_image1=sender_image.getScaledInstance(10, 10, 10);
				   ImageIcon sender_image2=new ImageIcon(sender_image1);
				   byte[] receiver_profile=rs.getBytes("receiver_profile");
				   ImageIcon receiver_profile1=new ImageIcon(receiver_profile);
				   Image receiver_image=receiver_profile1.getImage();
				   Image receiver_image1=receiver_image.getScaledInstance(10, 10, 10);
				   ImageIcon receiver_image2=new ImageIcon(receiver_image1);
				   out.print(receiver_image2);*/
				   record.put("SENDER PROFILE", rs.getBlob("sender_profile"));
				   record.put("RECEIVER PROFILE", rs.getBlob("receiver_profile"));
				   record.put("MADE_ON", rs.getString("made_on"));
				   array.put(record);
				}
			out.print("<h1>USER</h1>");
			jsonObject.put("user_contacts", array);
			out.println(jsonObject);
			/*PrintWriter out1 = res.getWriter();
	        res.setContentType("application/json");
	        res.setCharacterEncoding("UTF-8");*/
			
			String sql1="select made_on,sender_name,g.name as receiver_name,sender_profile,g.image as receiver_profile from(select made_on,g.name as sender_name,g.image as sender_profile,z.receiver_upi_id from(select max(t.made_on) as made_on,t.sender_upi_id,t.receiver_upi_id from(select a.upi_id from google_pay_user as g,accounts as a where g.phone_no="+phone_number+" and g.phone_no=a.phone_number) as k,google_pay_user as g,accounts as a,transaction as t where (t.sender_upi_id=k.upi_id or t.receiver_upi_id=k.upi_id) and t.receiver_upi_id=a.upi_id and a.phone_number=g.phone_no and g.google_account_type='bussiness' group by g.name order by t.made_on desc)as z,accounts as a,google_pay_user as g where (z.sender_upi_id=a.upi_id and a.phone_number=g.phone_no))as x,accounts as a,google_pay_user as g where x.receiver_upi_id=a.upi_id and a.phone_number=g.phone_no";
			ResultSet rs1=s.executeQuery(sql1);
			JSONObject jsonObject2 = new JSONObject();
			JSONArray array1 = new JSONArray();
		    while(rs1.next()){
		    	JSONObject record1 = new JSONObject();
				   //Inserting key-value pairs into the json object
		    	record1.put("SENDER NAME", rs1.getString("sender_name"));
				   record1.put("RECEIVER NAME", rs1.getString("receiver_name"));
				   record1.put("SENDER PROFILE", rs1.getBlob("sender_profile"));
				   record1.put("RECEIVER PROFILE", rs1.getBlob("receiver_profile"));
				   record1.put("MADE_ON", rs1.getString("made_on"));
				   array1.put(record1);
		    }
		    out.println("<h1>Bussiness</h1>");
		    jsonObject2.put("Business_contacts", array1);
		    out.println(jsonObject2);
		    out.println("<!DOCTYPE html><html><head><title>Insert title here</title>"+"<style>body{align:center;}</style>"
		    +"<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js' ></script></head><body style='align:center;'>"
		    +"<form action ='/googlepay/api/v1/rewards?' method='POST' id='form'>"+
		        
		        "<input type='hidden' name='obj' id='obj' readonly>"+
		        "<input type='submit' name='' onclick = 'rewards()' value='rewards'>"+
		        "<script>"+
		            "function rewards(e){"+
		                
		               "var object = {phone_number:'"+phone_number+"'};"+
		                "var param = JSON.stringify(object);"+
		                "var obj = document.getElementById('obj');"+
		                "obj.setAttribute('value',param);"+
		        "}</script></form></body></html>");
		    out.println("<!DOCTYPE html><html><head><title>Insert title here</title>"+"<style>body{align:center;}</style>"
				    +"<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js' ></script></head><body style='align:center;'>"
				    +"<form action ='/googlepay/api/v1/transaction?' method='POST' id='form'>"+
				        
				        "<input type='hidden' name='obj1' id='obj1' readonly>"+
				        "<input type='submit' name='' onclick = 'transaction()' value='transaction'>"+
				        "<script>"+
				            "function transaction(e){"+
				                
				               "var object = {phone_number:'"+phone_number+"'};"+
				                "var param = JSON.stringify(object);"+
				                "var obj1 = document.getElementById('obj1');"+
				                "obj1.setAttribute('value',param);"+
				        "}</script></form></body></html>");
				    out.println("<!DOCTYPE html><html><head><title>Insert title here</title>"+"<style>body{align:center;}</style>"
						    +"<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js' ></script></head><body style='align:center;'>"
						    +"<form action ='/googlepay/payment.jsp' method='POST' id='form'>"+
						        
						        "<input type='hidden' name='phone_number' id='phone_number' readonly>"+
						        "<input type='submit' name='' onclick = 'payment()' value='payment'><br><br>"+
						        "<script>"+
						            "function payment(e){"+
						            "var phone_number=document.getElementById('phone_number');"+
						                "phone_number.setAttribute('value',"+phone_number+");"+
						        "}</script></form></body></html>");
				    out.println("<!DOCTYPE html><html><head><title>Insert title here</title>"+"<style>body{align:center;}</style>"
						    +"<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js' ></script></head><body style='align:center;'>"
						    +"<form action ='/googlepay/balance.jsp' method='POST' id='form'>"+
						        
						        "<input type='hidden' name='phone_number1' id='phone_number1' readonly>"+
						        "<input type='submit' name='' onclick = 'balance()' value='balance'><br><br>"+
						        "<script>"+
						            "function balance(e){"+
						            "var phone_number1=document.getElementById('phone_number1');"+
						                "phone_number1.setAttribute('value',"+phone_number+");"+
						        "}</script></form></body></html>");
				    out.println("<!DOCTYPE html><html><head><title>Insert title here</title>"+"<style>body{align:center;}</style>"
						    +"<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js' ></script></head><body style='align:center;'>"
						    +"<form action ='/googlepay/api/v1/particular_transaction?' method='POST' id='form'>"+
						        
						        "<input type='hidden' name='obj2' id='obj2' readonly>"+
						        "<input type='text' name='name' id='name'>"+
						        "<input type='submit' name='' onclick = 'ptransaction()' value='ptransaction'>"+
						        "<script>"+
						            "function ptransaction(e){"+
						               "var name=document.getElementById('name');"+
						               "var object = {phone_number:'"+phone_number+"',name:name.value};"+
						                "var param = JSON.stringify(object);"+
						                "var obj2 = document.getElementById('obj2');"+
						                "obj2.setAttribute('value',param);"+
						        "}</script></form></body></html>");
				    
				   
		    
		    String sql3="select * from accounts where phone_number='"+phone_number+"'";
		    if(s.executeQuery(sql3)==null){
		    	 out.println("<button>add account</button>");
		    	 req.setAttribute("phone_number",phone_number);
				 RequestDispatcher rd =req.getRequestDispatcher("/api/v1/contacts");
				 rd.include(req,res);
		    }
		    
		    s.close();
	    	con.close();
	    	
		}
		catch(Exception e){
			e.getStackTrace();
			out.println("Error in database connection"+e);
		}
	}
}




