/**
 * Created by Dragos on 3/16/2017.
 */

package main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import repository.FlightRepo;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.*;
import java.sql.SQLException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javafx.scene.layout.AnchorPane;
import service.LoginService;
import java.net.URL;

@Configuration
public class Main extends Application
{
    private FXMLLoader loader;
    private FXMLLoader loader2;
    private Stage primaryStage;
    private AnchorPane rootLayout1;
    private TabPane rootLayout2;


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

    public void startserver()
    {
        LoginServer server = null;
        try
        {
            server = new LoginServer();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        server.runServer(this);
    }

    @Override
    public void start(Stage primaryStage) throws ClassNotFoundException, SQLException
    {
        this.primaryStage = primaryStage;
        loader = new FXMLLoader();
        loader2 = new FXMLLoader();
        startserver();
        //LoginView();
    }

    public void authenticated()
    {
        MainView();
    }

    private void LoginView() throws ClassNotFoundException, SQLException
    {
        try
        {
            String pathToFxml = "src/main/resources/LoginWindow.fxml";
            URL fxmlUrl = new File(pathToFxml).toURI().toURL();
            loader.setLocation(fxmlUrl);

            rootLayout1 = loader.load();
            Scene scene = new Scene(rootLayout1);
            primaryStage.setScene(scene);
            primaryStage.show();

            LoginService controller = loader.getController();
            controller.initManager(this);
        }

        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    private void MainView()
    {
        try
        {
            String pathToFxml = "src/main/resources/MainWindow.fxml";
            URL fxmlUrl = new File(pathToFxml).toURI().toURL();
            loader2.setLocation(fxmlUrl);

            rootLayout2 = loader2.load();
            Scene scene = new Scene(rootLayout2);
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

}
