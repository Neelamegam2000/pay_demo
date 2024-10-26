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
import org.json.JSONObject;


@WebServlet("/api/v1/particular_transaction")

public class particular_transaction extends HttpServlet {
	
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
	    }*/
		String string=null;
		String phone_number=null;
		String Name=null;
		string = req.getParameter("obj2");
		string=string.toString();
        JSONObject jsonObject1=null;
		try {
			jsonObject1 = new JSONObject(string);
			phone_number=jsonObject1.getString("phone_number");
			Name=jsonObject1.getString("name");
		} catch (JSONException e2) {
			e2.printStackTrace();
		}
	    
		try{
			//Connection
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/google_pay","root","");
			Statement s=con.createStatement();
		    String sql4="select z.amount,z.made_on,z.sender_upi_id,z.receiver_upi_id,z.transaction_id,z.sender_name,z.status,g.name as receiver_name from (select  k.amount,k.made_on,k.sender_upi_id,k.receiver_upi_id,k.transaction_id,k.status,g.name as sender_name from(select p.amount,p.made_on,p.sender_upi_id,p.receiver_upi_id,p.transaction_id,p.status from (select t.transaction_id,t.amount,g.name,t.sender_upi_id,t.made_on,t.receiver_upi_id,t.status from transaction as t,accounts as a,google_pay_user as g where g.name='"+Name+"' and g.phone_no=a.phone_number and (a.upi_id=t.receiver_upi_id or a.upi_id=t.sender_upi_id))as p,accounts as a where a.phone_number='"+phone_number+"' and (a.upi_id=p.sender_upi_id or a.upi_id=p.receiver_upi_id)) as k,accounts as a,google_pay_user as g where k.sender_upi_id=a.upi_id and a.phone_number=g.phone_no) as z,accounts as a,google_pay_user as g where z.receiver_upi_id=a.upi_id and a.phone_number=g.phone_no";
		    ResultSet rs5=s.executeQuery(sql4);
		    JSONObject jsonObject4 = new JSONObject();
			JSONArray array4 = new JSONArray();
		    while(rs5.next()){
		    	JSONObject record4 = new JSONObject();
				   //Inserting key-value pairs into the json object
				   record4.put("SENDER UPI ID", rs5.getString("sender_upi_id"));
				   record4.put("RECEIVER UPI ID", rs5.getString("receiver_upi_id"));
				   record4.put("SENDER NAME", rs5.getString("sender_name"));
				   record4.put("RECEIVER UPI ID", rs5.getString("receiver_name"));
				   record4.put("AMOUNT", rs5.getString("amount"));
				   record4.put("MADE ON", rs5.getString("made_on"));
				   array4.put(record4);
		    }
		    jsonObject4.put("Particular_Transaction", array4);
		    out.println("<h1>Particular_transaction</h1>");
	        out.println(jsonObject4);
		    s.close();
	    	con.close();
		}
		catch(Exception e){
			e.getStackTrace();
			out.println("Error in database connection"+e);
		}
	}
}


