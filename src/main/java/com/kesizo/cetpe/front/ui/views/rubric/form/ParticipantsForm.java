package com.kesizo.cetpe.front.ui.views.rubric.form;

import com.kesizo.cetpe.front.controller.client.LearningStudentClient;
import com.kesizo.cetpe.front.controller.dtos.LearningProcess;
import com.kesizo.cetpe.front.controller.dtos.LearningStudent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;

public class ParticipantsForm extends VerticalLayout { // extends FormLayout: a responsive layout that shows form fields in 1 or 2 columns depending on viewport width.

    private VerticalLayout participantsLayout = new VerticalLayout();

    private Button save = new Button("Add");
    private Button update = new Button("Update");
    private Button close = new Button("Cancel");

    private Grid<LearningStudent> studentsGrid = new Grid<>(LearningStudent.class);
    private Binder<LearningStudent> binderStudentForm = new Binder<>();
    private LearningStudent studentFormBean = new LearningStudent();
    private LearningProcess currentLearningProcess;
    private LearningStudentClient studentsService;


    public ParticipantsForm(LearningProcess learningProcess, LearningStudentClient studentsService) {

        this.setMargin(true);
        this.addClassName("students-form"); // Gives the component a CSS class name so we can style it later

        //Configuring the binder
        this.studentsService = studentsService;
        binderStudentForm.setBean(studentFormBean);
        currentLearningProcess = learningProcess;

        configureGrid();

        updateList();

        H2 header = new H2("Participants editor");
        this.add(
                header,
                createButtonsLayout(),
                studentsGrid); // Adds all the UI components. The buttons require a bit of extra configuration so we create and call a new method, createButtonsLayout()

    }

    private void updateList() {

        this.studentsGrid.setItems(studentsService.learningStudentsIndex());
    }


    public Grid<LearningStudent> getStudentsGrid() {
        return studentsGrid;
    }

    private void configureGrid() {

        this.studentsGrid.setHeight("100%");
        this.studentsGrid.setVisible(true);
        this.studentsGrid.addThemeVariants(GridVariant.LUMO_COMPACT);


    }
    private HorizontalLayout createButtonsLayout() {

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY); // Makes the buttons visually distinct from each other using built-in theme variants
        update.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        close.addClickShortcut(Key.ESCAPE);
        update.setVisible(false);

        HorizontalLayout buttonSet = new HorizontalLayout(save, update, close);
        buttonSet.setMargin(true);

        return buttonSet; // Returns a HorizontalLayout containing the buttons to place them next to each other
    }


    public Button getSave() {
        return save;
    }

    public Button getUpdate() {
        return update;
    }

    public Button getClose() {
        return close;
    }

}
