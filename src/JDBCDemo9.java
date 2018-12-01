import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class JDBCDemo9 
{

	public static void main(String[] args) 
	{

		Scanner scanner = new Scanner(System.in);
		CityDAO cityDAO = new CityDAO();
		
		while(true) 
		{
			System.out.println("====================================================================================");
			System.out.println("查询全部记录请输入'1'，再按回车键");
			System.out.println("查询指定记录请按格式输入'2#ID'，再按回车键");
			System.out.println("删除记录请按格式输入\"3#ID'，再按回车键\"");
			System.out.println("添加记录请按格式输入'4#Name#CountryCode#District#Population'，再按回车键");
			System.out.println("修改记录请按格式输入'5#ID#Name#CountryCode#District#Population'，再按回车键\"");
			System.out.println("退出请输入'6'，再按回车键");
			System.out.println("====================================================================================");
			
			String str = scanner.next();
			String cmd = str.substring(0, 1);
			String query = str.substring(1);
			if (cmd.equals("1")) 
			{
				List <City> cities = cityDAO.getAllCities();
				System.out.println("|  ID  |  Name  |  CountryCode  |  District  |  Population  |");
				for(City city : cities)
				{
					System.out.print("| ");
					System.out.print(city.getId());
					System.out.print(" | ");
					System.out.print(city.getName());
					System.out.print(" | ");
					System.out.print(city.getCountryCode());
					System.out.print(" | ");
					System.out.print(city.getDistrict());
					System.out.print(" | ");
					System.out.print(city.getPopulation());
					System.out.println(" | ");
				}
			} 
			else if(cmd.equals("2")) 
			{
				StringTokenizer strTokenizer = new StringTokenizer(query, "#");
				int countTokens = strTokenizer.countTokens();
				System.out.println( "‘#’符号的总数量: " + countTokens);
				if(countTokens != 1)
				{
					continue;
				}
				
				int id = Integer.parseInt(strTokenizer.nextToken());
				City city = cityDAO.getCityById(id);
				if(city != null) 
				{
					System.out.println("|  ID  |  Name  |  CountryCode  |  District  |  Population  |");
					System.out.print("| ");
					System.out.print(city.getId());
					System.out.print(" | ");
					System.out.print(city.getName());
					System.out.print(" | ");
					System.out.print(city.getCountryCode());
					System.out.print(" | ");
					System.out.print(city.getDistrict());
					System.out.print(" | ");
					System.out.print(city.getPopulation());
					System.out.println(" | ");
				}
				else 
				{
					System.out.println("|  ID  |  Name  |  CountryCode  |  District  |  Population  |");
					System.out.println("要删除的记录不存在");
				}
				
			} 
			else if (cmd.equals("3")) 
			{
				StringTokenizer strTokenizer = new StringTokenizer(query, "#");
				int countTokens = strTokenizer.countTokens();
				System.out.println( "‘#’符号的总数量: " + countTokens);
				if(countTokens != 1)
				{
					continue;
				}
				int id = Integer.parseInt(strTokenizer.nextToken());
				int rowCount = cityDAO.deleteCityById(id);
				System.out.println("成功删除了" + rowCount + "条记录");
			} 
			else if(cmd.equals("4")) 
			{
				StringTokenizer strTokenizer = new StringTokenizer(query, "#");
				int countTokens = strTokenizer.countTokens();
				System.out.println( "‘#’符号的总数量: " + countTokens);
				if(countTokens != 4)
				{
					continue;
				}
				City city = new City();
				city.setName(strTokenizer.nextToken());
				city.setCountryCode(strTokenizer.nextToken());
				city.setDistrict(strTokenizer.nextToken());
				city.setPopulation(Integer.parseInt(strTokenizer.nextToken()));
				int rowCount = cityDAO.addCity(city);
				System.out.println("成功插入了" + rowCount + "条记录");
			} 
			else if(cmd.equals("5")) 
			{
				StringTokenizer strTokenizer = new StringTokenizer(query, "#");
				int countTokens = strTokenizer.countTokens();
				System.out.println( "‘#’符号的总数量: " + countTokens);
				if(countTokens != 5)
				{
					continue;
				}
				City city = new City();
				city.setId(Integer.parseInt(strTokenizer.nextToken()));
				city.setName(strTokenizer.nextToken());
				city.setCountryCode(strTokenizer.nextToken());
				city.setDistrict(strTokenizer.nextToken());
				city.setPopulation(Integer.parseInt(strTokenizer.nextToken()));
				int rowCount = cityDAO.updateCity(city);
				System.out.println("成功更新了" + rowCount + "条记录");
			} 
			else if (cmd.equals("6")) 
			{
				System.out.println("退出主程序！");
				System.exit(0);
			}
			else 
			{
				System.out.println("指令代码输入错误，请重新输入！");
			}
		}
	}



}
