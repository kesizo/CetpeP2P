package com.kesizo.cetpe.front.ui.views.login;

import com.kesizo.cetpe.front.util.Constants;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Collections;

@Route("login") //
@PageTitle("Login | "+ Constants.APP_TITLE)

public class LoginViewImpl extends VerticalLayout implements BeforeEnterObserver {

    private LoginForm login = new LoginForm(); //Default Vaadin component

    public LoginViewImpl()
    {
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);

        setJustifyContentMode(JustifyContentMode.CENTER);

        // Sets the LoginForm action to "login" to post the login form to Spring Security
        login.setAction("login");

        add(new H1(Constants.APP_TITLE), login);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event)
    {
        // inform the user about an authentication error

        // Reads query parameters and shows an error if a login attempt fails
        if(!event.getLocation()
                .getQueryParameters()
                .getParameters()
                .getOrDefault("error", Collections.emptyList())
                .isEmpty())
        {
            login.setError(true);
        }
    }
}