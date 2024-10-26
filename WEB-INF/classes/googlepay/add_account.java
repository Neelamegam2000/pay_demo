package googlepay;

import java.io.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;

//import org.json.JSONArray;
import org.json.JSONException;
//import org.json.JSONException;
import org.json.JSONObject;


@WebServlet("/api/v1/add_account")

public class add_account extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
  
    
    //GET METHOD
    
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	}
	//POST METHOD
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter out=res.getWriter();
		res.setContentType("text/html");
		String string=null;
		Integer account_number=null;
		String account_details=null;
		String upi_id=null;
		Integer upi_pin=null;
		String active_status=null;
		Integer balance=null;
		String phone_number=null;
		string = req.getParameter("obj");
		string=string.toString();
        JSONObject jsonObject1=null;
		try {
			jsonObject1 = new JSONObject(string);
			account_number=jsonObject1.getInt("account_number");
			account_details=jsonObject1.getString("account_details");
			upi_id=jsonObject1.getString("upi_id");
			upi_pin=jsonObject1.getInt("upi_pin");
			balance=jsonObject1.getInt("balance");
			active_status=jsonObject1.getString("active_status");
			phone_number=jsonObject1.getString("phone_number");
		} catch (JSONException e2) {
			e2.printStackTrace();
		} 
		try{
			//Connection
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/google_pay","root","");
			PreparedStatement ps=con.prepareStatement("insert into accounts values(?,?,?,?,?,?,?)");  
		    //s.executeUpdate("INSERT INTO google_pay_user VALUES ("+phone_number+","+name+","+email+","+account_type+")");
		   // s.close();
			ps.setInt(1, account_number);
			ps.setString(2, account_details);
			ps.setString(3, upi_id);
			ps.setInt(4, upi_pin);
			ps.setInt(5, balance);
			ps.setString(6, active_status);
			ps.setString(7,phone_number);
			ps.executeUpdate();
			ps.close();
			out.println("<h3>Account is added successfully</h3>");
			req.setAttribute("phone_number",phone_number);
			RequestDispatcher rd = req.getRequestDispatcher("/api/v1/contacts");
			rd.include(req,res);
	    	con.close();
	    	
	    	
		}
		catch(Exception e){
			e.getStackTrace();
			out.println("Error in database connection"+e);
		}
	}

	}




