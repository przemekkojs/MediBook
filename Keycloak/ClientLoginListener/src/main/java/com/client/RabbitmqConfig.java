package com.client;

import org.jboss.logging.Logger;
import org.keycloak.Config.Scope;
import org.keycloak.events.Event;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;
import org.keycloak.util.JsonSerialization;

import java.util.Locale;
import java.util.regex.Pattern;

public class RabbitmqConfig {
    private static final Logger log = Logger.getLogger(RabbitmqConfig.class.getName());
    public static final String ROUTING_KEY_PREFIX = "KK.EVENT";
    private static final Pattern SPECIAL_CHARACTERS = Pattern.compile("[^*#a-zA-Z0-9 _.-]");
    private static final Pattern SPACE = Pattern.compile(" ");
    private static final Pattern DOT = Pattern.compile("\\.");

    private String hostUrl;
    private Integer port;
    private String username;
    private String password;
    private String vhost;
    private Boolean useTls;

    private String trustStore;
    private String trustStorePass;
    private String keyStore;
    private String keyStorePass;
    private String exchange;

    public static String calculateRoutingKey(Event event, KeycloakSession session) {
        //KK.EVENT.CLIENT.<REALM>.<RESULT>.<CLIENT>.<EVENT_TYPE>
        String routingKey = ROUTING_KEY_PREFIX
                + "." + removeDots(session.realms().getRealm(event.getRealmId()).getName())
                + "." + (event.getError() != null ? "ERROR" : "SUCCESS")
                + "." + removeDots(event.getClientId())
                + "." + event.getType();

        log.info(routingKey);

        return normalizeKey(routingKey);
    }

    //Remove all characters apart a-z, A-Z, 0-9, space, underscore, replace all spaces and hyphens with underscore
    public static String normalizeKey(CharSequence stringToNormalize) {
        return SPACE.matcher(SPECIAL_CHARACTERS.matcher(stringToNormalize).replaceAll(""))
                .replaceAll("_");
    }

    public static String removeDots(String stringToNormalize) {
        if(stringToNormalize != null) {
            return DOT.matcher(stringToNormalize).replaceAll("");
        }
        return stringToNormalize;
    }

    public static String writeAsJson(Object object, boolean isPretty) {
        try {
            if(isPretty) {
                return JsonSerialization.writeValueAsPrettyString(object);
            }
            return JsonSerialization.writeValueAsString(object);

        } catch (Exception e) {
            log.error("Could not serialize to JSON", e);
        }
        return "unparseable";
    }


    public static RabbitmqConfig createFromScope(Scope config) {
        RabbitmqConfig cfg = new RabbitmqConfig();

        cfg.hostUrl = resolveConfigVar(config, "url", "localhost");
        cfg.port = Integer.valueOf(resolveConfigVar(config, "port", "5672"));
        cfg.username = resolveConfigVar(config, "username", "admin");
        cfg.password = resolveConfigVar(config, "password", "admin");
        cfg.vhost = resolveConfigVar(config, "vhost", "/");
        cfg.useTls = Boolean.valueOf(resolveConfigVar(config, "use_tls", "false"));
        System.out.println(cfg.hostUrl);
        System.out.println(cfg.port);
        System.out.println(cfg.username);
        System.out.println(cfg.password);
        System.out.println(cfg.vhost);
        System.out.println(cfg.useTls);

        // SSL context settings
        cfg.trustStore = resolveConfigVar(config, "trust_store", "");
        cfg.trustStorePass = resolveConfigVar(config, "trust_store_pass", "");
        cfg.keyStore = resolveConfigVar(config, "key_store", "");
        cfg.keyStorePass = resolveConfigVar(config, "key_store_pass", "");
        //

        cfg.exchange = resolveConfigVar(config, "exchange", "keycloak-notification");
        return cfg;

    }

    private static String resolveConfigVar(Scope config, String variableName, String defaultValue) {

        String value = defaultValue;
        if(config != null && config.get(variableName) != null) {
            value = config.get(variableName);
        } else {
            //try from env variables eg: KK_TO_RMQ_URL:
            String envVariableName = "KK_TO_RMQ_" + variableName.toUpperCase(Locale.ENGLISH);
            String env = System.getenv(envVariableName);
            if(env != null) {
                value = env;
            }
        }
        if (!"password".equals(variableName)) {
            log.infof("keycloak-to-rabbitmq configuration: %s=%s%n", variableName, value);
        }
        return value;

    }


    public String getHostUrl() {
        return hostUrl;
    }
    public void setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
    }
    public Integer getPort() {
        return port;
    }
    public void setPort(Integer port) {
        this.port = port;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getVhost() {
        return vhost;
    }
    public void setVhost(String vhost) {
        this.vhost = vhost;
    }
    public Boolean getUseTls() {
        return useTls;
    }
    public void setUseTls(Boolean useTls) {
        this.useTls = useTls;
    }

    // setters and getters SSL context setting
    public void setTrustStore(String trustStore) {
        this.trustStore = trustStore;
    }
    public void setTrustStorePass(String trustStorePass) {
        this.trustStorePass = trustStorePass;
    }
    public void setKeyStore(String keyStore) {
        this.keyStore = keyStore;
    }
    public void setKeyStorePass(String keyStorePass) {
        this.keyStorePass = keyStorePass;
    }
    public String getTrustStore() {
        return trustStore;
    }
    public String getTrustStorePass() {
        return trustStorePass;
    }
    public String getKeyStore() {
        return keyStore;
    }
    public String getKeytStorePass() {
        return keyStorePass;
    }
    //

    public String getExchange() {
        return exchange;
    }
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }


}
