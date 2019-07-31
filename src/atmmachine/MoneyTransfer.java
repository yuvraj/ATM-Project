package atmmachine;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MoneyTransfer {
	static final Logger logger = LogManager.getLogger(MoneyTransfer.class.getName());
	void transfer(long acc_no)
	{
		int sender_acc_balance=0;  //account balance of sender
		int receiver_acc_balance=0;  //account balance of receiver
		long sender_acc_number=acc_no; 
		int receiver_acc_number=0;	
		
		try{  
			Connection con= DBConnection.getConnection();	  
			//step3 create the statement object  
			Statement stmt=con.createStatement();
			//step4 execute query retrieve 
			ResultSet rs=stmt.executeQuery("select account_balance from account,atm_card where account.card_no=ATM_CARD.card_no and atm_card.card_no=1111222233334444");  
			while(rs.next()) {		
			sender_acc_balance = rs.getInt("account_balance"); 
			System.out.println("Your balance is "+sender_acc_balance);  
			
			}
		
		System.out.print("Please Enter Amount to transfer:\n");
		Scanner amtobj=new Scanner(System.in);
		int amt=amtobj.nextInt();
		
if(sender_acc_balance <amt)
    	{
			System.out.print("You do not have enough balance");
    	}
else
		{
		System.out.print("Please Enter beneficiary account number\n");
		Scanner receiver_acc_numberobj=new Scanner(System.in);
		receiver_acc_number=receiver_acc_numberobj.nextInt();
		}
		ResultSet rs2=stmt.executeQuery("select account_balance from account,atm_card where account.card_no=ATM_CARD.card_no and account.acc_number='"+receiver_acc_number+"'");
		while(rs2.next()) {	 	
		receiver_acc_balance = rs2.getInt("account_balance");
		}
	    	if(sender_acc_balance >amt)
	    	{
	    		sender_acc_balance=sender_acc_balance-amt;
	    	    receiver_acc_balance= receiver_acc_balance+amt;

	    		String query2 = "update account set account_balance ="+receiver_acc_balance+" where account_no='"+receiver_acc_number+"'";
	    		stmt.executeUpdate(query2);
	    		
	    	    String query = " update account set account_balance ='"+sender_acc_balance+"' where card_no='"+sender_acc_number+"'" ;
	    		stmt.executeUpdate(query);
	    	    
	    	    System.out.println("Your transaction is processed successfully");
	    	    System.out.println(sender_acc_balance);
	    	    System.out.println(receiver_acc_balance);
	    	}
	    	else
	    	{
	    		System.out.print("You dont have balance");
	    	}
	    
		}
		catch(Exception e)
		{
			System.out.print("Invalid account number");
		}
	    }
	}

