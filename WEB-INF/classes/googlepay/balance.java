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


@WebServlet("/api/v1/balance")

public class balance extends HttpServlet {
	
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
		String upi_pin=null;
		string = req.getParameter("obj1");
		string=string.toString();
        JSONObject jsonObject1=null;
		try {
			jsonObject1 = new JSONObject(string);
			phone_number=jsonObject1.getString("phone_number");
			upi_pin=jsonObject1.getString("upi_pin");
		} catch (JSONException e2) {
			e2.printStackTrace();
		}
	    
		try{
			//Connection
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/google_pay","root","");
			Statement s=con.createStatement();
		    String sql4="select balance from accounts where phone_number='"+phone_number+"' and upi_pin="+upi_pin+" and active_status='active';";
		    ResultSet rs5=s.executeQuery(sql4);
		    JSONObject jsonObject4 = new JSONObject();
			JSONArray array4 = new JSONArray();
		    while(rs5.next()){
		    	JSONObject record4 = new JSONObject();
				   //Inserting key-value pairs into the json object
				   record4.put("BALANCE", rs5.getString("balance"));
				 
				   array4.put(record4);
		    }
		    jsonObject4.put("balance", array4);
		    out.println("<h1>YOUR BALANCE</h1>");
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


