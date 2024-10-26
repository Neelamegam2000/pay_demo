package googlepay;


import java.io.*;

//import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;

//import org.json.JSONArray;
import org.json.JSONException;
//import org.json.JSONException;
import org.json.JSONObject;


@WebServlet("/api/v1/accountpay")

public class accountpay extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
  
    
    //GET METHOD
    
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	}
	//POST METHOD
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter out=res.getWriter();
		res.setContentType("application/json");
		String string=null;
		String phone_number=null;
		Integer account_number=null;
		String holder_name=null;
		String ifsc_code=null;
		Integer upi_pin =null;
		Integer amount=null;
		string = req.getParameter("obj1");
		string=string.toString();
        JSONObject jsonObject1=null;
		try {
			jsonObject1 = new JSONObject(string);
			phone_number=jsonObject1.getString("phone_number");
			upi_pin=jsonObject1.getInt("upi_pin");
			amount=jsonObject1.getInt("amount");
			holder_name=jsonObject1.getString("holder_name");
			account_number=jsonObject1.getInt("account_number");
			ifsc_code=jsonObject1.getString("ifsc_code");
			
		} catch (JSONException e2) {
			e2.printStackTrace();
			out.println(e2);
		}
	       
		try{
			//Connection
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/google_pay","root","");
			Statement s=con.createStatement();
			String sql="select upi_id from accounts where phone_number='"+phone_number+"' and upi_pin="+upi_pin+" and active_status='active';";
		    ResultSet rs= s.executeQuery(sql);
		    rs.next();
		    String sender_upi_id=rs.getString("upi_id");
			//String phone_number = (String)req.getAttribute("phone_number");
			PreparedStatement ps=con.prepareStatement("insert into transaction values(?,?,?,?,?,?)");
			PreparedStatement ps2=con.prepareStatement("insert into account_transaction values(?,?,?,?)");
			PreparedStatement ps1=con.prepareStatement("update accounts set balance=? where upi_id=?");
			PreparedStatement ps4=con.prepareStatement("insert into rewards values(?,?,?,?)");
		    //s.executeUpdate("INSERT INTO google_pay_user VALUES ("+phone_number+","+name+","+email+","+account_type+")");
		   // s.close
			Date date = new Date();
			String modifiedDate= new SimpleDateFormat("yyyy/MM/dd").format(date);
			Integer transaction_id=(int)(((Math.random())*100000000)+1);
			ps.setInt(1, transaction_id);
			ps.setString(2, sender_upi_id);
			ps.setString(3, null);
			ps.setInt(4, amount);
			ps.setString(5, modifiedDate);
			ps.setString(6, "successful");
			String sql1="select balance from accounts where upi_id='"+sender_upi_id+"' and active_status='active';";
			String sql4="select coupon_id from coupon order by RAND() LIMIT 1;";
			ResultSet rs1=s.executeQuery(sql1);
			rs1.next();
			if(rs1.getInt("balance")>=amount){
				Integer sender_balance=rs1.getInt("balance")-amount;
				ps1.setInt(1,sender_balance );
				ps1.setString(2, sender_upi_id);
				ps2.setInt(1,transaction_id);
				ps2.setInt(2,account_number);
				ps2.setString(4, holder_name);
				ps2.setString(3, ifsc_code);
				ps.executeUpdate();
				ps1.executeUpdate();
				ps2.executeUpdate();
			}
				ResultSet rs4=s.executeQuery(sql4);
				rs4.next();
				out.println(rs4.getString("coupon_id"));
				String s1=rs4.getString("coupon_id");
				if(s1.equals("null")){
					Integer reward_id=(int)(((Math.random())*1000)+1);
				    ps4.setInt(1,reward_id);
				    ps4.setInt(2, transaction_id);
				    ps4.setInt(3,(int)(((Math.random())*10)+1));
				    ps4.setString(4, null);
				    ps4.executeUpdate();
				    
				}
				else{
					Integer reward_id=(int)(((Math.random())*1000)+1);
				    ps4.setInt(1,reward_id);
				    ps4.setInt(2, transaction_id);
				    ps4.setString(3,null);
				    ps4.setString(4,rs4.getString("coupon_id"));
				    ps4.executeUpdate();
				}
				
			out.println("payment_successful");
	    	con.close();
		}
		catch(Exception e){
			e.getStackTrace();
			out.println("Error in database connection"+e);
		}
	}

	}




