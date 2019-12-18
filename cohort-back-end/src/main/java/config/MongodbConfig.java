package config;

public class MongodbConfig{
    public static enum authTypes {
        noAuth, userPass, sha
    }

    ////////////////////////////////////////////////
    // Variables
    ///////////////////////////////////////////////
    private String username;
    private String password;
    private authTypes authType;
    private String authDatabase;
    private String port;
    private String url;
    private String databaseName;
    private String collectionName;

    ////////////////////////////////////////////////
    // Getters
    ///////////////////////////////////////////////
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public authTypes getAuthType() {
        return authType;
    }

    public String getAuthDatabase() {
        return authDatabase;
    }

    public String getPort() {
        return port;
    }

    public String getUrl() {
        return url;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    ////////////////////////////////////////////////
    // Constructors
    ///////////////////////////////////////////////
    public MongodbConfig(authTypes authType, String username, String password, String authDatabase, String url, String port, String databaseName, String collectionName) {
        this.username = username;
        this.password = password;
        this.authType = authType;
        this.authDatabase = authDatabase;
        this.port = port;
        this.url = url;
        this.databaseName = databaseName;
        this.collectionName = collectionName;
    }

    public MongodbConfig(String url, String port, String databaseName, String collectionName) {
        this.authType = authTypes.noAuth;
        this.port = port;
        this.url = url;
        this.databaseName = databaseName;
        this.collectionName = collectionName;
    }

    ////////////////////////////////////////////////
    // static methods
    ///////////////////////////////////////////////
    public static authTypes stringToEnum (String typeString) throws InvalidTypeException{
        if(typeString.equals("none"))
            return authTypes.noAuth;
        else if(typeString.equals("user/pass"))
            return authTypes.userPass;
        throw new InvalidTypeException(typeString + "is not a valid authentication type");
    }

    public static String EnumToString (authTypes type) throws InvalidTypeException{
        switch (type){
            case sha:
                return null;
            case noAuth:
                return "none";
            case userPass:
                return null;
            default:
                throw new InvalidTypeException(type.name() + "is not a valid authentication type");
        }
    }
    public static class InvalidTypeException extends Exception{
        InvalidTypeException(String s){
            super(s);
        }
    }
}
