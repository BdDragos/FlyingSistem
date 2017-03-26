package observer;

/**
 * Created by Dragos on 3/21/2017.
 */

public interface Observable<E> {

    void addObserver(Observer<E> o);

    void removeObserver(Observer<E> o);

    void notifyObservers();
}
