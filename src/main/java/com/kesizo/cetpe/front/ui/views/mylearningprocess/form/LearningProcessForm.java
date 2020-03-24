package com.kesizo.cetpe.front.ui.views.mylearningprocess.form;

import com.kesizo.cetpe.front.controller.dtos.LearningProcess;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class LearningProcessForm extends FormLayout { // extends FormLayout: a responsive layout that shows form fields in 1 or 2 columns depending on viewport width.

  private TextField name = new TextField("Learning Process Name"); // Creates all the UI components as fields in the component.
  private TextArea description = new TextArea("Description");
  private DatePicker startingDateTime = new DatePicker();
  private DatePicker endDateTime = new DatePicker();
  private Button save = new Button("Save"); //
  private Button delete = new Button("Delete");
  private Button close = new Button("Cancel");

  private Binder<LearningProcess> binderNewLearningProcessForm = new Binder<>();
  private LearningProcess learningProcessFormBean;

  // BeanValidationBinder is a Binder that is aware of bean validation annotations. By passing it in the LearningProcess.class, we define the type of object we are binding to.
  // Binder<LearningProcess> binder = new BeanValidationBinder<>(LearningProcess.class);

  public LearningProcessForm() {
    this.addClassName("learning-process-form"); // Gives the component a CSS class name so we can style it later
    //binder.bindInstanceFields(this); //bindInstanceFields matches fields in LearningProcess and LearningProcessForm based on their names
    this.add(name,
            description,
            startingDateTime,
            endDateTime,
            createButtonsLayout()); // Adds all the UI components. The buttons require a bit of extra configuration so we create and call a new method, createButtonsLayout()
  }

  private HorizontalLayout createButtonsLayout() {

    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY); // Makes the buttons visually distinct from each other using built-in theme variants
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    save.addClickShortcut(Key.ENTER); // Defines keyboard shortcuts: Enter to save and Escape to close the editor
    close.addClickShortcut(Key.ESCAPE);

    return new HorizontalLayout(save, delete, close); // Returns a HorizontalLayout containing the buttons to place them next to each other
  }

  public void setContact(LearningProcess learningProcess) {
  //  binder.setBean(learningProcess); // Calls binder.setBean to bind the values from the learningProcess to the UI fields
  }
}