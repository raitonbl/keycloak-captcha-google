package com.raitonbl.keycloak.captcha;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class GoogleCaptchaServiceFactory implements CaptchaServiceFactory {

    private static final String PROVIDER_ID = "google";

    private GoogleCaptchaService singleton;

    @Override
    public CaptchaService create(KeycloakSession keycloakSession) {

        if (singleton == null) {
            singleton = new GoogleCaptchaService();
        }

        return singleton;
    }

    @Override
    public void init(Config.Scope scope) {
        // DO NOTHING
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
        // DO NOTHING
    }

    @Override
    public void close() {
        // DO NOTHING
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

}
