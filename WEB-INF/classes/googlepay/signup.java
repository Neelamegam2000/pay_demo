package googlepay;

import java.io.*;
//import java.nio.charset.Charset;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;
//import java.util.Random;

//import org.json.JSONArray;
import org.json.JSONException;
//import org.json.JSONException;
import org.json.JSONObject;


@WebServlet("/api/v1/signup")

public class signup extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
  
    
    //GET METHOD
    
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	}
	//POST METHOD
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter out=res.getWriter();
		res.setContentType("text/html");
		String string=null;
		String phone_number=null;
		String email=null;
		String account_type=null;
		String name=null;
		string = req.getParameter("obj");
		
		string=string.toString();
		
        JSONObject jsonObject1=null;
		try {
			jsonObject1 = new JSONObject(string);
			name=jsonObject1.getString("name");
			email=jsonObject1.getString("email");
			account_type=jsonObject1.getString("account_type");
			phone_number=jsonObject1.getString("phone_number");	
		} catch (JSONException e2) {
			e2.printStackTrace();
		}    
		try{
			//Connection
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/google_pay","root","");
			//String phone_number = (String)req.getAttribute("phone_number");
			PreparedStatement ps=con.prepareStatement("insert into google_pay_user values(?,?,?,?,?)"); 
			PreparedStatement ps1=con.prepareStatement("insert into bussiness_account values(?,?,?)");  
		    //s.executeUpdate("INSERT INTO google_pay_user VALUES ("+phone_number+","+name+","+email+","+account_type+")");
		   // s.close();
			ps.setString(1, phone_number);
			ps.setString(2, name);
			ps.setString(3, email);
			ps.setString(4, account_type);
			ps.setString(5, null);
			ps.executeUpdate();
			if(account_type!="user"){
				    Integer merchant_id=(int)(((Math.random())*100000)+1);
					ps1.setString(1,phone_number);
					ps1.setInt(2,merchant_id );
					String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                            + "0123456789"
                            + "abcdefghijklmnopqrstuvxyz";
					StringBuilder sb = new StringBuilder(20);
					  
				        for (int i = 0; i < 20; i++) {
				  
				            // generate a random number between
				            // 0 to AlphaNumericString variable length
				            int index
				                = (int)(AlphaNumericString.length()
				                        * Math.random());
				  
				            // add Character one by one in end of sb
				            sb.append(AlphaNumericString
				                          .charAt(index));
				        }
				  
				    String merchant_key=sb.toString();
					ps1.setString(3,merchant_key);
					ps1.executeUpdate();
			}
			out.println("<h1>Successfully signed</h1>");
			req.setAttribute("phone_number",phone_number);
			RequestDispatcher rd = req.getRequestDispatcher("/add_account.jsp");
			rd.include(req,res);
	    	con.close();
		}
		catch(Exception e){
			e.getStackTrace();
			out.println("Error in database connection"+e);
		}
	}

	}



