package googlepay;
import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;

import org.json.JSONArray;
import org.json.JSONException;
//import org.json.JSONException;
import org.json.JSONObject;


@WebServlet("/api/v1/rewards")

public class rewards extends HttpServlet {
	
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
		String string=null;
		String phone_number=null;
		string = req.getParameter("obj");
		string=string.toString();
        JSONObject jsonObject1=null;
		try {
			jsonObject1 = new JSONObject(string);
			phone_number=jsonObject1.getString("phone_number");
		} catch (JSONException e2) {
			e2.printStackTrace();
		}  
		try{
			//Connection
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/google_pay","root","");
			Statement s=con.createStatement();
			//String phone_number = (String)req.getAttribute("phone_number");
		    String sql2="select distinct(p.transaction_id),p.amount,p.receiver_upi_id,p.reward_id,p.reward_amount,p.coupon_id,c.coupon_details,c.expiry_date from (select t.transaction_id,t.amount,t.receiver_upi_id,r.reward_id,r.reward_amount,r.coupon_id from (select a.upi_id from google_pay_user as g,accounts as a where g.phone_no='"+phone_number+"' and g.phone_no=a.phone_number) as k,rewards as r,transaction as t where t.sender_upi_id=k.upi_id and t.transaction_id=r.transaction_id order by t.made_on desc)as p left join coupon as c on p.coupon_id=c.coupon_id";
		    ResultSet rs3=s.executeQuery(sql2);
		    JSONObject jsonObject2 = new JSONObject();
			JSONArray array2 = new JSONArray();
		    while(rs3.next()){
		    	JSONObject record2 = new JSONObject();
				   //Inserting key-value pairs into the json object
				   record2.put("TRANSACTION ID", rs3.getString("transaction_id"));
				   record2.put("AMOUNT", rs3.getString("amount"));
				   record2.put("RECEIVER UPI ID", rs3.getString("receiver_upi_id"));
				   record2.put("REWARD AMOUNT", rs3.getString("reward_amount"));
				   record2.put("COUPON ID", rs3.getString("coupon_id"));
				   array2.put(record2);
		    }
		    jsonObject2.put("Rewards", array2);
		    out.println("<h1>Rewards</h1>");
		    out.print(jsonObject2);
		    /*PrintWriter out1 = res.getWriter();
	        res.setContentType("application/json");
	        res.setCharacterEncoding("UTF-8");
	        out1.println(jsonObject2);
	        out1.flush();*/
		    s.close();
	    	con.close();
		}
		catch(Exception e){
			e.getStackTrace();
			out.println("Error in database connection"+e);
		}
	}

	}



