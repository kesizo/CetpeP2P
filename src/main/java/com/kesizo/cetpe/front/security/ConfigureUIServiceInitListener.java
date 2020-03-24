package com.kesizo.cetpe.front.security;

import com.kesizo.cetpe.front.ui.views.login.LoginViewImpl;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.springframework.stereotype.Component;

@Component //  Registers the listener. Vaadin will pick it up on startup.
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

	@Override
	public void serviceInit(ServiceInitEvent event) {
		event.getSource().addUIInitListener(uiEvent -> { // We listen for the initialization of the UI (the internal root component in Vaadin)
														 // and then add a listener before every view transition.

			final UI ui = uiEvent.getUI();
			ui.addBeforeEnterListener(this::authenticateNavigation);
		});
	}

	private void authenticateNavigation(BeforeEnterEvent event) {
		if (!LoginViewImpl.class.equals(event.getNavigationTarget())
		    && !SecurityUtils.isUserLoggedIn()) { // In authenticateNavigation, we reroute all requests to the login,
			 									  // if the user is not logged in

			event.rerouteTo(LoginViewImpl.class);
		}
	}
}