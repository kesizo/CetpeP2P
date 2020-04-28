package com.kesizo.cetpe.front.ui.views.rubric;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kesizo.cetpe.front.controller.client.*;
import com.kesizo.cetpe.front.controller.dtos.*;
import com.kesizo.cetpe.front.ui.MainLayout;
import com.kesizo.cetpe.front.ui.components.UserGroupMemberLayout;
import com.kesizo.cetpe.front.ui.views.rubric.form.AssessmentRubricItemsForm;
import com.kesizo.cetpe.front.ui.views.rubric.form.ParticipantsForm;
import com.kesizo.cetpe.front.ui.views.rubric.form.RubricForm;
import com.kesizo.cetpe.front.ui.views.rubric.form.UserGroupsForm;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Route(value = "rubrics", layout = MainLayout.class)
@PageTitle("Rubric Configuration | CETPE P2P")
@CssImport(value = "styles/views/rubricconfiguration/rubricconfiguration-view.css", include = "lumo-badge")
@JsModule("@vaadin/vaadin-lumo-styles/badge.js")
public class RubricConfigurationView extends Div implements AfterNavigationObserver, HasUrlParameter<String> {

    private String learning_process_id;
    private LearningProcess currentLearningProcess;
    private AssessmentRubric rubric1;
    private AssessmentRubric rubric2;
    private AssessmentRubric rubric3;
    private AssessmentRubric rubric4;

    private LearningProcessClient learningService;
    private StatusClient statusService;
    private AssessmentRubricClient rubricService;
    private RubricTypeClient  rubricTypeService;
    private ItemRubricClient itemService;
    private UserGroupClient userGroupService;
    private LearningStudentClient learningStudentService;

    @Autowired
    private ObjectMapper mapper;

    private AssessmentRubricItemsForm form1;
    private AssessmentRubricItemsForm form2;
    private AssessmentRubricItemsForm form3;
    private AssessmentRubricItemsForm form4;

    private UserGroupsForm userGroupsForm;
    private ParticipantsForm participantsForm;

    private HorizontalLayout titleSet;
    private Accordion rubricsAccordeon;

    private Accordion usersAndGroupsAccordeon;
    private Accordion userGroupsAndParticipantsAndAccordion;


    @Override
    public void setParameter(BeforeEvent event,
                             @OptionalParameter String parameter) {

        Location location = event.getLocation();
        QueryParameters queryParameters = location
                .getQueryParameters();

        Map<String, List<String>> parametersMap =
                queryParameters.getParameters();

        //parametersMap.forEach((key,value) -> System.out.println(key +"-" + value));


        this.learning_process_id = parametersMap.get("learning_process_id").get(0);

    }

    public RubricConfigurationView(@Autowired LearningProcessClient learningService,
                                   @Autowired StatusClient statusService,
                                   @Autowired AssessmentRubricClient rubricService,
                                   @Autowired RubricTypeClient rubricTypeService,
                                   @Autowired ItemRubricClient itemService,
                                   @Autowired UserGroupClient userGroupService,
                                   @Autowired LearningStudentClient learningStudentService
                                   ) {

        this.learningService = learningService;
        this.statusService = statusService;
        this.rubricService = rubricService;
        this.rubricTypeService = rubricTypeService;
        this.itemService = itemService;
        this.userGroupService = userGroupService;
        this.learningStudentService = learningStudentService;
        this.mapper = mapper;


        setId("rubricconfiguration-view");
        addClassName("rubricconfiguration-view");
        setSizeFull();
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {

        this.currentLearningProcess = learningService.getLearningProcessById(this.learning_process_id);
        List<AssessmentRubric> rubrics = rubricService.rubricsByLearningProcessId(this.learning_process_id);

        this.rubric1 = rubrics.stream().filter(rubric -> rubric.getRubricType().getId()==1).findAny().orElse(null);
        this.rubric2 = rubrics.stream().filter(rubric -> rubric.getRubricType().getId()==2).findAny().orElse(null);
        this.rubric3 = rubrics.stream().filter(rubric -> rubric.getRubricType().getId()==3).findAny().orElse(null);
        this.rubric4 = rubrics.stream().filter(rubric -> rubric.getRubricType().getId()==4).findAny().orElse(null);

        // Instantiate the button filter/button area
        this.configureLearningProcessTitleArea();

        ((Label)titleSet.getComponentAt(0)).setText("Learning Process Title: "+this.currentLearningProcess.getName());

        // Create the tabs with the users and the user groups
        this.createUsersAndGroupsSection();

        // Configure the tabs for the rubrics
        this.createRubricSection();

        // Instantiate the Forms
        this.configureAssessmentRubricItemsActionForm();
        this.configureUserGroupsForm();
        this.configureParticipantsForm();

        // Creates a Div that wraps the grid and the form, gives it a CSS class name, and makes it full size
        //Div content = new Div(grid,form);
        VerticalLayout userGroupsLayout = new VerticalLayout(usersAndGroupsAccordeon);
        userGroupsLayout.setPadding(true);
        userGroupsLayout.setMargin(true);
        userGroupsLayout.setSpacing(true);
        userGroupsLayout.addClassName("card");

        VerticalLayout rubricsLayout= new VerticalLayout(rubricsAccordeon);
        rubricsLayout.setPadding(true);
        rubricsLayout.setMargin(true);
        rubricsLayout.setSpacing(true);
        rubricsLayout.addClassName("card");

        Div content = new Div(new VerticalLayout(userGroupsLayout,rubricsLayout),userGroupsForm, participantsForm, form1, form2,form3,form4);
        content.addClassName("content");
        content.setSizeFull();

        this.add(titleSet,content);
    }

    private void configureLearningProcessTitleArea() {
        HorizontalLayout titleSet = new HorizontalLayout();
        titleSet.addClassName("rubricconfiguration-buttonSet");
        Label learningProcessTitle = new Label();
        titleSet.add(learningProcessTitle);
        this.titleSet =titleSet;
    }

    private void createUsersAndGroupsSection() {
        // BEGIN ACCORDION
        usersAndGroupsAccordeon = new Accordion();

        Paragraph paragraph = new Paragraph();
        paragraph.setText("A CETPE Learning process requires the organization of the students into groups. These groups need to satisfy the following constrains: "
                + "First, you need to create at least 2 user groups per CETPE process. "
                + "The user groups must be well balanced, it means that the difference between the user groups with more students and the one with less members cannot be bigger than 1.");
        VerticalLayout userGroupsTabLayout = new VerticalLayout(paragraph);
        usersAndGroupsAccordeon.add("User groups:", userGroupsTabLayout);

        userGroupsAndParticipantsAndAccordion = new Accordion();
        userGroupService.userGroupsByLearningProcessId(learning_process_id).forEach(userGroup -> {
            VerticalLayout membersLayout = new VerticalLayout();
            userGroup.getLearningStudentList().forEach(student -> {
                membersLayout.add(student.getUsername());
            });
            userGroupsAndParticipantsAndAccordion.add(userGroup.getName(),membersLayout);
        });

        userGroupsAndParticipantsAndAccordion.close();

        Paragraph paragraph2 = new Paragraph();
        paragraph2.setText("The following users are assigned as participants of the current CETPE Learning process. You can use the form to add or remove participants.");
        VerticalLayout listOfGroupsWithStudentsLayout = new VerticalLayout(paragraph2,
                                                                           userGroupsAndParticipantsAndAccordion);

        usersAndGroupsAccordeon.add("Students:", listOfGroupsWithStudentsLayout);

        usersAndGroupsAccordeon.close();
        usersAndGroupsAccordeon.addOpenedChangeListener(event -> {
            rubricsAccordeon.close();
            if (event.getOpenedIndex().isPresent()) {
                if(event.getOpenedIndex().getAsInt()==0) {
                    visualizeForm(this.userGroupsForm);
                }
                else if (event.getOpenedIndex().getAsInt()==1) {
                    participantsForm.getStudentsGrid().setSelectionMode(Grid.SelectionMode.NONE);
                    updateUserGroupsAndParticipantsAndAccordion();
                    userGroupsAndParticipantsAndAccordion.close();
                    visualizeForm(this.participantsForm);
                }
            }
        });
    }

    private void updateUserGroupsAndParticipantsAndAccordion(){

        usersAndGroupsAccordeon.remove(usersAndGroupsAccordeon.getOpenedPanel().get());

        userGroupsAndParticipantsAndAccordion = new Accordion();
        userGroupService.userGroupsByLearningProcessId(learning_process_id).forEach(userGroup -> {
            UserGroupMemberLayout membersLayout = new UserGroupMemberLayout(userGroup.getId().toString());
            Paragraph paragraphMemberLayout = new Paragraph();
            paragraphMemberLayout.setText("The selected user group contains the indicated students. You can use the form to add or remove participants.");
            membersLayout.add(paragraphMemberLayout);
            userGroupsAndParticipantsAndAccordion.add(userGroup.getName(),membersLayout);

            userGroupsAndParticipantsAndAccordion.addOpenedChangeListener(openEvent -> {

                if (openEvent.getOpenedIndex().isPresent()) {
                    participantsForm.getStudentsGrid().setSelectionMode(Grid.SelectionMode.MULTI);
                    ListDataProvider<LearningStudent> mylistProvider = (ListDataProvider<LearningStudent>) participantsForm.getStudentsGrid().getDataProvider();
                    List<LearningStudent> listGridStudents = mylistProvider.getItems().stream().collect(Collectors.toList());
                    String userGroupOpenedId = ((UserGroupMemberLayout)userGroupsAndParticipantsAndAccordion.getOpenedPanel()
                                                                                                            .get()
                                                                                                            .getContent()
                                                                                                            .findFirst()
                                                                                                            .get())
                                                                                                            .getUserGroupId();
                    userGroupService.userGroupById(userGroupOpenedId)
                            .getLearningStudentList()
                            .forEach(student -> {

                                LearningStudent studentToSelect = listGridStudents.stream()
                                                                                  .filter(studentRow -> studentRow.getUsername().equals(student.getUsername()))
                                                                                  .findFirst()
                                                                                  .orElse(null);

                                if (studentToSelect!=null) {
                                    participantsForm.getStudentsGrid()
                                            .asMultiSelect()
                                            .select(listGridStudents.get(listGridStudents.indexOf(studentToSelect)));
                                }
                            });
                }
                else {
                    participantsForm.getStudentsGrid().setSelectionMode(Grid.SelectionMode.NONE);
                    participantsForm.getStudentsGrid().deselectAll();
                }
            });
        });

        Paragraph paragraph2 = new Paragraph();
        paragraph2.setText("The following users are assigned as participants of the current CETPE Learning process. You can use the form to add or remove participants.");
        VerticalLayout listOfGroupsWithStudentsLayout = new VerticalLayout(paragraph2, userGroupsAndParticipantsAndAccordion);

        usersAndGroupsAccordeon.add("Students:", listOfGroupsWithStudentsLayout);
    }

    private void createRubricSection() {
        // BEGIN ACCORDION
        Accordion accordion = new Accordion();

        // RUBRIC 1
        RubricForm rubricForm1 = new RubricForm(this.rubric1);
        rubricForm1.getEnableRubricCheckBox().setVisible(false);
        rubricForm1.getRubricTypeSelector().setItems(rubricTypeService.rubricTypeById("1"));
        rubricForm1.getRubricTypeSelector().setItemLabelGenerator(RubricType::getType);
        rubricForm1.getRubricTypeSelector().setValue(rubricTypeService.rubricTypeById("1"));
        rubricForm1.getConfig().addClickListener(event -> visualizeForm(form1));
        rubricForm1.getUpdate().addClickListener(event -> updateRubric(rubricForm1.getBinderAssessmentForm()));

        accordion.add("Rubric 1: Content Assessment", rubricForm1);

        // RUBRIC 2
        RubricForm rubricForm2 = new RubricForm(rubric2);
        rubricForm2.getEnableRubricCheckBox().setVisible(false);
        rubricForm2.getRubricTypeSelector().setItems(rubricTypeService.rubricTypeById("2"));
        rubricForm2.getRubricTypeSelector().setItemLabelGenerator(RubricType::getType);
        rubricForm2.getRubricTypeSelector().setValue(rubricTypeService.rubricTypeById("2"));
        rubricForm2.getConfig().addClickListener(event -> visualizeForm(form2));
        rubricForm2.getUpdate().addClickListener(event -> updateRubric(rubricForm2.getBinderAssessmentForm()));

        accordion.add("Rubric 2: Evaluators Assessment", rubricForm2);

        // RUBRIC 3
        RubricForm rubricForm3 = new RubricForm(rubric3);
        rubricForm3.getRubricTypeSelector().setItems(rubricTypeService.rubricTypeById("3"));
        rubricForm3.getRubricTypeSelector().setItemLabelGenerator(RubricType::getType);
        rubricForm3.getRubricTypeSelector().setValue(rubricTypeService.rubricTypeById("3"));
        rubricForm3.getConfig().addClickListener(event -> visualizeForm(form3));
        rubricForm3.getUpdate().addClickListener(event -> updateRubric(rubricForm3.getBinderAssessmentForm()));

        accordion.add("Rubric 3: Group Assessment", rubricForm3);

        // RUBRIC 4
        RubricForm rubricForm4 = new RubricForm(rubric4);

        rubricForm4.getRubricTypeSelector().setItems(rubricTypeService.rubricTypeById("4"));
        rubricForm4.getRubricTypeSelector().setItemLabelGenerator(RubricType::getType);
        rubricForm4.getRubricTypeSelector().setValue(rubricTypeService.rubricTypeById("4"));
        rubricForm4.getConfig().addClickListener(event -> visualizeForm(form4));
        rubricForm4.getUpdate().addClickListener(event -> updateRubric(rubricForm4.getBinderAssessmentForm()));

        accordion.add("Rubric 4: Own Group Assessment", rubricForm4);
        accordion.close();
        accordion.addOpenedChangeListener(event -> {

            usersAndGroupsAccordeon.close();
            if (event.getOpenedIndex().isPresent()) {
                if(event.getOpenedIndex().getAsInt()==0) {
                    visualizeForm(form1);
                }
                else if (event.getOpenedIndex().getAsInt()==1) {
                    visualizeForm(form2);
                }
                else if (event.getOpenedIndex().getAsInt()==2) {
                    visualizeForm(form3);
                }
                else if (event.getOpenedIndex().getAsInt()==3) {
                    visualizeForm(form4);
                }
            }

        });
        rubricsAccordeon = accordion;
    }

    private void visualizeForm(VerticalLayout selectedForm) {

        this.userGroupsForm.setVisible(false);
        this.participantsForm.setVisible(false);
        this.form1.setVisible(false);
        this.form2.setVisible(false);
        this.form3.setVisible(false);
        this.form4.setVisible(false);
        if (null!=selectedForm) {
            selectedForm.setVisible(true);
        }
    }

    private void configureUserGroupsForm() {

        this.userGroupsForm = new UserGroupsForm(currentLearningProcess, userGroupService, learningService, mapper);
        this.userGroupsForm.addClassName("card");
        this.userGroupsForm.setVisible(false);
    }

    private void configureParticipantsForm() {

        this.participantsForm = new ParticipantsForm(currentLearningProcess,learningStudentService );
        this.participantsForm.addClassName("card");
        this.participantsForm.getSave().addClickListener(event -> addParticipantsToGroups(participantsForm));
        this.participantsForm.getClose().addClickListener(event -> closeParticipants(participantsForm));
        //this.participantsForm.getUpdate().addClickListener(event -> updateItem(form1));
        this.participantsForm.setVisible(false);
    }

    private void configureAssessmentRubricItemsActionForm(){

        this.form1 = new AssessmentRubricItemsForm(rubric1, itemService); //By default rubric 1
        this.form1.addClassName("card");
        this.form1.getSave().addClickListener(event -> saveNewItem(form1));
        this.form1.getClose().addClickListener(event -> closeNewItem(form1));
        this.form1.getUpdate().addClickListener(event -> updateItem(form1));

        this.form1.setVisible(false);

        this.form2 = new AssessmentRubricItemsForm(rubric2, itemService); //By default rubric 1
        this.form2.addClassName("card");
        this.form2.getSave().addClickListener(event -> saveNewItem(form2));
        this.form2.getClose().addClickListener(event -> closeNewItem(form2));
        this.form2.getUpdate().addClickListener(event -> updateItem(form2));
        this.form2.setVisible(false);

        this.form3 = new AssessmentRubricItemsForm(rubric3, itemService); //By default rubric 1
        this.form3.addClassName("card");
        this.form3.getSave().addClickListener(event -> saveNewItem(form3));
        this.form3.getClose().addClickListener(event -> closeNewItem(form3));
        this.form3.getUpdate().addClickListener(event -> updateItem(form3));
        this.form3.setVisible(false);

        this.form4 = new AssessmentRubricItemsForm(rubric4, itemService); //By default rubric 1
        this.form4.addClassName("card");
        this.form4.getSave().addClickListener(event -> saveNewItem(form4));
        this.form4.getClose().addClickListener(event -> closeNewItem(form4));
        this.form4.getUpdate().addClickListener(event -> updateItem(form4));
        this.form4.setVisible(false);

    }

    private void updateRubric(Binder<AssessmentRubric> currentBinder) {

        if (currentBinder.isValid()) {

            //Without Jackson mapper
            Map<String, Object> rubricObjMapped = currentBinder.getBean().toMap();

            if (null == rubricService.update(currentBinder.getBean().getId().toString(),rubricObjMapped)) {
                Notification notification = new Notification("Validation succeeded but update operation fails, please contact the administrator of the tool", 3000);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.open();
            } else {
                Notification notification = new Notification("Assessment Rubric successfully updated", 3000);
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                notification.open();
            }
        }
        else { //Validation fails
            Notification notification = new Notification("Validation fails, please review the input values and try again", 3000);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        }
    }

    private void closeNewItem(AssessmentRubricItemsForm currentForm) {
        currentForm.resetFields();
        currentForm.setEditMode(false);
        currentForm.setVisible(false);
    }

    private void saveNewItem(AssessmentRubricItemsForm currentForm) {
        if (currentForm.getBinderNewItemRubricForm().isValid()) {

            Map<String, Object> itemRubricObjMapped = currentForm.getBinderNewItemRubricForm().getBean().toMap();
            ItemRubric newItem=null;
            if (itemRubricObjMapped==null) {
                Notification notification = new Notification("Data input is null, please contact the administrator of the tool", 3000);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.open();
                return;
            }

            newItem = itemService.create(itemRubricObjMapped);
            if (null == newItem) {
                    Notification notification = new Notification("Validation succeeded but the operation fails, please contact the administrator of the tool", 3000);
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    notification.open();
                } else {
                    Notification notification = new Notification("New item created", 3000);
                    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    currentForm.resetFields();
                    currentForm.addItemToLayout(newItem);
                    notification.open();
                }
        }
        else { //Validation fails
            Notification notification = new Notification("Validation fails, please review the input values and try again", 3000);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        }

    }

    private void updateItem(AssessmentRubricItemsForm currentForm) {
        if (currentForm.getBinderNewItemRubricForm().isValid()) {

            Map<String, Object> itemRubricObjMapped = currentForm.getBinderNewItemRubricForm().getBean().toMap();
            ItemRubric editableItem=null;
            if (itemRubricObjMapped==null) {
                Notification notification = new Notification("Data input is null, please contact the administrator of the tool", 3000);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.open();
                return;
            }

            editableItem = itemService.update(currentForm.getBinderNewItemRubricForm().getBean().getId().toString(),itemRubricObjMapped);
            if (null == editableItem) {
                Notification notification = new Notification("Validation succeeded but the operation fails, please contact the administrator of the tool", 3000);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.open();
            } else {
                Notification notification = new Notification("Item updated", 3000);
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                currentForm.resetFields();
                currentForm.refreshItemList();
                notification.open();
            }
        }
        else { //Validation fails
            Notification notification = new Notification("ERROR Validation fails, please review this input values and try again", 3000);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        }

    }

    private void closeParticipants(ParticipantsForm currentForm) {
        currentForm.setVisible(false);
    }

    private void addParticipantsToGroups(ParticipantsForm currentForms){

        UserGroupMemberLayout userGroupPanel = (UserGroupMemberLayout)userGroupsAndParticipantsAndAccordion.getOpenedPanel()
                                                                                                           .orElse(null)
                                                                                                           .getContent()
                                                                                                           .findFirst()
                                                                                                           .orElse(null);

        UserGroup currentUserGroup = userGroupService.userGroupById(userGroupPanel.getUserGroupId());
        Map<String, Object> userGroupToRemove = this.mapper.convertValue(currentUserGroup, Map.class);

        if (userGroupService.updateRemoveLearningStudent(userGroupPanel.getUserGroupId(), userGroupToRemove)) {
            Notification notification = new Notification("Reset user group id  "+ userGroupPanel.getUserGroupId(), 3000);
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.open();
        }
        else {
            Notification notification = new Notification("Error refreshing user group with id  "+ userGroupPanel.getUserGroupId(), 3000);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        }

        currentUserGroup.getLearningStudentList().removeIf(learningStudent -> true);


        // Before adding new participants we remove all previously added members
        participantsForm.getStudentsGrid().getSelectedItems().forEach( participant -> {

            if (!currentUserGroup.getLearningStudentList().contains(participant)) {
                currentUserGroup.getLearningStudentList().add(participant);

                Map<String, Object>  userGroup = this.mapper.convertValue(currentUserGroup, Map.class);

                if (null!=userGroupService.updateAddLearningStudent(userGroupPanel.getUserGroupId(), userGroup)) {

                    Notification notification = new Notification("Added student "+ participant.getUsername() +" to user group "+ userGroupPanel.getUserGroupId(), 3000);
                    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    notification.open();
                }
                else {
                    Notification notification = new Notification("Error adding student "+ participant.getUsername() +" to user group "+ userGroupPanel.getUserGroupId(), 3000);
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    notification.open();
                }
            }
            else {
                Notification notification = new Notification("The student "+ participant.getUsername() +"  is already assigned to the user group "+ userGroupPanel.getUserGroupId(), 3000);
                notification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
                notification.open();
            }
        });
    }
}