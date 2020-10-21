package com.raitonbl.keycloak.captcha;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.jboss.resteasy.logging.Logger;
import org.keycloak.connections.httpclient.HttpClientProvider;
import org.keycloak.models.KeycloakSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GoogleCaptchaService implements CaptchaService {

    private static final Logger LOG = Logger.getLogger(GoogleCaptchaService.class);
    private static final String SITE_KEY = "COM.RAITONBL.KEYCLOAK.CAPTCHA.GOOGLE.SITE-KEY";
    private static final String SECRET_KEY = "COM.RAITONBL.KEYCLOAK.CAPTCHA.GOOGLE.SECRET-KEY";

    private final ObjectMapper objectMapper;

    public GoogleCaptchaService() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public boolean isVerified(KeycloakSession session, String responseToken) {
        try {

            String key = Optional.ofNullable(System.getenv(SECRET_KEY)).orElseGet(() -> System.getProperty(SECRET_KEY));

            if (key == null || responseToken == null || session == null) {

                if (session == null) {
                    LOG.debug("session  is null therefore there is no verification to be done");
                }

                if (key == null) {
                    LOG.debug("key is null therefore there is no verification to be done");
                }

                if (responseToken == null) {
                    LOG.debug("secret is null therefore there is no verification to be done");
                }

                return Boolean.FALSE;
            }

            HttpClientProvider provider = session.getProvider(HttpClientProvider.class);

            if (provider == null) {
                LOG.debug("provider couldn't be found therefore there is no verification to be done");
                return Boolean.FALSE;
            }

            return verify(provider, key, responseToken);
        } catch (CaptchaException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new CaptchaException(ex);
        }
    }

    @Override
    public String getCaptchaSiteKey() {
        return Optional.ofNullable(System.getenv(SITE_KEY)).orElseGet(() -> System.getProperty(SITE_KEY));
    }

    private boolean verify(HttpClientProvider provider, String secretKey, String responseToken) throws IOException {

        if (secretKey == null || responseToken == null || provider == null) {
            return Boolean.FALSE;
        }

        HttpPost req = new HttpPost("https://www.google.com/recaptcha/api/siteverify");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("secret", secretKey));
        params.add(new BasicNameValuePair("response", responseToken));

        req.setEntity(new UrlEncodedFormEntity(params));
        req.setHeader("Accept-Language", "en-US,en;q=0.5");

        HttpResponse response = provider.getHttpClient().execute(req);

        CaptchaResponse instance = objectMapper.readValue(response.getEntity().getContent(), CaptchaResponse.class);

        if (instance.getTimestamp() == null) {
            return Boolean.FALSE;
        }

        return instance.isSuccess();
    }

    @Override
    public void close() {
        // DO NOTHING
    }

}
