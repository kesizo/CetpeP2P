package com.kesizo.cetpe.front.ui.views.mylearningprocess.form;

import com.kesizo.cetpe.front.controller.dtos.LearningProcess;
import com.kesizo.cetpe.front.controller.dtos.Status;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LearningProcessForm extends FormLayout { // extends FormLayout: a responsive layout that shows form fields in 1 or 2 columns depending on viewport width.

  private TextField nameTextField = new TextField("Learning Process Name"); // Creates all the UI components as fields in the component.
  private TextArea descriptionText = new TextArea("Description");
  private DatePicker startingDateTime = new DatePicker();
  private DatePicker endDateTime = new DatePicker();
  private ComboBox<Status> statusSelector = new ComboBox<>("Learning Process Status");

  private HorizontalLayout datesArea;
  private VerticalLayout gradesAreaLayout;
  private HorizontalLayout weightArea;

  private Checkbox cal1CheckboxPublished = new Checkbox("Grade 1");
  private Checkbox cal2CheckboxPublished = new Checkbox("Grade 2");
  private Checkbox cal3CheckboxPublished = new Checkbox("Grade 3");
  private Checkbox calFCheckboxPublished = new Checkbox("Grade F");

  private ComboBox<Integer> weightA = new ComboBox("A %", Stream.iterate(0, n -> n + 1).limit(100).collect(Collectors.toList()));
  private ComboBox<Integer> weightB = new ComboBox("B %", Stream.iterate(0, n -> n + 1).limit(100).collect(Collectors.toList()));
  private ComboBox<Integer> weightC = new ComboBox("C %", Stream.iterate(0, n -> n + 1).limit(100).collect(Collectors.toList()));
  private ComboBox<Integer> weightD = new ComboBox("D %", Stream.iterate(0, n -> n + 1).limit(100).collect(Collectors.toList()));
  private ComboBox<Integer> weightE = new ComboBox("E %", Stream.iterate(0, n -> n + 1).limit(100).collect(Collectors.toList()));

  private Button save = new Button("Save");
  private Button update = new Button("Update");
  private Button delete = new Button("Delete");
  private Button close = new Button("Cancel");


  private Binder<LearningProcess> binderNewLearningProcessForm = new Binder<>();
  private LearningProcess learningProcessFormBean = new LearningProcess();

  // BeanValidationBinder is a Binder that is aware of bean validation annotations. By passing it in the LearningProcess.class, we define the type of object we are binding to.
  // Binder<LearningProcess> binder = new BeanValidationBinder<>(LearningProcess.class);

  public LearningProcessForm() {
    this.addClassName("learning-process-form"); // Gives the component a CSS class name so we can style it later

    //Configuring the binder
    binderNewLearningProcessForm.setBean(learningProcessFormBean);


    nameTextField.setId("learning-process-name-textField");
    binderNewLearningProcessForm.forField(nameTextField)
            .withValidator(string -> string != null && !string.isEmpty(), "Learning process title cannot be empty")
            .withValidator(name -> name.length() > 2, "Learning process title must contain at least 3 characters")
            .withValidator(name -> name.length() < 256, "Learning process title cannot be bigger than 255 characters")
            //.withConverter(new StringToIntegerConverter("Input value should be an integer"))
            //.withValidator(integer -> integer > -1, "Input value should be a positive integer")
            .bind(learningProcess -> learningProcess.getName(),
                    ((learningProcess, formValue) -> learningProcess.setName(formValue))
            );


    nameTextField.setId("learning-process-descriptionText");
    binderNewLearningProcessForm.forField(descriptionText)
            .withValidator(string -> string != null && !string.isEmpty(), "Learning process description cannot be empty")
            .withValidator(name -> name.length() > 1, "Learning process description must contain at least 1 characters")
            .withValidator(name -> name.length() < 1025, "Learning process description cannot be bigger than 1024 characters")
            //.withConverter(new StringToIntegerConverter("Input value should be an integer"))
            //.withValidator(integer -> integer > -1, "Input value should be a positive integer")
            .bind(learningProcess -> learningProcess.getDescription(),
                    ((learningProcess, formValue) -> learningProcess.setDescription(formValue))
            );


    // see revalidation section
    // https://vaadin.com/docs/flow/binding-data/tutorial-flow-components-binder-validation.html


    startingDateTime.setId("learning-process-starting-date");
    startingDateTime.setLabel("Learning Process Starting Date");
    startingDateTime.setLocale(Locale.ITALIAN);
    binderNewLearningProcessForm.forField(startingDateTime)
//            .withValidator(returnDate -> !returnDate
//                      .isBefore(endDateTime.getValue()),
//              "Cannot return before departing")
            .bind(learningProcess -> null!= learningProcess.getStarting_date_time() ? learningProcess.getStarting_date_time().toLocalDate() : null,
                    (learningProcess, formValue) -> learningProcess.setStarting_date_time(formValue.atStartOfDay()));


    endDateTime.setId("learning-process-end-date");
    endDateTime.setLabel("Learning Process Deadline");
    endDateTime.setLocale(Locale.ITALIAN);
    binderNewLearningProcessForm.forField(endDateTime)
//            .withValidator(returnDate -> !returnDate
//                      .isBefore(endDateTime.getValue()),
//              "Cannot return before departing")
            .bind(learningProcess -> null!= learningProcess.getEnd_date_time() ? learningProcess.getEnd_date_time().toLocalDate() : null,
                    (learningProcess, formValue) -> learningProcess.setEnd_date_time(formValue.atStartOfDay()));

    datesArea = new HorizontalLayout(startingDateTime, endDateTime);
    datesArea.setVisible(false);

    weightA.setId("learning-process-weight-A");
    weightA.setAllowCustomValue(false);
    weightA.setWidth("70px");
    binderNewLearningProcessForm.forField(weightA)
             .bind(LearningProcess::getWeight_param_A,LearningProcess::setWeight_param_A);

    weightB.setId("learning-process-weight-B");
    weightB.setAllowCustomValue(false);
    weightB.setWidth("70px");
    binderNewLearningProcessForm.forField(weightB)
            .bind(LearningProcess::getWeight_param_B,LearningProcess::setWeight_param_B);

    weightC.setId("learning-process-weight-C");
    weightC.setAllowCustomValue(false);
    weightC.setWidth("70px");
    binderNewLearningProcessForm.forField(weightC)
            .bind(LearningProcess::getWeight_param_C,LearningProcess::setWeight_param_C);

    weightD.setId("learning-process-weight-D");
    weightD.setAllowCustomValue(false);
    weightD.setWidth("70px");
    binderNewLearningProcessForm.forField(weightD)
            .bind(LearningProcess::getWeight_param_D,LearningProcess::setWeight_param_D);

    weightE.setId("learning-process-weight-E");
    weightE.setAllowCustomValue(false);
    weightE.setWidth("70px");
    binderNewLearningProcessForm.forField(weightE)
          //  .withValidator(value -> value>-1 && value<99, "Value must be an integer between 0 and 100")
            .bind(LearningProcess::getWeight_param_E,LearningProcess::setWeight_param_E);

    weightArea = new HorizontalLayout(weightA,weightB,weightC,weightD,weightE);
    weightArea.setVisible(false);

    statusSelector.setId("learning-process-status");
    binderNewLearningProcessForm.forField(statusSelector)
            .bind(LearningProcess::getLearning_process_status,
                    LearningProcess::setLearning_process_status);

    cal1CheckboxPublished.setId("learning-process-grade-1");
    binderNewLearningProcessForm.forField(cal1CheckboxPublished)
            .bind(LearningProcess::getIs_cal1_available, LearningProcess::setIs_cal1_available);

    cal2CheckboxPublished.setId("learning-process-grade-2");
    binderNewLearningProcessForm.forField(cal2CheckboxPublished)
            .bind(LearningProcess::getIs_cal2_available, LearningProcess::setIs_cal2_available);

    cal3CheckboxPublished.setId("learning-process-grade-3");
    binderNewLearningProcessForm.forField(cal3CheckboxPublished)
            .bind(LearningProcess::getIs_cal3_available, LearningProcess::setIs_cal3_available);

    calFCheckboxPublished.setId("learning-process-grade-F");
    binderNewLearningProcessForm.forField(calFCheckboxPublished)
            .bind(LearningProcess::getIs_calF_available, LearningProcess::setIs_calF_available);


    Label titleGrades = new Label("Grades publication");
    HorizontalLayout gradesArea = new HorizontalLayout(cal1CheckboxPublished,cal2CheckboxPublished,cal3CheckboxPublished,calFCheckboxPublished);
    gradesAreaLayout = new VerticalLayout(titleGrades, gradesArea);
    gradesAreaLayout.setMargin(true);
    gradesAreaLayout.setPadding(false);
    gradesAreaLayout.setVisible(false);

    this.add(nameTextField,
            descriptionText,
            datesArea,
            statusSelector,
            weightArea,
            gradesAreaLayout,
            createButtonsLayout()); // Adds all the UI components. The buttons require a bit of extra configuration so we create and call a new method, createButtonsLayout()
  }

  public Binder<LearningProcess> getBinderNewLearningProcessForm() {
    return binderNewLearningProcessForm;
  }

  private HorizontalLayout createButtonsLayout() {

    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY); // Makes the buttons visually distinct from each other using built-in theme variants
    update.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    save.addClickShortcut(Key.ENTER); // Defines keyboard shortcuts: Enter to save and Escape to close the editor
    close.addClickShortcut(Key.ESCAPE);

    update.setVisible(false);
    delete.setVisible(false);
    HorizontalLayout buttonSet = new HorizontalLayout(save,update, delete, close);
    buttonSet.setMargin(true);

    return buttonSet; // Returns a HorizontalLayout containing the buttons to place them next to each other
  }

  public void resetFields(){
    this.binderNewLearningProcessForm.setBean(null);
    learningProcessFormBean = new LearningProcess();
    binderNewLearningProcessForm.setBean(learningProcessFormBean);
  }

  public void setEditionMode(boolean isEditionMode){
    this.delete.setVisible(isEditionMode);
    this.save.setVisible(!isEditionMode);
    this.update.setVisible(isEditionMode);
    this.datesArea.setVisible(isEditionMode);
    this.statusSelector.setVisible(isEditionMode);
    this.weightArea.setVisible(isEditionMode);
    this.gradesAreaLayout.setVisible(isEditionMode);
  }

  public Button getSave() {
    return save;
  }

  public Button getDelete() {
    return delete;
  }

  public Button getClose() {
    return close;
  }

  public Button getUpdate() {
    return update;
  }

  public ComboBox<Status> getStatusSelector() {
    return statusSelector;
  }

}