/**
 * Created by Dragos on 3/16/2017.
 */
import javafx.application.Application;
import repository.FlightRepo;
import javafx.stage.Stage;
import java.sql.SQLException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Main extends Application
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
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {

    }
}
