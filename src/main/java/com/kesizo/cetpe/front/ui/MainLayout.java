package com.kesizo.cetpe.front.ui;

import com.kesizo.cetpe.front.ui.views.mycollaborations.MyCollaborationsViewImpl;
import com.kesizo.cetpe.front.ui.views.mylearningprocess.MyLearningProcessesView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

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

@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {


    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {

        H1 logo = new H1("P2P Assessment");
        logo.addClassName("logo");

        // Creates a new Anchor (<a> tag) that links to /logout
        Anchor logout = new Anchor("/logout", "Log out");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);
        header.expand(logo); //make the logo take up all the extra space in the layout. This pushes the logout button to the far righ
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassName("header");

        addToNavbar(header);

    }

    private void createDrawer() {
        RouterLink listLink = new RouterLink("My Assessments", MyLearningProcessesView.class);
        RouterLink listLinkCollaborations = new RouterLink("P2P Collaborations", MyCollaborationsViewImpl.class);

        listLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(listLink,listLinkCollaborations));

    }
}