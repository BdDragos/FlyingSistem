package service;

/**
 * Created by Dragos on 3/23/2017.
 */

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import repository.UserManager;
import main.Main;
public class LoginService
{
    private UserManager manager;
    @FXML private TextField user;
    @FXML private PasswordField password;
    @FXML private Button loginButton;
    @FXML private Button modificaButton;

    public void initialize()
    {
        password.setPromptText("Your password");
        user.setPromptText("Your username");
        manager=new UserManager();
        modificaButton.setVisible(true);
    }

    public void initManager(final Main loginManager)
    {
        loginButton.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                String sessionID = login();
                if (sessionID.compareTo("error")!=0)
                {
                    loginManager.authenticated();
                }
                else
                {
                    showErrorMessage("Nume invalid sau parola invalida");
                    user.setText("");
                    password.setText("");
                }
            }
        });

    }

    @FXML private void registerUser()
    {
        String username = user.getText();
        String passwords = password.getText();
        if (username == null)
        {
            showErrorMessage("Dati un username");
        }
        else if (passwords == null)
        {
            showErrorMessage("Dati o parola");
        }
        else
        {
            int i=manager.addUser(username,passwords);
            if (i==1)
                showMessage(Alert.AlertType.CONFIRMATION);
            else
                showErrorMessage("User-ul deja exista");
        }
    }

    private String login()
    {
        String username = user.getText();
        String passwords = password.getText();
        if (manager.autentificare(username,passwords)==1)
        {
            return "success";
        }
        else
            return "error";
    }


    private static void showMessage(Alert.AlertType type)
    {
        Alert message=new Alert(type);
        message.setHeaderText("Succes");
        message.setContentText("Succes");
        message.showAndWait();
    }

    private static void showErrorMessage(String text)
    {
        Alert message=new Alert(Alert.AlertType.ERROR);
        message.setTitle("Mesaj eroare");
        message.setContentText(text);
        message.showAndWait();
    }
}
