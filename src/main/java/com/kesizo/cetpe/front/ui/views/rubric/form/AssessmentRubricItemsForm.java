package com.kesizo.cetpe.front.ui.views.rubric.form;

import com.kesizo.cetpe.front.controller.client.ItemRubricClient;
import com.kesizo.cetpe.front.controller.dtos.AssessmentRubric;
import com.kesizo.cetpe.front.controller.dtos.ItemRubric;
import com.kesizo.cetpe.front.ui.components.ItemElementLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.richtexteditor.RichTextEditor;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.Binder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AssessmentRubricItemsForm extends VerticalLayout { // extends FormLayout: a responsive layout that shows form fields in 1 or 2 columns depending on viewport width.

    private RichTextEditor descriptionText = new RichTextEditor("Description");
    private IntegerField weightA = new IntegerField("Item Value %");
    private AssessmentRubric currentRubric;

    private VerticalLayout itemListLayout = new VerticalLayout();

    private Button save = new Button("Save");
    private Button update = new Button("Update");
    private Button close = new Button("Cancel");
    private List<ItemRubric> itemList = new ArrayList<>();
    private ItemRubricClient itemInterfaceService;

    private Binder<ItemRubric> binderNewItemRubricForm = new Binder<>();
    private ItemRubric itemRubricFormBean = new ItemRubric();

    public AssessmentRubricItemsForm(AssessmentRubric currentRubric, ItemRubricClient itemInterfaceService) {

        this.setMargin(true);
        this.addClassName("assessment-item-rubric-form"); // Gives the component a CSS class name so we can style it later

        this.currentRubric = currentRubric;
        this.itemInterfaceService = itemInterfaceService;
        itemRubricFormBean.setAssessmentRubric(currentRubric);
        binderNewItemRubricForm.setBean(itemRubricFormBean);

        descriptionText.setId("item-rubric-descriptionText");
        binderNewItemRubricForm.forField(descriptionText)
                .withValidator(string -> string != null && !string.isEmpty(), "Item description cannot be empty")
                .withValidator(name -> name.length() > 1, "Item description must contain at least 1 characters")
                .withValidator(name -> name.length() < 2048, "Item description cannot be bigger than 2048 characters")
                .bind(itemRubric -> itemRubric.getDescription(),
                        ((itemRubric, formValue) -> itemRubric.setDescription(formValue)));

        weightA.setId("item-rubric-weight-A");
        weightA.setWidth("100px");
        weightA.setHasControls(true);
        weightA.setStep(5);
        weightA.setMin(0);
        weightA.setMax(100);
        weightA.setValue(0);
        binderNewItemRubricForm.forField(weightA)
                .withValidator(integer -> integer != null && integer.intValue() > 0, "Item value must be bigger than 0")
                .bind(ItemRubric::getWeight, ItemRubric::setWeight);

        itemList = this.itemInterfaceService.itemRubricByLearningProcessIdOrRubricId(currentRubric.getLearningProcess().getId().toString(), currentRubric.getId().toString());
        itemList.stream().sorted((o1, o2) -> o1.getId().compareTo(o2.getId()))
                .collect(Collectors.toList())
                .forEach(item -> {
                    ItemElementLayout currentElement = new ItemElementLayout(item);
                    currentElement.getDeleteButton().addClickListener(event -> deleteItem(currentElement));
                    currentElement.getItemDescriptionParagraph().addClickListener(event -> {

                        itemRubricFormBean = new ItemRubric();
                        itemRubricFormBean.setAssessmentRubric(currentRubric);
                        itemRubricFormBean.setId(item.getId());
                        itemRubricFormBean.setDescription(item.getDescription());
                        itemRubricFormBean.setWeight(item.getWeight());
                        binderNewItemRubricForm.setBean(itemRubricFormBean);
                        setEditMode(true);
                    });
                    itemListLayout.add(currentElement);
                });

        itemListLayout.setWidthFull();

        HorizontalLayout hlItemInfo = new HorizontalLayout(descriptionText, weightA);
        hlItemInfo.setWidthFull();
        hlItemInfo.expand(descriptionText);
        this.add(
                hlItemInfo,
                createButtonsLayout(),
                itemListLayout); // Adds all the UI components. The buttons require a bit of extra configuration so we create and call a new method, createButtonsLayout()
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

    public Binder<ItemRubric> getBinderNewItemRubricForm() {
        return binderNewItemRubricForm;
    }

    public Button getClose() {
        return close;
    }

    public Button getUpdate() {
        return update;
    }

    public Button getSave() {
        return save;
    }

    public void resetFields() {
        this.binderNewItemRubricForm.setBean(null);
        itemRubricFormBean = new ItemRubric();
        itemRubricFormBean.setAssessmentRubric(this.currentRubric);
        binderNewItemRubricForm.setBean(itemRubricFormBean);
        setEditMode(false);
    }


    public void setEditMode(boolean isEdit) {
        this.save.setVisible(!isEdit);
        this.update.setVisible(isEdit);
    }

    public void addItemToLayout(ItemRubric newItem) {

        itemList.add(newItem);
        ItemElementLayout newItemLayout = new ItemElementLayout(newItem);
        newItemLayout.getDeleteButton().addClickListener(event -> deleteItem(newItemLayout));
        newItemLayout.getItemDescriptionParagraph().addClickListener(event -> {

            itemRubricFormBean = new ItemRubric();
            itemRubricFormBean.setAssessmentRubric(currentRubric);
            itemRubricFormBean.setId(newItem.getId());
            itemRubricFormBean.setDescription(newItem.getDescription());
            itemRubricFormBean.setWeight(newItem.getWeight());
            binderNewItemRubricForm.setBean(itemRubricFormBean);
            setEditMode(true);
        });
        this.itemListLayout.add(newItemLayout);
    }

    private void deleteItem(ItemElementLayout currentElementLayout) {
        String itemId = currentElementLayout.getCurrentItem().getId().toString();
        if (!itemInterfaceService.delete(itemId)) {
            Notification notification = new Notification("Delete operation fails, please contact the administrator of the tool", 3000);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        } else {
            Notification notification = new Notification("Selected item successfully deleted ", 3000);
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.open();
            itemListLayout.remove(currentElementLayout);
            itemList.remove(currentElementLayout.getCurrentItem());
            resetFields();
        }
    }

    public void refreshItemList() {
        itemListLayout.removeAll();
        itemList = this.itemInterfaceService.itemRubricByLearningProcessIdOrRubricId(currentRubric.getLearningProcess().getId().toString(), currentRubric.getId().toString());
        itemList.stream()
                .sorted((o1, o2) -> o1.getId().compareTo(o2.getId()))
                .collect(Collectors.toList())
                .forEach(item -> {
                    ItemElementLayout currentElement = new ItemElementLayout(item);
                    currentElement.getDeleteButton().addClickListener(event -> deleteItem(currentElement));
                    currentElement.getItemDescriptionParagraph().addClickListener(event -> {
                        itemRubricFormBean = new ItemRubric();
                        itemRubricFormBean.setAssessmentRubric(currentRubric);
                        itemRubricFormBean.setId(item.getId());
                        itemRubricFormBean.setDescription(item.getDescription());
                        itemRubricFormBean.setWeight(item.getWeight());
                        binderNewItemRubricForm.setBean(itemRubricFormBean);
                        setEditMode(true);
                    });
                    itemListLayout.add(currentElement);
                });
    }

}
