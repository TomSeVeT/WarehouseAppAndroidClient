package pl.sevet.myapplication.utils;

public class Constants {
    public static final String SERVER               = "http://sevet.zapto.org:8080/cb_restapi_war_exploded";
    public static final String LOGIN                = SERVER+"/login";
    public static final String CREATE_LOCATION      = SERVER+"/location/create";
    public static final String ALL_LOCATIONS        = SERVER+"/location/getAll";
    public static final String USER_LOCATIONS       = SERVER+"/location/getByUserId?userId=";
    public static final String CREATE_USER          = SERVER+"/users/create";
    public static final String ALL_USERS            = SERVER+"/users/getAll";
    public static final String UPDATE_USER_DESC     = SERVER+"/users/updateDesc";
    public static final String DELETE_USER          = SERVER+"/users/delete";
    public static final String CREATE_TYPE          = SERVER+"/item/createType";
    public static final String ITEM_TYPES           = SERVER+"/item/getTypes";
    public static final String WH_ITEMS             = SERVER+"/item/getAllNonEmptyByLocationId?locationId=0";
    public static final String LOCATION_ITEMS       = SERVER+"/item/getAllNonEmptyByLocationId?locationId=";
    public static final String DELIVERY             = SERVER+"/transaction/delivery";
    public static final String MOVE                 = SERVER+"/transaction/move";
    public static final String USE_ITEM             = SERVER+"/transaction/use";
    public static final String ALL_ACCESSES         = SERVER+"/access/all";
    public static final String ACCESS_CREATE        = SERVER+"/access/create";
    public static final String ACCESS_DELETE        = SERVER+"/access/delete";
    public static final String ITEM_GROUPS          = SERVER+"/itemGroup/getAll";
    public static final String CREATE_ITEM_GROUP    = SERVER+"/itemGroup/create";
    public static final String HISTORY_USAGE        = SERVER+"/transaction/history/usage";
}
