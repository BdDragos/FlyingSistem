/**
 * Created by Dragos on 3/16/2017.
 */
import repository.FlightRepo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Main
{
    @Bean
    public static void execute(String sql) throws SQLException
    {
        System.out.println("Executing sql " + sql);
        System.out.println(new FlightRepo().getAll());
    }
    @Bean
    public static void main(String[] args)  throws ClassNotFoundException, SQLException
    {
        execute("done");
    }
}
