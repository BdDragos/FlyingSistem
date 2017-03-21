package Observer;

/**
 * Created by Dragos on 3/21/2017.
 */

public interface Observer<E>
{
    void update(Observable<E> observable);
}
