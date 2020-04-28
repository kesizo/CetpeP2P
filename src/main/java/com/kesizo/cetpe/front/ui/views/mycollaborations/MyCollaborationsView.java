package com.kesizo.cetpe.front.ui.views.mycollaborations;

import com.kesizo.cetpe.front.controller.client.LearningProcessClient;
import com.kesizo.cetpe.front.controller.client.StatusClient;
import com.kesizo.cetpe.front.controller.dtos.LearningProcess;
import com.kesizo.cetpe.front.controller.dtos.Status;
import com.kesizo.cetpe.front.ui.MainLayout;
import com.kesizo.cetpe.front.ui.components.CardListLayout;
import com.kesizo.cetpe.front.ui.views.mylearningprocess.form.LearningProcessForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

@Route(value = "my-collaborations", layout = MainLayout.class)
@PageTitle("Assessments Participation | CETPE P2P")
@CssImport(value = "styles/views/mylearningprocess/mylearningprocess-view.css", include = "lumo-badge")
@JsModule("@vaadin/vaadin-lumo-styles/badge.js")
public class MyCollaborationsView extends Div implements AfterNavigationObserver {

    private LearningProcessClient learningService;
    private StatusClient statusService;
    private Grid<LearningProcess> grid;
    private HorizontalLayout buttonSet;
    private TextField filterText;
    private Button addLearningProcessButton;
    private LearningProcessForm form;

    public MyCollaborationsView(@Autowired LearningProcessClient learningService, @Autowired StatusClient statusService) {

        this.learningService = learningService;
        this.statusService = statusService;

        setId("mylearningprocess-view");
        addClassName("mylearningprocess-view");
        setSizeFull();

        // Instantiate the button filter/button area
        this.configureLearningProcessSearchAndCreateActionArea();

        // Configure the grid with the cards
        this.configureLearningProcessesGrid();

        // Instantiate the Form
        this.configureLearningProcessActionForm();

        // Creates a Div that wraps the grid and the form, gives it a CSS class name, and makes it full size
        Div content = new Div(grid,form);
        content.addClassName("content");
        content.setSizeFull();

        this.add(buttonSet, content);

    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {

        grid.setItems(learningService.getLearningProcess());
    }

    private void configureLearningProcessSearchAndCreateActionArea() {
        HorizontalLayout buttonSet = new HorizontalLayout();
        buttonSet.addClassName("mylearningprocess-buttonSet");

        filterText = new TextField();
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        addLearningProcessButton = new Button("+", click -> toggleAddLearningProcessMenu());

        buttonSet.add(filterText, addLearningProcessButton);

        this.buttonSet =buttonSet;
    }

    private void configureLearningProcessesGrid(){
        this.grid = new Grid<>();
        this.grid.addClassName("grid-cards");
        this.grid.setHeight("100%");
        this.grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        this.grid.addComponentColumn(CardListLayout::new);

        this.grid.addItemClickListener(event -> {
            LearningProcess updatableLearningProcess = learningService.getLearningProcessById(String.valueOf(event.getItem().getId()));
            form.getBinderNewLearningProcessForm().setBean(null);
            form.getBinderNewLearningProcessForm().setBean(updatableLearningProcess);
            if (!form.isVisible()) {
                form.setVisible(true);
            }
            form.setEditionMode(true);

        });

    }

    private void configureLearningProcessActionForm(){
        this.form = new LearningProcessForm();
        this.form.setVisible(false);
        this.form.getSave().addClickListener(event -> saveNewLearningProcess());
        this.form.getClose().addClickListener(event -> closeNewLearningProcess());
        this.form.getDelete().addClickListener(event -> deleteNewLearningProcess());
        this.form.getUpdate().addClickListener(event -> updateLearningProcess());
        this.form.getConfig().addClickListener(event -> configLearningProcess());
        this.form.getStatusSelector().setItems(statusService.cetpeLearningProcessStatusIndex());
        this.form.getStatusSelector().setItemLabelGenerator(Status::getName);
    }

    private void updateList() {

        grid.setItems(learningService.getLearningProcess()
                .stream()
                .filter(learningProcess -> learningProcess.getName().contains(filterText.getValue()))
                .collect(Collectors.toList()));
    }

    private void toggleAddLearningProcessMenu(){
        this.form.resetFields();
        this.form.setEditionMode(false);
        form.setVisible(!form.isVisible());
    }

    private void closeNewLearningProcess() {
        this.toggleAddLearningProcessMenu();
    }

    private void saveNewLearningProcess() {

        if (this.form.getBinderNewLearningProcessForm().isValid()) {

            //Using Jackson mapper
            // Map<String, String> learningProcessObjMapped = new ObjectMapper().convertValue(this.form.getBinderNewLearningProcessForm().getBean(), Map.class);
            //Without Jackson mapper
            Map<String, Object> learningProcessObjMapped = this.form.getBinderNewLearningProcessForm().getBean().toMap();

            if (learningProcessObjMapped==null) {
                Notification notification = new Notification("Data input is null, please contact the administrator of the tool", 3000);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.open();
            }
            else if (null == learningService.create(learningProcessObjMapped)) {
                Notification notification = new Notification("Validation succeeded but the operation fails, please contact the administrator of the tool", 3000);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.open();
            } else {
                Notification notification = new Notification("New learning process created", 3000);
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                this.form.resetFields();
                grid.setItems(learningService.getLearningProcess());
                notification.open();
            }
        }
        else { //Validation fails
            Notification notification = new Notification("Validation fails, please review the input values and try again", 3000);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        }
    }

    private void updateLearningProcess() {

        if (this.form.getBinderNewLearningProcessForm().isValid()) {

            //Without Jackson mapper
            Map<String, Object> learningProcessObjMapped = this.form.getBinderNewLearningProcessForm().getBean().toMap();

            if (null == learningService.update(this.form.getBinderNewLearningProcessForm().getBean().getId().toString(),learningProcessObjMapped)) {
                Notification notification = new Notification("Validation succeeded but update operation fails, please contact the administrator of the tool", 3000);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.open();
            } else {
                Notification notification = new Notification("Learning process successfully updated", 3000);
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                this.form.resetFields();
                grid.setItems(learningService.getLearningProcess());
                notification.open();
            }
        }
        else { //Validation fails
            Notification notification = new Notification("Validation fails, please review the input values and try again", 3000);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        }
    }

    private void deleteNewLearningProcess() {

        if (!learningService.delete(this.form.getBinderNewLearningProcessForm().getBean().getId().toString())) {
            Notification notification = new Notification("Delete operation fails, please contact the administrator of the tool", 3000);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        } else {
            Notification notification = new Notification("Selected learning process successfully deleted ", 3000);
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            this.form.resetFields();
            grid.setItems(learningService.getLearningProcess());
            form.setEditionMode(false);
            notification.open();
        }
    }

    private void configLearningProcess() {

        LearningProcess currentLearningProcess = learningService.getLearningProcessById(this.form.getBinderNewLearningProcessForm().getBean().getId().toString());
        if (null==currentLearningProcess) {
            Notification notification = new Notification("Config operation fails, please contact the administrator of the tool", 3000);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        } else {
            Notification notification = new Notification("Accessing to rubric configuration ", 3000);
            notification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);

            List<String> list = new ArrayList<>();
            Map<String, List<String>> parametersMap = new HashMap<>();
            list.add(currentLearningProcess.getId().toString());
            parametersMap.put("learning_process_id", list);
            QueryParameters qp = new QueryParameters(parametersMap);

            form.getUI().ifPresent(ui ->
                    ui.navigate("rubrics",qp));
            notification.open();
        }

    }
}
