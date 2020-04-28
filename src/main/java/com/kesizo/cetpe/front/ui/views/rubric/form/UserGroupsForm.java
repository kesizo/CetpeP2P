package com.kesizo.cetpe.front.ui.views.rubric.form;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kesizo.cetpe.front.controller.client.LearningProcessClient;
import com.kesizo.cetpe.front.controller.client.UserGroupClient;
import com.kesizo.cetpe.front.controller.dtos.LearningProcess;
import com.kesizo.cetpe.front.controller.dtos.UserGroup;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;


public class UserGroupsForm extends VerticalLayout { // extends FormLayout: a responsive layout that shows form fields in 1 or 2 columns depending on viewport width.

    private TextField userGroupTextField = new TextField("User Group Name");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button update = new Button("Update");
    private Button close = new Button("Cancel");

    private Grid<UserGroup> userGroupGrid = new Grid<>(UserGroup.class);
    private Binder<UserGroup> binderNewUserGroupForm = new Binder<>();
    private UserGroup userGroupFormBean = new UserGroup();
    private LearningProcess currentLearningProcess;
    private UserGroupClient userGroupService;
    private LearningProcessClient learningProcessService;

    private ObjectMapper mapper; // Used for converting Objects to/from JSON

    public UserGroupsForm(LearningProcess learningProcess,
                          UserGroupClient userGroupService,
                          LearningProcessClient learningProcessService,
                          ObjectMapper mapper
    ) {

        this.setMargin(true);
        this.addClassName("user-group-form"); // Gives the component a CSS class name so we can style it later
        this.userGroupService = userGroupService;
        this.learningProcessService = learningProcessService;
        this.mapper = mapper;
        currentLearningProcess = learningProcess;

        //Configuring the binder
        userGroupFormBean.setLearningProcess(learningProcess);
        binderNewUserGroupForm.setBean(userGroupFormBean);

        userGroupTextField.setId("user-group-descriptionText");
        binderNewUserGroupForm.forField(userGroupTextField)
                .withValidator(string -> string != null && !string.isEmpty(), "User group name cannot be empty")
                .withValidator(name -> name.length() > 3, "User group name must contain at least 3 characters")
                .withValidator(name -> name.length() < 256, "User group name cannot be bigger than 256 characters")
                .bind(userGroup -> userGroup.getName(),
                        ((userGroup, formValue) -> userGroup.setName(formValue)));

        configureGrid();

        updateList();

        H2 header = new H2("User groups editor");
        HorizontalLayout hlItemInfo = new HorizontalLayout(userGroupTextField);
        hlItemInfo.setWidthFull();
        hlItemInfo.expand(userGroupTextField);
        this.add(
                header,
                hlItemInfo,
                createButtonsLayout(),
                userGroupGrid); // Adds all the UI components. The buttons require a bit of extra configuration so we create and call a new method, createButtonsLayout()

        this.close.addClickListener(event -> closeUserGroup());
        this.save.addClickListener(event -> saveNewUserGroup());
        this.update.addClickListener(event -> updateUserGroup());
        this.delete.addClickListener(event -> deleteUserGroup());

    }

    private void configureGrid() {

        this.userGroupGrid.setHeight("100%");
        this.userGroupGrid.setVisible(true);
        this.userGroupGrid.setColumns("name", "authorizedStudent");
        this.userGroupGrid.addThemeVariants(GridVariant.LUMO_COMPACT);
        this.userGroupGrid.addItemClickListener(event -> {
            UserGroup updatableUserGroup = userGroupService.userGroupById(String.valueOf(event.getItem().getId()));
            getBinderNewUserGroupForm().setBean(null);
            getBinderNewUserGroupForm().setBean(updatableUserGroup);
            setEditionMode(true);
        });
    }

    public void updateList() {

        this.userGroupGrid.setItems(userGroupService.userGroupsByLearningProcessId(String.valueOf(currentLearningProcess.getId())).stream()
                                                                                                                                  .sorted((o1, o2) -> o1.getId().compareTo(o2.getId()))
                                                                                                                                  .collect(Collectors.toList()));
    }

    private HorizontalLayout createButtonsLayout() {

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY); // Makes the buttons visually distinct from each other using built-in theme variants
        update.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        close.addClickShortcut(Key.ESCAPE);
        update.setVisible(false);
        delete.setVisible(false);

        HorizontalLayout buttonSet = new HorizontalLayout(save, update, delete, close);
        buttonSet.setMargin(true);

        return buttonSet; // Returns a HorizontalLayout containing the buttons to place them next to each other
    }

    public Grid<UserGroup> getUserGroupGrid() {
        return userGroupGrid;
    }

    public Binder<UserGroup> getBinderNewUserGroupForm() {
        return binderNewUserGroupForm;
    }

    public void resetFields(){
        this.binderNewUserGroupForm.setBean(null);
        UserGroup userGroupFormBean = new UserGroup();
        userGroupFormBean.setLearningProcess(currentLearningProcess);
        binderNewUserGroupForm.setBean(userGroupFormBean);
    }

    public void setEditionMode(boolean isEditionMode){
        this.delete.setVisible(isEditionMode);
        this.save.setVisible(!isEditionMode);
        this.update.setVisible(isEditionMode);
        this.delete.setVisible(isEditionMode);
    }

    private void saveNewUserGroup() {

        if (this.getBinderNewUserGroupForm().isValid()) {
            LearningProcess toAddUserGroupLearningProcess = learningProcessService.getLearningProcessById(currentLearningProcess.getId().toString());
            UserGroup newUserGroup = this.getBinderNewUserGroupForm().getBean();
            newUserGroup.setLearningStudentList(new ArrayList<>());
            toAddUserGroupLearningProcess.getUserGroupList().removeIf(elem -> true);
            toAddUserGroupLearningProcess.addUserGroup(newUserGroup);

            Map<String, Object> learningProcessObjMapped = this.mapper.convertValue(toAddUserGroupLearningProcess, Map.class);

            if (learningProcessObjMapped==null) {
                Notification notification = new Notification("Data input is null, please contact the administrator of the tool", 3000);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.open();
            }

            if (null == this.learningProcessService.updateAddUserGroup(currentLearningProcess.getId().toString(),learningProcessObjMapped)) {
                Notification notification = new Notification("Validation succeeded but the operation fails, please contact the administrator of the tool", 3000);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.open();
            } else {
                Notification notification = new Notification("New User Group created", 3000);
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                this.updateList();
                notification.open();
            }
        }
        else { //Validation fails
            Notification notification = new Notification("Validation fails, please review the input values and try again", 3000);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        }

    }

    private void deleteUserGroup() {
        if (!userGroupService.delete(this.getBinderNewUserGroupForm().getBean().getId().toString())) {
            Notification notification = new Notification("Delete operation fails, please contact the administrator of the tool", 3000);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        } else {
            Notification notification = new Notification("Selected user group successfully deleted ", 3000);
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            this.resetFields();
            this.updateList();
            this.setEditionMode(false);
            notification.open();
        }
    }

    private void updateUserGroup() {

        if (this.getBinderNewUserGroupForm().isValid()) {

            Map<String, Object> userGroupObjMapped = this.mapper.convertValue(this.getBinderNewUserGroupForm().getBean(), Map.class);

            //Without Jackson mapper
            //Map<String, Object> userGroupObjMapped = this.getBinderNewUserGroupForm().getBean().toMap();

            if (null == userGroupService.update(this.getBinderNewUserGroupForm().getBean().getId().toString(),userGroupObjMapped)) {
                Notification notification = new Notification("Validation succeeded but update operation fails, please contact the administrator of the tool", 3000);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.open();
            } else {
                Notification notification = new Notification("User Group successfully updated", 3000);
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                this.resetFields();
                this.updateList();
                this.setEditionMode(false);
                notification.open();
            }
        }
        else { //Validation fails
            Notification notification = new Notification("Validation fails, please review the input values and try again", 3000);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        }
    }

    private void closeUserGroup() {
        this.resetFields();
        if (update.isVisible()){
            this.setEditionMode(false);
        }
        else {
            this.setVisible(false);
        }

    }
}



