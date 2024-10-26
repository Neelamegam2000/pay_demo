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


@WebServlet("/api/v1/phonenumberpay")

public class phonenumberpay extends HttpServlet {
	
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
		String receiver=null;
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
			receiver=jsonObject1.getString("receiver");
			
		} catch (JSONException e2) {
			e2.printStackTrace();
		}
	       
		try{
			//Connection
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/google_pay","root","");
			Statement s=con.createStatement();
			String sql="select upi_id from accounts where phone_number='"+phone_number+"' and upi_pin="+upi_pin+";";
		    ResultSet rs= s.executeQuery(sql);
		    rs.next();
		    String sender_upi_id=rs.getString("upi_id");
		    String sql5="select upi_id from accounts where phone_number='"+receiver+"' and active_status='active';";
		    ResultSet rs5= s.executeQuery(sql5);
		    rs5.next();
		    String receiver_upi_id=rs5.getString("upi_id");
			//String phone_number = (String)req.getAttribute("phone_number");
			PreparedStatement ps=con.prepareStatement("insert into transaction values(?,?,?,?,?,?)");
			PreparedStatement ps1=con.prepareStatement("update accounts set balance=? where upi_id=?");
			PreparedStatement ps2=con.prepareStatement("update accounts set balance=? where upi_id=?");
			PreparedStatement ps3=con.prepareStatement("insert into bill_payment values(?,?,?)");
			PreparedStatement ps4=con.prepareStatement("insert into rewards values(?,?,?,?)");
		    //s.executeUpdate("INSERT INTO google_pay_user VALUES ("+phone_number+","+name+","+email+","+account_type+")");
		   // s.close
			Date date = new Date();
			String modifiedDate= new SimpleDateFormat("yyyy/MM/dd").format(date);
			Integer transaction_id=(int)(((Math.random())*100000000)+1);
			ps.setInt(1, transaction_id);
			ps.setString(2, sender_upi_id);
			ps.setString(3, receiver_upi_id);
			ps.setInt(4, amount);
			ps.setString(5, modifiedDate);
			ps.setString(6, "successful");
			String sql2="select balance from accounts where upi_id='"+receiver_upi_id+"' and active_status='active';";
			String sql1="select balance from accounts where upi_id='"+sender_upi_id+"' and active_status='active';";
			String sql4="select coupon_id from coupon order by RAND() LIMIT 1;";
			ResultSet rs1=s.executeQuery(sql1);
			rs1.next();
			if(rs1.getInt("balance")>=amount){
				Integer sender_balance=rs1.getInt("balance")-amount;
				ps1.setInt(1,sender_balance );
				ps1.setString(2, sender_upi_id);
				ResultSet rs2=s.executeQuery(sql2);
				rs2.next();
				Integer receiver_balance=rs2.getInt("balance")+amount;
				ps2.setInt(1,receiver_balance );
				ps2.setString(2, receiver);
				ps.executeUpdate();
				ps1.executeUpdate();
				ps2.executeUpdate();
				String sql3="select category_id from category where upi_id='"+receiver_upi_id+"';";
				ResultSet rs3=null;
				rs3=s.executeQuery(sql3);
				if(rs3.next()){
					Integer payment_id=(int)(((Math.random())*1000)+1);
					/*String sql4="select payment_id from bill_payment";
					ResultSet rs4=s.executeQuery(sql4);
					rs4.next();
					while(.contains(n)!=)*/
					ps3.setInt(1,payment_id);
					ps3.setInt(2,transaction_id);
					ps3.setString(3, rs3.getString("category_id"));
					ps3.executeUpdate();
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




