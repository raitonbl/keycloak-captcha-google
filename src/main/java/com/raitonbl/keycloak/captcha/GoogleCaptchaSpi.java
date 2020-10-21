package com.raitonbl.keycloak.captcha;

import org.keycloak.provider.ProviderFactory;
import org.keycloak.provider.Spi;

public class GoogleCaptchaSpi implements Spi {

    @Override
    public boolean isInternal() {
        return Boolean.FALSE;
    }

    @Override
    public String getName() {
        return "captcha";
    }

    @Override
    public Class<? extends CaptchaService> getProviderClass() {
        return CaptchaService.class;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return CaptchaServiceFactory.class;
    }

}
