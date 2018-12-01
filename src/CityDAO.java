import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CityDAO 
{
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	static 
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
		} 
		catch(ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
	}

	private void prepareconnectionection() 
	{
		try 
		{
			if (connection == null || connection.isClosed()) 
			{
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "root");
			}
		} 
		catch (SQLException e) 
		{
			throw new RuntimeException("�����쳣    :" + e.getMessage());
		}
	}

	private void close() 
	{
		try 
		{
			if (preparedStatement != null) 
			{
				preparedStatement.close();
			}
			if (connection != null) 
			{
				connection.close();
			}
		} 
		catch(SQLException e) 
		{
			throw new RuntimeException("�ر������쳣:" + e.getMessage());
		}
	}

	private void rollback() 
	{
		try 
		{
			connection.rollback();
		} 
		catch (SQLException e) 
		{
			throw new RuntimeException("�ع�ʧ��:" + e.getMessage());
		}
	}

	public int addCity(City city) 
	{
		int rowCount = 0;
		try 
		{
			prepareconnectionection();
			connection.setAutoCommit(false);
			//�����¼����Ҫָ��ID����Ϊcity���ID�ֶ�����Ϊ������
			preparedStatement = connection.prepareStatement("insert into city(Name, CountryCode, District, Population) values(?, ?, ?, ?)");
			preparedStatement.setString(1, city.getName());
			preparedStatement.setString(2, city.getCountryCode());
			preparedStatement.setString(3, city.getDistrict());
			preparedStatement.setInt(4, city.getPopulation());
			rowCount = preparedStatement.executeUpdate();
			connection.commit();
		} 
		catch (SQLException e) 
		{
			rollback();
			e.printStackTrace();
		} 
		finally 
		{
			close();
		}
		return rowCount;
	}

	public int deleteCity(City city) 
	{
		int rowCount = 0;
		try 
		{
			prepareconnectionection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement("delete from city where id = ?");
			preparedStatement.setInt(1, city.getId());
			rowCount = preparedStatement.executeUpdate();
			connection.commit();
		} 
		catch (SQLException e) 
		{
			rollback();
			e.printStackTrace();
		} 
		finally 
		{
			close();
		}
		return rowCount;
	}
	
	public int deleteCityById(int id) 
	{
		int rowCount = 0;
		try 
		{
			prepareconnectionection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement("delete from city where id = ?");
			preparedStatement.setInt(1, id);
			rowCount = preparedStatement.executeUpdate();
			connection.commit();
		} 
		catch (SQLException e) 
		{
			rollback();
			e.printStackTrace();
		} 
		finally 
		{
			close();
		}
		return rowCount;
	}
	
	

	public int updateCity(City city) 
	{
		int rowCount = 0;
		try 
		{
			prepareconnectionection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement("update city set Name = ?, Population = ? where id = ?");
			preparedStatement.setString(1, city.getName());
			preparedStatement.setInt(2, city.getPopulation());
			preparedStatement.setInt(3, city.getId());
			rowCount = preparedStatement.executeUpdate();
			connection.commit();
		} 
		catch(SQLException e) 
		{
			rollback();
			e.printStackTrace();
		} 
		finally 
		{
			close();
		}
		return rowCount;
	}

	public List<City> getAllCities() 
	{
		List<City> cities = new ArrayList<City>();
		try 
		{
			prepareconnectionection();
			preparedStatement = connection.prepareStatement("select * from city");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) 
			{
				City city = new City();
				city.setId(resultSet.getInt(1));
				city.setName(resultSet.getString(2));
				city.setCountryCode(resultSet.getString(3));
				city.setDistrict(resultSet.getString(4));
				city.setPopulation(resultSet.getInt(5));
				cities.add(city);//���������к��м�¼���ͽ���¼��װ��Ϊһ��City������ӵ�����List��
				
			}
		} 
		catch(SQLException e) 
		{
			e.printStackTrace();
		} finally {
			close();
		}
		
		return cities;
	}

	public City getCityById(Integer id) 
	{
		City city = null;
		try 
		{
			prepareconnectionection();
			preparedStatement = connection.prepareStatement("select * from city where id = ?");
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) //���������к��м�¼���ͽ���¼��װ��Ϊһ��City����
			{
				city = new City();
				city.setId(resultSet.getInt(1));
				city.setName(resultSet.getString(2));
				city.setCountryCode(resultSet.getString(3));
				city.setDistrict(resultSet.getString(4));
				city.setPopulation(resultSet.getInt(5));
				
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			close();
		}
		return city;
	}

}
