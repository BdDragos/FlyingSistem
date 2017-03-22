/**
 * Created by Dragos on 3/16/2017.
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import repository.FlightRepo;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.sql.SQLException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;
import javafx.scene.layout.AnchorPane;

@Configuration
public class Main extends Application
{
    @Bean
    private static void execute(String sql) throws SQLException
    {
        System.out.println("Executing sql " + sql);
        System.out.println(new FlightRepo().getAll());
    }
    @Bean
    public static void main(String[] args) throws ClassNotFoundException, SQLException
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws ClassNotFoundException, SQLException
    {
        LoginView(primaryStage);
    }

    public void authenticated(String perm)
    {
        MainView(perm);
    }

    private void LoginView(Stage primaryStage) throws ClassNotFoundException, SQLException
    {
        try
        {
            AnchorPane page;
            page = FXMLLoader.load(Main.class.getResource("LoginWindow.fxml"));
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login");
            primaryStage.show();
            execute("connection");

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    private void MainView(String perm)
    {

    }
}
