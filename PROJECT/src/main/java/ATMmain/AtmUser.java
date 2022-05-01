package ATMmain;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class AtmUser {

	Databaseconn ob=new Databaseconn();
	Connection conn=ob.dbConnection();
	
	public boolean login(long eid,String password) throws SQLException 
	{
		
		PreparedStatement ps=conn.prepareStatement("select * from Saving_acc where acc_id=? && Pin=?");
		ps.setLong(1, eid);
		ps.setString(2, password);
		ResultSet result=ps.executeQuery();
		
		
			if(result.next())
			{
				return true; 
			}
			else
			{
				return false;
			}
			
		
	}		
	
	
	public void withdraw(long uid,double withdrawAmount) 
	{
		try
		{
		PreparedStatement ps=conn.prepareStatement("select * from Saving_acc where acc_id=?");
		ps.setLong(1, uid);
		
		ResultSet result=ps.executeQuery();
		double availableBalance=0.0;
		
			while(result.next())
			{
				availableBalance=result.getDouble(6);
			}
			
		
		
		
		if(withdrawAmount<=availableBalance)
		{
			availableBalance=availableBalance-withdrawAmount;
			PreparedStatement ps1=conn.prepareStatement("update Saving_acc set ava_bal=? where acc_id=?");
			ps1.setDouble(1, availableBalance);
			ps1.setLong(2, uid);
			ps1.executeUpdate();
			
			System.out.println("Withdrawal Successfull!!!");
			System.out.println("Remaining Balance:"+availableBalance);
			
			
			
		}
		else
		{
			System.out.println("Sufficient Balance not available!!");
			
		}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		
		
	}
	
	
	public void deposit(long uid,double depositAmount)
	{
		try
		{
		PreparedStatement ps=conn.prepareStatement("select * from Saving_acc where acc_id=?");
		ps.setLong(1, uid);
		
		ResultSet result=ps.executeQuery();
		double availableBalance=0.0;
		
			while(result.next())
			{
				availableBalance=result.getDouble(6);
			}
			
			availableBalance=availableBalance+depositAmount;
		
			PreparedStatement ps1=conn.prepareStatement("update Saving_acc set ava_bal=? where acc_id=?");
			ps1.setDouble(1, availableBalance);
			ps1.setLong(2, uid);
			ps1.executeUpdate();
			
			System.out.println("Deposit Successfull!!!");
			System.out.println("New Balance:"+availableBalance);

			
		}
		catch(Exception e)
		{
			System.out.println("Problem in execution!!");
		}
	}
	
	
	public void balanceCheck(long uid)
	{
		try
		{
		PreparedStatement ps=conn.prepareStatement("select * from Saving_acc where acc_id=?");
		ps.setLong(1, uid);
		
		ResultSet result=ps.executeQuery();
		double availableBalance=0.0;
		
			while(result.next())
			{
				availableBalance=result.getDouble(6);
			}
		System.out.println("Available Balance:"+availableBalance);	
		}
		catch(Exception e)
		{
			System.out.println("Problem in balance check!!");
		}
	}
		
	public boolean changePassword(long accId,String newPassword) throws SQLException
	{
		PreparedStatement ps=conn.prepareStatement("update Saving_acc set Pin=? where acc_id=? ");
		ps.setString(1, newPassword);
		ps.setLong(2, accId);
		int affectedRows=ps.executeUpdate();
		
		if(affectedRows>0)
			return true;
		else
			return false;
	}
	
	public boolean logout() throws SQLException
	{
		conn.close();
		return true;
	}
	
	public void fundTransfer(long sid,long rid,double amount)
	{
		try
		{
			//Receiver balance updation
		PreparedStatement ps=conn.prepareStatement("select * from Saving_acc where acc_id=? ");
		ps.setLong(1, rid);
		ResultSet result=ps.executeQuery();
		double receiverBalance=0.0;
		
			while(result.next())
			{
				receiverBalance=result.getDouble(6);
			}
			
			receiverBalance=receiverBalance+amount;
			PreparedStatement ps1=conn.prepareStatement("update Saving_acc set available_balance=? where acc_id=? ");
			ps1.setDouble(1, receiverBalance);
			ps1.setLong(2, rid);
			int affectedRows=ps1.executeUpdate();
			
			//Sender Account Balance Updation
			
			
			PreparedStatement ps3=conn.prepareStatement("select * from Saving_acc where acc_id=? ");
			ps3.setLong(1, sid);
			ResultSet result1=ps3.executeQuery();
			double senderBalance=0.0;
			
				while(result1.next())
				{
					senderBalance=result1.getDouble(6);
				}
				senderBalance=senderBalance-amount;
				PreparedStatement ps4=conn.prepareStatement("update Saving_acc set ava_bal=? where acc_id=? ");
				ps4.setDouble(1, senderBalance);
				ps4.setLong(2, sid);
				int a=ps4.executeUpdate();
				
				
				Random r=new Random();
				long tid=r.nextInt(9999);
				
				java.util.Date now=new java.util.Date();
				Date d=new Date(now.getDate());
				
				
				PreparedStatement ps5=conn.prepareStatement("insert into transaction values(?,?,?,?,?) ");
				ps5.setLong(1, tid);
				ps5.setDouble(2, amount);
				ps5.setDate(3,d);
				ps5.setLong(4, rid);
				ps5.setLong(5, sid);
				affectedRows=ps5.executeUpdate();
				
				if(affectedRows>0)
				{
					System.out.println("Transaction Successfull!!");
				}
				else
				{
					System.out.println("Transaction Failed!!");
				}
									
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			System.out.println("Something went wrong!!");
		}
				
		
	}
	public ResultSet transaction(long accId)
	{
		ResultSet result=null;
		try
		{
		PreparedStatement ps=conn.prepareStatement("select * from transaction where sender_acc_id=? ");
		ps.setLong(1, accId);
		result=ps.executeQuery();
		
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			System.out.println("Something went wrong!!");
		}
		return result;
	}

	
	

}

