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
import java.io.InputStream;
import java.util.Properties;

import static java.lang.String.format;
import static java.nio.file.Files.createTempDirectory;


/**
 * eroshenkoam
 * 15/11/14
 */
@Provider
public class DatabaseProvider implements ContainerRequestFilter {

    private static String YANDEX_CLIEND_ID = null;
    private static String YANDEX_CLIEND_SECRET = null;
    private static String YANDEX_SPEECHKIT_KEY = null;
    private static String PROJECT_NUBER_BUILD  = null;

    private static String DBDRIVER = null;
    private static String DBUSER = null;
    private static String DBPASS = null;
    private static String DBURL = null;
    private static String USER_CONTEXT_ATTRIBUTE_NAME = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseProvider.class);
    private static String YANDEX_TOKEN_URL = null;

    static {
        Properties prop = new Properties();

        try(InputStream inputStream = DatabaseProvider.class.getResourceAsStream("/application.properties")) {
            prop.load(inputStream);
        }
        catch ( IOException e ) {
            LOGGER.error( e.getMessage(), e );
        }

        try {
            YANDEX_TOKEN_URL = "http://oauth.yandex.ru/token";
            USER_CONTEXT_ATTRIBUTE_NAME = "userContext";
            DBURL = prop.getProperty("dbUrl");
            DBUSER = prop.getProperty("dbUser");
            DBPASS = prop.getProperty("dbPass");
            DBDRIVER = prop.getProperty("dbDriver");
            YANDEX_CLIEND_ID = prop.getProperty("yandexCliendId");
            YANDEX_CLIEND_SECRET = prop.getProperty("yandexCliendSecret");
            YANDEX_SPEECHKIT_KEY = prop.getProperty("yandexSpeechKitKey");
            PROJECT_NUBER_BUILD = prop.getProperty("build.number");

            LOGGER.info(format("Starting mysql database with url '%s' ...", DBURL));
            openConnection();
            Flyway flyway = new Flyway();
            flyway.setDataSource(DBURL, DBUSER, DBPASS);
            flyway.migrate();
        } catch (Exception e) {
            LOGGER.error("Failed to start embedded database", e);
        }
        

    }

    public static String getProperty(String key, String defaultValue) {
        final String value = System.getProperty(key);
        return (value == null) ? defaultValue : value;
    }

    private static String getJsonAttribute(String jsonMessage, String attributeName){

        String attribute = "";

        JSONObject responseJson = null;

        try {
            responseJson = new JSONObject(jsonMessage);
        } catch (JSONException e) {
            LOGGER.error("Parse JSON message to get attribute: ", e.getMessage());
        }

        if (responseJson == null) {
            return  attribute;
        }

        try {
            if (responseJson.has(attributeName)){
                attribute = responseJson.getString(attributeName);
            }
        } catch (JSONException e) {
            LOGGER.error("Try to get attribute from JSON message: ", e.getMessage());
        }

        return attribute;

    }

    private static String executeRequest(Request request){

        String responseString = "";

        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);

        Response response = null;

        try{
            response = call.execute();
        } catch (IOException e) {
            LOGGER.error("Try to get response by executing http request: ", e.getMessage());
        }

        if (response == null){
            return responseString;
        }

        try {
            responseString = response.body().string();
        } catch (IOException e) {
            LOGGER.error("Try to read response: ", e.getMessage());
        }

        return responseString;
    }

    public static void openConnection() {
        if (!Base.hasConnection()) {
            Base.open(DBDRIVER, DBURL, DBUSER, DBPASS);
        }
    }

    public static Logger getLogger(){
        return LOGGER;
    }

    public static String getYandexToken(String responseCode){

        MediaType bodyTypeForToken = MediaType.parse("application/x-www-form-urlencoded");

        String bodyTextForToken = String.format("grant_type=authorization_code&code=%s&client_id=%s&client_secret=%s"
                , responseCode
                , YANDEX_CLIEND_ID
                , YANDEX_CLIEND_SECRET);

        RequestBody bodyForToken = RequestBody.create(bodyTypeForToken, bodyTextForToken);
        Request requestForToken = new Request.Builder()
                .url(YANDEX_TOKEN_URL)
                .post(bodyForToken)
                .build();

        String responseForTokenString = executeRequest(requestForToken);

        return getJsonAttribute(responseForTokenString, "access_token");
    }

    public static User getYandexUser(String accessToken) {

        String url = String.format("https://login.yandex.ru/info?format=json&oauth_token=%s", accessToken);

        Request requestPassport = new Request.Builder()
                .url(url)
                .build();

        String passportJsonString = executeRequest(requestPassport);

        String login = getJsonAttribute(passportJsonString, "login");
        String email = getJsonAttribute(passportJsonString, "default_email");
        String displayName = getJsonAttribute(passportJsonString, "display_name");
        String id  =   getJsonAttribute(passportJsonString, "id");

        if (login.isEmpty() || email.isEmpty() || displayName.isEmpty()) {
            return null;
        }

        LazyList<User> users = User.findBySQL(format("select * from users where login='%s' and email='%s' and displayName='%s'", login, email, displayName));

        if (!users.isEmpty()){
            return users.get(0);
        }

        User user = new User();
        user.setLogin(login);
        user.setEmail(email);
        user.setDisplayName(displayName);
        user.setAvatar("http://avatars.yandex.net/get-yapic/" + id + "/islands-middle");
        user.saveIt();

        return user;

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

    public static String getYandexSpeechKitKey(){ return YANDEX_SPEECHKIT_KEY; }

    public static String getYandexClientId() { return YANDEX_CLIEND_ID; }

    public static String getProjectBuildNumber() { return PROJECT_NUBER_BUILD; }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        openConnection();
    }

}