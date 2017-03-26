package repository;

/**
 * Created by Dragos on 3/23/2017.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import model.User ;
import javafx.scene.control.Alert;


public class UserManager
{

    public UserManager()
    {
        loadFromF();
    }

    private HashMap<String,User> users=new HashMap<String,User>();

    private void loadFromF()
    {
        try {
            BufferedReader br=new BufferedReader(new FileReader("src/main/resources/User.txt"));
            {
                String line=br.readLine();
                while (line!=null)
                {
                    String[] elems=line.split("[|]");
                    String usr=elems[0];
                    users.put(usr,new User(elems[0],elems[1]));
                    line=br.readLine();
                }
            }
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public int addUser(String usr,String psd)
    {
        if (!users.containsKey(usr))
        {
            users.put(usr,new User(usr,psd));
            saveToFile();
            return 1;
        }
        else
            return 0;
    }

    private void saveToFile()
    {
        File log = new File("User.txt");
        try
        {
            Iterator<Entry<String, User>> it = users.entrySet().iterator();

            PrintWriter out = new PrintWriter(new FileWriter(log));
            while (it.hasNext())
            {
                Map.Entry<String,User> pair = (Map.Entry<String,User>)it.next();
                out.append(pair.getValue().getUser()+ "|"+ pair.getValue().getParola());
                out.append("\n");
                it.remove();
            }
            out.close();

        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }

        loadFromF();

    }

    public int autentificare(String username, String passwords)
    {

        User value = users.get(username);
        if (value != null)
            if (passwords.compareTo(value.getParola()) == 0)
                return 1;
            else
                return 0;
        else
        {
            return 0;
        }
    }


    static void showMessage(Alert.AlertType type, String header, String text)
    {
        Alert message=new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.showAndWait();
    }

    static void showErrorMessage(String text)
    {
        Alert message=new Alert(Alert.AlertType.ERROR);
        message.setTitle("Mesaj eroare");
        message.setContentText(text);
        message.showAndWait();
    }

}
