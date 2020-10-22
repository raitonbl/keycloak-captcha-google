# keycloak captcha google
Intended to extend [Keycloak](https://www.keycloak.org/) features through a Google Recaptcha v3 service which allows for captcha. This project implements [Keycloak captcha api](https://github.com/raitonbl/keycloak-captcha)

## Building

Ensure you have JDK 8 (or newer), Maven 3.5.4 (or newer) and Git installed

    java -version
    mvn -version
    git --version

How to build:

    mvn clean package

## Deployment    

In order to deploy the implementation , Keycloak must be stopped and the generated jar should be deployed on **KEYCLOAK_HOME/providers/** (for containers) or on **KEYCLOAK_HOME/standalone/deployments/**.
Its important that [API](https://github.com/raitonbl/keycloak-captcha) version matches the plugin version and is also deployed on Keycloak.

Start **Keycloak** , [More details](https://www.keycloak.org/documentation.html)

