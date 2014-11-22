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

    public static final String yandexCliendId = "17c735ef06644350b6b9fabc0ae467ed";
    public static final String yandexCliendSecret = "91928eb427e54e84a09d57b7f7eeda89";

    private static final String DBUSER = "sa";
    public final static Logger logger = LoggerFactory.getLogger(DatabaseProvider.class);
    private static String dbUrl;

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
            System.exit(-1);
        }
    }

    public static void openConnection() {
        if (!Base.hasConnection()) {
            Base.open(org.h2.Driver.class.getName(), dbUrl, DBUSER, "");
        }
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

    public static String GetYandexToken(String responseCode) throws IOException, JSONException{

        MediaType bodyTypeForToken = MediaType.parse("application/x-www-form-urlencoded");

        String bodyTextForToken ="grant_type=authorization_code" +
                "&code=" + responseCode +
                "&client_id=" + DatabaseProvider.yandexCliendId +
                "&client_secret=" + DatabaseProvider.yandexCliendSecret;

        OkHttpClient client = new OkHttpClient();
        RequestBody bodyForToken = RequestBody.create(bodyTypeForToken, bodyTextForToken);
        Request requestForToken = new Request.Builder()
                .url("http://oauth.yandex.ru/token")
                .post(bodyForToken)
                .build();
        Response responseForToken = client.newCall(requestForToken).execute();
        String responseForTokenString = responseForToken.body().string();

        JSONObject responseJson = new JSONObject(responseForTokenString);

        return responseJson.has("access_token") ? responseJson.getString("access_token") : null;
    }

    public static User GetYandexUser(String access_token) throws IOException, JSONException {

        if (access_token == null || access_token.isEmpty()){
            LazyList<User> users = User.findBySQL("select * from users where displayName='Guest'");
            return users.get(0);
        }

        String url = "https://login.yandex.ru/info?" +
                "format=json" +
                "&oauth_token="+access_token;

        OkHttpClient client = new OkHttpClient();
        Request requestPassport = new Request.Builder()
                .url(url)
                .build();
        Response responsePassport = client.newCall(requestPassport).execute();

        String passport = responsePassport.body().string();

        JSONObject passportJson = new JSONObject(passport);

        String login = passportJson.getString("login");
        String email = passportJson.getString("default_email");
        String displayName = passportJson.getString("display_name");


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

    @Override
    public void filter(ContainerRequestContext requestContext) {
        openConnection();
    }


}
