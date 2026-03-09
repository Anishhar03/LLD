package design_patterns;

import java.util.*;

/*
    ============================================================
    OBSERVER DESIGN PATTERN - COMPLETE WORKFLOW IN ONE FILE
    ============================================================

    SCENARIO:
    We are designing a system where users want to be notified
    when a product (iPhone) comes back in stock.

    Instead of making users constantly check the stock,
    we allow them to SUBSCRIBE for notifications.

    When stock becomes available, the system automatically
    NOTIFIES all subscribed users.

    This is exactly what the OBSERVER PATTERN solves.

    ------------------------------------------------------------
    TERMINOLOGY
    ------------------------------------------------------------

    Observable (Subject)
        -> The object whose state is being observed.
        -> Example: iPhone stock inventory.

    Observer
        -> Objects interested in changes in the observable.
        -> Example: Users waiting for stock notification.

    Concrete Observable
        -> Implementation of observable.
        -> Maintains list of observers.

    Concrete Observer
        -> Implementation of observer.
        -> Defines what to do when update happens.

    ------------------------------------------------------------
    WORKFLOW SUMMARY
    ------------------------------------------------------------

    1. Create Observable (iPhone inventory system)
    2. Create Observers (Users)
    3. Observers subscribe to Observable
    4. Product stock becomes available
    5. Observable notifies all observers
    6. Observers perform their actions (email, SMS etc)

*/

public class observer_design_pattern {

    /*
        ====================================================
        STEP 1 : OBSERVER INTERFACE
        ====================================================

        This interface represents all observers.

        Any class that wants notifications MUST implement it.

        Example observers:
        - Email notification service
        - Mobile notification service
        - WhatsApp notification service
        - Push notification service
    */
    interface NotificationAlertObserver {

        /*
            update() method is triggered by observable.

            When observable detects a state change
            (like stock becoming available),
            it will call update() on all observers.
        */
        void update();
    }



    /*
        ====================================================
        STEP 2 : OBSERVABLE INTERFACE
        ====================================================

        This interface represents the Subject (Observable).

        The observable maintains a list of observers and
        provides methods to manage them.

        Responsibilities:
        1. Add observer
        2. Remove observer
        3. Notify observers
        4. Manage internal state (stock)
    */
    interface StockObservable {

        /*
            Used when a new observer subscribes.
            Example: User clicks "Notify Me".
        */
        void add(NotificationAlertObserver observer);

        /*
            Used when observer unsubscribes.
            Example: User cancels notification.
        */
        void remove(NotificationAlertObserver observer);

        /*
            This method will notify ALL observers.

            Observable will loop through observer list
            and call update() on each observer.
        */
        void notifySubscribers();

        /*
            Updates stock count when new products arrive.
        */
        void setStockCount(int newStockAdded);

        /*
            Returns current stock count.
        */
        int getStockCount();
    }



    /*
        ====================================================
        STEP 3 : CONCRETE OBSERVABLE
        ====================================================

        This class implements the StockObservable interface.

        It represents the real product inventory.

        Responsibilities:
        - Store list of observers
        - Track stock
        - Notify observers when stock becomes available
    */
    static class IphoneObservableImpl implements StockObservable {

        /*
            List storing all observers.

            Example:
            [observer1, observer2, observer3]

            When stock becomes available,
            we iterate through this list and notify everyone.
        */
        List<NotificationAlertObserver> observers = new ArrayList<>();


        /*
            Current stock count.

            Example:
            0  -> Out of stock
            10 -> 10 items available
        */
        int stockCount = 0;



        /*
            Method used when observer subscribes.
        */
        @Override
        public void add(NotificationAlertObserver observer) {

            observers.add(observer);
        }



        /*
            Method used when observer unsubscribes.
        */
        @Override
        public void remove(NotificationAlertObserver observer) {

            observers.remove(observer);
        }



        /*
            This method notifies all observers.

            Workflow:
            Loop through all observers
            -> Call update() method
        */
        @Override
        public void notifySubscribers() {

            for(NotificationAlertObserver observer : observers) {

                observer.update();
            }
        }



        /*
            Method used to update stock.

            Example:
            setStockCount(10)

            Meaning:
            10 new iPhones arrived.

            Logic:
            If stock was previously 0,
            notify all observers waiting for stock.
        */
        @Override
        public void setStockCount(int newStockAdded) {

            /*
                If stockCount == 0,
                product was previously out of stock.

                So users waiting for notification
                should be notified now.
            */
            if(stockCount == 0) {

                notifySubscribers();
            }

            /*
                Increase stock count
            */
            stockCount += newStockAdded;
        }



        /*
            Returns current stock value.
        */
        @Override
        public int getStockCount() {

            return stockCount;
        }
    }




    /*
        ====================================================
        STEP 4 : EMAIL OBSERVER
        ====================================================

        Concrete implementation of observer.

        Represents users who want EMAIL notifications.
    */
    static class EmailAlertObserverImpl implements NotificationAlertObserver {

        String emailId;
        StockObservable observable;



        /*
            Constructor initializes observer.

            observable reference is stored so that
            observer can query observable if needed.
        */
        EmailAlertObserverImpl(String emailId, StockObservable observable) {

            this.emailId = emailId;
            this.observable = observable;
        }



        /*
            This method executes when observable
            sends notification.
        */
        @Override
        public void update() {

            sendMail(emailId, "Product is back in stock!");
        }



        /*
            Simulates sending email.
        */
        void sendMail(String emailId, String msg) {

            System.out.println("EMAIL SENT TO: " + emailId + " -> " + msg);
        }
    }




    /*
        ====================================================
        STEP 5 : MOBILE OBSERVER
        ====================================================

        Another observer implementation.

        Represents users who want mobile alerts.
    */
    static class MobileAlertObserverImpl implements NotificationAlertObserver {

        String userName;
        StockObservable observable;



        MobileAlertObserverImpl(String userName, StockObservable observable) {

            this.userName = userName;
            this.observable = observable;
        }



        /*
            Called when observable notifies observers.
        */
        @Override
        public void update() {

            sendMsg(userName, "Product available now!");
        }



        /*
            Simulates mobile notification.
        */
        void sendMsg(String userName, String msg) {

            System.out.println("MOBILE ALERT FOR: " + userName + " -> " + msg);
        }
    }




    /*
        ====================================================
        STEP 6 : MAIN METHOD (PROGRAM EXECUTION)
        ====================================================
    */
    public static void main(String[] args) {

        /*
            STEP 1:
            Create Observable object.

            This represents iPhone inventory system.
        */
        StockObservable iphoneStockObservable = new IphoneObservableImpl();



        /*
            STEP 2:
            Create observers.

            These represent users waiting for notification.
        */
        NotificationAlertObserver observer1 =
                new EmailAlertObserverImpl("abc@gmail.com", iphoneStockObservable);

        NotificationAlertObserver observer2 =
                new EmailAlertObserverImpl("xyz@gmail.com", iphoneStockObservable);

        NotificationAlertObserver observer3 =
                new MobileAlertObserverImpl("Rahul", iphoneStockObservable);



        /*
            STEP 3:
            Observers subscribe to observable.
        */
        iphoneStockObservable.add(observer1);
        iphoneStockObservable.add(observer2);
        iphoneStockObservable.add(observer3);



        /*
            Current system state:

            Observers = [observer1, observer2, observer3]
            Stock = 0 (Out of stock)
        */



        /*
            STEP 4:
            Stock arrives in warehouse.

            This triggers notification system.
        */
        iphoneStockObservable.setStockCount(10);



        /*
            INTERNAL WORKFLOW AFTER THIS CALL:

            setStockCount(10)
                |
                v
            check if stockCount == 0
                |
                v
            YES
                |
                v
            notifySubscribers()
                |
                v
            Loop through observers list
                |
                v
            observer1.update() -> Email sent
            observer2.update() -> Email sent
            observer3.update() -> Mobile alert sent
        */
    }
}