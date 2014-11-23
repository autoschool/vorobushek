package ru.qatools.school.vorobushek.service;

import com.squareup.okhttp.*;
import org.flywaydb.core.Flyway;
import org.javalite.activejdbc.Base;

import org.javalite.activejdbc.LazyList;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.qatools.school.vorobushek.models.User;
import ru.qatools.school.vorobushek.models.UserContext;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

import static java.lang.String.format;
import static java.nio.file.Files.createTempDirectory;


/**
 * eroshenkoam
 * 15/11/14
 */
@Provider
public class DatabaseProvider implements ContainerRequestFilter {

    public static final String YANDEX_CLIEND_ID;
    public static final String YANDEX_CLIEND_SECRET;


    private static final String DBUSER = "sa";
    private final static Logger logger = LoggerFactory.getLogger(DatabaseProvider.class);
    private static String dbUrl;
    private final static String USER_CONTEXT_ATTRIBUTE_NAME;


    static {
        try {
            dbUrl = format("jdbc:h2:file:%s/%s,user=%s", getDbPath(), getDbName(), DBUSER);
            logger.info(format("Starting embedded database with url '%s' ...", dbUrl));
            openConnection();
            Flyway flyway = new Flyway();
            flyway.setDataSource(dbUrl, DBUSER, null);
            flyway.migrate();
        } catch (Exception e) {
            logger.error("Failed to start embedded database", e);
        }
        
        YANDEX_CLIEND_ID = "17c735ef06644350b6b9fabc0ae467ed";
        YANDEX_CLIEND_SECRET = "91928eb427e54e84a09d57b7f7eeda89";
        USER_CONTEXT_ATTRIBUTE_NAME = "userContext";
    }

    public static void openConnection() {
        if (!Base.hasConnection()) {
            Base.open(org.h2.Driver.class.getName(), dbUrl, DBUSER, "");
        }
    }

    public static Logger getLogger(){
        return logger;
    }

    private static String getDbName() {
        return getProperty("db.name", "default");
    }

    private static String getDbPath() throws IOException {
        return getProperty("db.path", createTempDirectory("blog").toAbsolutePath().toString());
    }

    private static String getProperty(String key, String defaultValue) {
        final String value = System.getProperty(key);
        return (value == null) ? defaultValue : value;
    }

    public static String getYandexToken(String responseCode){

        MediaType bodyTypeForToken = MediaType.parse("application/x-www-form-urlencoded");

        String bodyTextForToken ="grant_type=authorization_code" +
                "&code=" + responseCode +
                "&client_id=" + DatabaseProvider.YANDEX_CLIEND_ID +
                "&client_secret=" + DatabaseProvider.YANDEX_CLIEND_SECRET;

        OkHttpClient client = new OkHttpClient();
        RequestBody bodyForToken = RequestBody.create(bodyTypeForToken, bodyTextForToken);
        Request requestForToken = new Request.Builder()
                .url("http://oauth.yandex.ru/token")
                .post(bodyForToken)
                .build();

        Response responseForToken = null;

        try{ responseForToken = client.newCall(requestForToken).execute(); }
        catch (IOException e) { DatabaseProvider.getLogger().info(e.getMessage()); }

        if (responseForToken == null) return null;


        String responseForTokenString = null;

        try { responseForTokenString = responseForToken.body().string(); }
        catch (IOException e) { DatabaseProvider.getLogger().info(e.getMessage()); }


        JSONObject responseJson = null;

        try { responseJson = new JSONObject(responseForTokenString); }
        catch (JSONException e) { DatabaseProvider.getLogger().info(e.getMessage()); }

        if (responseJson == null) return  null;


        String accessToken = null;

        try { accessToken = responseJson.getString("access_token"); }
        catch (JSONException e) { DatabaseProvider.getLogger().info(e.getMessage()); }

        return accessToken;
    }

    public static User getYandexUser(String access_token) {

        if (access_token == null || access_token.isEmpty()) return  null;

        String url = "https://login.yandex.ru/info?" +
                "format=json" +
                "&oauth_token="+access_token;

        OkHttpClient client = new OkHttpClient();
        Request requestPassport = new Request.Builder()
                .url(url)
                .build();

        Response responsePassport = null;

        try{responsePassport = client.newCall(requestPassport).execute();}
        catch (IOException e) { DatabaseProvider.getLogger().info(e.getMessage()); }

        if (responsePassport == null) return null;


        String passportJsonString = null;

        try { passportJsonString = responsePassport.body().string(); }
        catch (IOException e) { DatabaseProvider.getLogger().info(e.getMessage()); }

        if (passportJsonString == null) return null;


        JSONObject passportJson = null;

        try { passportJson = new JSONObject(passportJsonString); }
        catch (JSONException e) { DatabaseProvider.getLogger().info(e.getMessage()); }

        if (passportJson == null) return null;


        String login = null;
        String email = null;
        String displayName = null;

        try{
            login = passportJson.getString("login");
            email = passportJson.getString("default_email");
            displayName = passportJson.getString("display_name");
        }
        catch (JSONException e) { DatabaseProvider.getLogger().info(e.getMessage()); }

        if (login == null || email == null || displayName == null) return null;

        LazyList<User> users = User.findBySQL(format("select * from users where login='%s' and email='%s' and displayName='%s'", login, email, displayName));
        if (users.isEmpty()){
            User user = new User();
            user.setLogin(login);
            user.setEmail(email);
            user.setDisplayName(displayName);
            user.saveIt();
            return user;
        }

        return users.get(0);

    }

    public static UserContext getUserContext(HttpServletRequest httpRequest){
        UserContext userContext = (UserContext) httpRequest
                .getSession()
                .getAttribute(USER_CONTEXT_ATTRIBUTE_NAME);

        if (userContext == null){
            userContext = new UserContext();
            setUserContext(httpRequest, userContext);
        }

        return userContext;
    }

    public static void  setUserContext(HttpServletRequest httpRequest, UserContext userContext){
        httpRequest.getSession().setAttribute(USER_CONTEXT_ATTRIBUTE_NAME, userContext);
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        openConnection();
    }


}
