package pl.sevet.myapplication.utils;

import pl.sevet.myapplication.model.Mode;
import pl.sevet.myapplication.model.Role;
import pl.sevet.myapplication.model.Transaction;
import pl.sevet.myapplication.model.history.HistoryItem;
import pl.sevet.myapplication.model.items.Item;
import pl.sevet.myapplication.model.items.ItemGroup;
import pl.sevet.myapplication.model.items.ItemType;
import pl.sevet.myapplication.model.locations.Location;
import pl.sevet.myapplication.model.users.User;
import pl.sevet.myapplication.model.users.UserParams;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SLib {
    private static ArrayList<User> users;
    private static UserParams userParams;
    private static User userDetails;
    private static Map<Long,List<Long>> usersAccess;
    private static Map<Long,List<Long>> usersAccessCopy;
    private static Role role;
    private static Mode mode;
    private static HttpURLConnection connection;
    private static RequestApi requestApi;
    private static ArrayList<Location> locations;
    private static ArrayList<Item> items;
    private static ArrayList<ItemType> itemTypes;
    private static ArrayList<Transaction> transactions;
    private static ArrayList<HistoryItem> transactionHistory;
    private static User selectedUser = null;
    private static ItemType selectedItemType = null;
    private static ItemGroup selectedItemGroup = null;
    private static Location selectedLocation;
    private static ArrayList<Item> selectedItems;
    private static ArrayList<ItemGroup> itemGroups;
    private static Map<Integer, List<Transaction>> transactionMap;

    public static Map<Long, List<Long>> getUsersAccessCopy() {
        return usersAccessCopy;
    }

    public static void setUsersAccessCopy(Map<Long, List<Long>> usersAccessCopy) {
        SLib.usersAccessCopy = usersAccessCopy;
    }

    public static Map<Long, List<Long>> getUsersAccess() {
        return usersAccess;
    }

    public static void setUsersAccess(Map<Long, List<Long>> usersAccess) {
        SLib.usersAccess = usersAccess;
    }

    public static Location getSelectedLocation() {
        return selectedLocation;
    }

    public static void setSelectedLocation(Location selectedLocation) {
        SLib.selectedLocation = selectedLocation;
    }

    public static ArrayList<Item> getSelectedItems() {
        return selectedItems;
    }

    public static void setSelectedItems(ArrayList<Item> selectedItems) {
        SLib.selectedItems = selectedItems;
    }

    public static HttpURLConnection getConnection() {
        return connection;
    }

    public static void setConnection(HttpURLConnection connection) {
        SLib.connection = connection;
    }

    public static UserParams getUserParams() {
        return userParams;
    }

    public static void setUserParams(UserParams userParams) {
        SLib.userParams = userParams;
    }

    public static User getUserDetails() {
        return userDetails;
    }

    public static void setUserDetails(User userDetails) {
        SLib.userDetails = userDetails;
        if(userDetails.isAdmin())
            setRole(Role.ADMIN);
        else
            setRole(Role.USER);
    }

    public static Role getRole() {
        return role;
    }

    public static void setRole(Role role) {
        SLib.role = role;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<User> users) {
        SLib.users = users;
    }

    public static ArrayList<Location> getLocations() {
        return locations;
    }

    public static void setLocations(ArrayList<Location> locations) {
        SLib.locations = locations;
    }

    public static ArrayList<Item> getItems() {
        return items;
    }

    public static void setItems(ArrayList<Item> items) {
        SLib.items = items;
    }

    public static ArrayList<ItemType> getItemTypes() {
        return itemTypes;
    }

    public static void setItemTypes(ArrayList<ItemType> itemTypes) {
        SLib.itemTypes = itemTypes;
    }

    public static ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public static void setTransactions(ArrayList<Transaction> transactions) {
        SLib.transactions = transactions;
    }

    public static RequestApi getRequestApi() {
        return requestApi;
    }

    public static void setRequestApi(RequestApi requestApi) {
        SLib.requestApi = requestApi;
    }

    public static Mode getMode() {
        return mode;
    }

    public static void setMode(Mode mode) {
        SLib.mode = mode;
    }

    public static User getSelectedUser() {
        return selectedUser;
    }

    public static void setSelectedUser(User selectedUser) {
        SLib.selectedUser = selectedUser;
    }

    public static ItemType getSelectedItemType() {
        return selectedItemType;
    }

    public static void setSelectedItemType(ItemType selectedItemType) {
        SLib.selectedItemType = selectedItemType;
    }

    public static Map<Integer, List<Transaction>> getTransactionMap() {
        return transactionMap;
    }

    public static void setTransactionMap(Map<Integer, List<Transaction>> transactionMap) { SLib.transactionMap = transactionMap; }

    public static ArrayList<ItemGroup> getItemGroups() {
        return itemGroups;
    }

    public static void setItemGroups(ArrayList<ItemGroup> itemGroups) {
        SLib.itemGroups = itemGroups;
    }

    public static ItemGroup getSelectedItemGroup() {
        return selectedItemGroup;
    }

    public static void setSelectedItemGroup(ItemGroup selectedItemGroup) {
        SLib.selectedItemGroup = selectedItemGroup;
    }

    public static ArrayList<HistoryItem> getTransactionHistory() { return transactionHistory; }

    public static void setTransactionHistory(ArrayList<HistoryItem> transactionHistory) { SLib.transactionHistory = transactionHistory; }
}
