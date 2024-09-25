import java.util.Observer;

    public interface Observable {
        /**
         * add an observer to the list of observers
         * @param o osservatore
         */
        void addObserver(Observer o);
        /**
         * notify all observers
         */
        void notifyObservers();
    }

