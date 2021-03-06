package com.kesizo.cetpe.front.ui.views.participants;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */

//@PWA(name = "P2P Assessment (CETPE)",
//        shortName = "P2P Assessment",
//        description = "Web app aims to support collaborative evaluation by peers methods.",
//        enableInstallPrompt = true)
//@CssImport("./styles/shared-styles.css")
//@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
//public class MainView extends VerticalLayout {
//
//    /**
//     * Construct a new Vaadin view.
//     * <p>
//     * Build the initial UI state for the user accessing the application.
//     *
//     * @param service The message service. Automatically injected Spring managed bean.
//     */
//    public MainView(@Autowired GreetService service) {
//
//        // Use TextField for standard text input
//        TextField textField = new TextField("Your name");
//
//        // Button click listeners can be defined as lambda expressions
//        Button button = new Button("Say hello",
//                e -> Notification.show(service.greet(textField.getValue())));
//
//        // Theme variants give you predefined extra styles for components.
//        // Example: Primary button is more prominent look.
//        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//
//        // You can specify keyboard shortcuts for buttons.
//        // Example: Pressing enter in this view clicks the Button.
//        button.addClickShortcut(Key.ENTER);
//
//        // Use custom CSS classes to apply styling. This is defined in shared-styles.css.
//        addClassName("centered-content");
//
//        add(textField, button);
//
//        add(new H1("Hello world"), textField, button);
//    }
public class ParticipantsView extends VerticalLayout {

    public ParticipantsView() {

    }

}
