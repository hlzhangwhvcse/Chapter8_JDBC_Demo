import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCDemo6 
{
	private Connection connection = null;
	private Statement  statement = null;
	
	//��̬�����:��java��ʹ��static�ؼ��������Ĵ���顣��̬�����ڳ�ʼ���࣬Ϊ������Գ�ʼ����ÿ����̬�����ֻ��ִ��һ�Σ�JVM�ڼ�����ʱִ�о�̬����飬��̬���������������ִ�С�
	//ע�⣺1 ��̬����鲻�ܴ������κη������ڡ�2 ��̬����鲻��ֱ�ӷ��ʾ�̬ʵ��������ʵ����������Ҫͨ�����ʵ�����������ʡ�
	static 
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void prepareConnection() 
	{
		try 
		{
			if (null == connection || connection.isClosed()) 
			{
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "root");
			}
		} 
		catch (SQLException sqlException) 
		{
			throw new RuntimeException("�����쳣    :"+ sqlException.getMessage());
		}
	}
	
	private void close()
	{
		try
		{
			if (statement != null) 
			{
				statement.close();
			}
			
			if (connection != null) 
			{
				connection.close();
			}
		}
		catch (SQLException sqlException) 
		{
			throw new RuntimeException("�ر������쳣:" + sqlException.getMessage());
		}
	}	
	
	private void rollback()
	{
		try 
		{
			connection.rollback();
		} 
		catch (SQLException sqlException) 
		{
			throw new RuntimeException("�ع�ʧ��:" + sqlException.getMessage());
		}
	}
	public void addCity(int id, String name, String countryCode, String district, int population) 
	{
		prepareConnection();
		try 
		{
			connection.setAutoCommit(false);//Ĭ��Ϊ�Զ��ύ�� ִ��update ,delete����insert�����Զ��ύ�����ݿ⣬�޷��ع����� 
			//����connection.setautocommit(false),ֻ�г������connection.commit()ʱ�ŻὫ��ǰִ������ύ�����ݿ⣬ʵ�����ݿ�����
			//true��sql������ύ��commit�������������� 
			//false��sql������ύ��Ӧ�ó����𣬳���������commit����rollback����
			
			statement = connection.createStatement();
			
			//��̬�������ʱ����Ҫ����ܸ��ӵ�sql����ַ������׳��������Ķ�
			String sqlStatement = "insert into city(ID, Name, CountryCode, District, Population)" + "values(" + id +	", '" + name + "' ,'"  + countryCode + "', '" + district + "', " + population + ")";	
			System.out.println("Ҫִ�е�sql����ǣ�" + sqlStatement);
			
			int rowCount = statement.executeUpdate(sqlStatement);//executeUpdate()��Statement������ִ�л�����ݿ�����Ӱ���sql��䣬��insert, update, delete��������Ӱ�������
			connection.commit();	
			
			if (rowCount > 0) 
			{
				System.out.println("�ɹ������ˣ�" + rowCount + "�м�¼");
			}
		} 
		catch (SQLException sqlException) 
		{
			rollback();
			throw new RuntimeException("��Ӽ�¼�쳣��" + sqlException.getMessage());
		}
		finally
		{
			close();
		}
	}
	public static void main(String[] args) 
	{
		new JDBCDemo6().addCity(4080, "Wu Han", "CHN", "Hubei", 8000000);
	}
	
}