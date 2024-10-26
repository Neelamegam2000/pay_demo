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


@WebServlet("/api/v1/transaction")

public class transaction extends HttpServlet {
	
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
		string = req.getParameter("obj1");
		string=string.toString();
        JSONObject jsonObject1=null;
		try {
			jsonObject1 = new JSONObject(string);
			out.println(jsonObject1);
			phone_number=jsonObject1.getString("phone_number");
		} catch (JSONException e2) {
			e2.printStackTrace();
		}
	    
		try{
			//Connection
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/google_pay","root","");
			Statement s=con.createStatement();
			String sql3="select z.transaction_id,z.sender_upi_id,z.receiver_upi_id,z.amount,g.name as sender_name,z.receiver_name,z.status,z.made_on from (select k.transaction_id,k.sender_upi_id,k.receiver_upi_id,k.amount,g.name as receiver_name,k.status,k.made_on from(select t.transaction_id,t.amount,t.receiver_upi_id,t.sender_upi_id,t.status,t.made_on from transaction as t,accounts as a,google_pay_user as g where a.phone_number="+phone_number+" and a.phone_number=g.phone_no and (a.upi_id=t.sender_upi_id or a.upi_id=t.receiver_upi_id)order by t.made_on desc) as k,google_pay_user as g,accounts as a where k.receiver_upi_id=a.upi_id and a.phone_number=g.phone_no) as z,google_pay_user as g,accounts as a where z.sender_upi_id=a.upi_id and a.phone_number=g.phone_no";
		    ResultSet rs4=s.executeQuery(sql3);
		    JSONObject jsonObject3 = new JSONObject();
			JSONArray array3 = new JSONArray();
		    while(rs4.next()){
		    	JSONObject record3 = new JSONObject();
				   //Inserting key-value pairs into the json object
				   record3.put("TRANSACTION ID", rs4.getString("transaction_id"));
				   record3.put("AMOUNT", rs4.getString("amount"));
				   record3.put("SENDER UPI ID", rs4.getString("receiver_upi_id"));
				   record3.put("RECEIVER UPI ID", rs4.getString("sender_upi_id"));
				   record3.put("SENDER NAME", rs4.getString("sender_name"));
				   record3.put("RECEIVER NAME", rs4.getString("receiver_name"));
				   record3.put("TRANSACTION ID", rs4.getString("transaction_id"));
				   record3.put("STATUS", rs4.getString("status"));
				   record3.put("MADE ON", rs4.getString("made_on"));
				   array3.put(record3);
		    }
		    jsonObject3.put("Transaction", array3);
		    out.println("<h1>Transaction</h1>");
		    out.println(jsonObject3);
		    /*PrintWriter out1 = res.getWriter();
	        res.setContentType("application/json");
	        res.setCharacterEncoding("UTF-8");
	        out1.println(jsonObject3);
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
