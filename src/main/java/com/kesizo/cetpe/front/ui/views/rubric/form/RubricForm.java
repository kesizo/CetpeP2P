package com.kesizo.cetpe.front.ui.views.rubric.form;

import com.kesizo.cetpe.front.controller.dtos.AssessmentRubric;
import com.kesizo.cetpe.front.controller.dtos.RubricType;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import java.util.Locale;

public class RubricForm extends FormLayout { // extends FormLayout: a responsive layout that shows form fields in 1 or 2 columns depending on viewport width.

    private Checkbox enableRubricCheckBox = new Checkbox("Enable the Rubric");

    private TextField titleTextField = new TextField("Rubric Title"); // Creates all the UI components as fields in the component.
    private DatePicker startingDateTime = new DatePicker();
    private DatePicker endDateTime = new DatePicker();

    private ComboBox<RubricType> rubricTypeSelector = new ComboBox<>("Rubric Type");
    private IntegerField rankIntegerField = new IntegerField("Rubric Rank");

    private Button update = new Button("Update");
    private Button config = new Button();

    private Binder<AssessmentRubric> binderNewAssessmentForm = new Binder<>();
    private AssessmentRubric assessmentRubricFormBean = null;

    public RubricForm(AssessmentRubric current) {
        this.addClassName("assessment-rubric-form"); // Gives the component a CSS class name so we can style it later

        //Configuring the binder
        binderNewAssessmentForm.setBean(current);

        enableRubricCheckBox.setId("assessment-rubric-enable");
        enableRubricCheckBox.setValue(binderNewAssessmentForm.getBean()!=null);

        titleTextField.setId("assessment-rubric-title-textField");
        titleTextField.setEnabled(binderNewAssessmentForm.getBean()!=null);
        binderNewAssessmentForm.forField(titleTextField)
                .withValidator(string -> string != null && !string.isEmpty(), "Assessment Rubric title cannot be empty")
                .withValidator(name -> name.length() > 2, "Assessment Rubric title must contain at least 3 characters")
                .withValidator(name -> name.length() < 256, "Assessment Rubric title cannot be bigger than 255 characters")
                //.withConverter(new StringToIntegerConverter("Input value should be an integer"))
                //.withValidator(integer -> integer > -1, "Input value should be a positive integer")
                .bind(assessmentRubric -> assessmentRubric.getTitle(),
                        ((assessmentRubric, formValue) -> assessmentRubric.setTitle(formValue))
                );

        startingDateTime.setId("assessment-rubric-starting-date");
        startingDateTime.setEnabled(binderNewAssessmentForm.getBean()!=null);
        startingDateTime.setLabel("Assessment Rubric Starting Date");
        startingDateTime.setLocale(Locale.ITALIAN);
        binderNewAssessmentForm.forField(startingDateTime)
//            .withValidator(returnDate -> !returnDate
//                      .isBefore(endDateTime.getValue()),
//              "Cannot return before departing")
                .bind(assessmentRubric -> null!= assessmentRubric.getStarting_date_time() ? assessmentRubric.getStarting_date_time().toLocalDate() : null,
                        (assessmentRubric, formValue) -> assessmentRubric.setStarting_date_time(formValue.atStartOfDay()));


        endDateTime.setId("assessment-rubric-end-date");
        endDateTime.setEnabled(binderNewAssessmentForm.getBean()!=null);
        endDateTime.setLabel("Assessment Rubric Deadline");
        endDateTime.setLocale(Locale.ITALIAN);
        binderNewAssessmentForm.forField(endDateTime)
//            .withValidator(returnDate -> !returnDate
//                      .isBefore(endDateTime.getValue()),
//              "Cannot return before departing")
                .bind(assessmentRubric -> null!= assessmentRubric.getEnd_date_time() ? assessmentRubric.getEnd_date_time().toLocalDate() : null,
                        (assessmentRubric, formValue) -> assessmentRubric.setEnd_date_time(formValue.atStartOfDay()));

        rankIntegerField.setId("assessment-rubric-rank");
        rankIntegerField.setEnabled(binderNewAssessmentForm.getBean()!=null);
        rankIntegerField.setHasControls(true);
        rankIntegerField.setStep(1);
        rankIntegerField.setMin(1);
        rankIntegerField.setMax(10);
        binderNewAssessmentForm.forField(rankIntegerField)
                .bind(AssessmentRubric::getRank,AssessmentRubric::setRank);


        rubricTypeSelector.setId("assessment-rubric-status");
        rubricTypeSelector.setEnabled(binderNewAssessmentForm.getBean()!=null);
      //  binderNewAssessmentForm.forField(rubricTypeSelector)
      //          .bind(AssessmentRubric::getRubricType, AssessmentRubric::setRubricType);

        this.add(enableRubricCheckBox,
                titleTextField,
                rubricTypeSelector,
                startingDateTime,
                endDateTime,
                rankIntegerField,
                createButtonsLayout());

        enableFields(binderNewAssessmentForm.getBean()!=null);
    }

    public Binder<AssessmentRubric> getBinderAssessmentForm() {
        return binderNewAssessmentForm;
    }

    private HorizontalLayout createButtonsLayout() {

        update.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        config.addThemeVariants(ButtonVariant.LUMO_ICON);
        config.setIcon(new Icon(VaadinIcon.WRENCH));

        update.setVisible(false);
        config.setVisible(false);
        HorizontalLayout buttonSet = new HorizontalLayout(update, config);
        buttonSet.setMargin(true);

        return buttonSet; // Returns a HorizontalLayout containing the buttons to place them next to each other
    }
    public void enableFields(boolean enable) {

        titleTextField.setEnabled(enable);
        startingDateTime.setEnabled(enable);
        endDateTime.setEnabled(enable);
        rankIntegerField.setEnabled(enable);
        rubricTypeSelector.setEnabled(enable);
        update.setVisible(enable);
        config.setVisible(enable);
    }

    public Checkbox getEnableRubricCheckBox() {
        return enableRubricCheckBox;
    }


    public AssessmentRubric getAssessmentRubricFormBean() {
        return assessmentRubricFormBean;
    }

    public void setAssessmentRubricFormBean(AssessmentRubric assessmentRubricFormBean) {
        this.assessmentRubricFormBean = assessmentRubricFormBean;
    }

    public ComboBox<RubricType> getRubricTypeSelector() {
        return rubricTypeSelector;
    }

    public Button getUpdate() {
        return update;
    }

    public void setUpdate(Button update) {
        this.update = update;
    }

    public Button getConfig() {
        return config;
    }

    public void setConfig(Button config) {
        this.config = config;
    }
}
