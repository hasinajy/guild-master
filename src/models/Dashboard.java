package models;

import java.sql.SQLException;
import java.util.List;

public class Dashboard {
    private int playersCount;
    private int storedItems;
    private int transactionsCount;

    /* ------------------------------ Constructors ------------------------------ */
    public Dashboard() {
    }

    public Dashboard(int playersCount, int storedItems, int transactionsCount) {
        this.setPlayersCount(playersCount);
        this.setStoredItems(storedItems);
        this.setTransactionsCount(transactionsCount);
    }

    /* --------------------------- Getters and setters -------------------------- */
    public int getPlayersCount() {
        return playersCount;
    }

    public void setPlayersCount(int playersCount) {
        this.playersCount = playersCount;
    }

    public int getStoredItems() {
        return storedItems;
    }

    public void setStoredItems(int storedItems) {
        this.storedItems = storedItems;
    }

    public int getTransactionsCount() {
        return transactionsCount;
    }

    public void setTransactionsCount(int transactionsCount) {
        this.transactionsCount = transactionsCount;
    }

    /* ---------------------------- Database methods ---------------------------- */
    public static Dashboard getData() throws ClassNotFoundException, SQLException {
        Dashboard dashboard = new Dashboard();

        List<Player> playerList = Player.getAll();
        List<Inventory> inventoryList = Inventory.getAll();
        List<Transaction> transactionList = Transaction.getAll();

        dashboard.setPlayersCount(playerList.size());
        dashboard.setStoredItems(inventoryList.size());
        dashboard.setTransactionsCount(transactionList.size());

        return dashboard;
    }
}
