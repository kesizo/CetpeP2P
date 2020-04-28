package com.kesizo.cetpe.front.ui.components;

import com.kesizo.cetpe.front.controller.dtos.ItemRubric;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class ItemElementLayout extends HorizontalLayout {

    private ItemRubric currentItem;
    private Label itemIdLabel = new Label();

    public Paragraph getItemDescriptionParagraph() {
        return itemDescriptionParagraph;
    }

    private Paragraph itemDescriptionParagraph = new Paragraph();
    private Label weightLabel = new Label();
    private Button deleteButton = new Button();

    public ItemRubric getCurrentItem() {
        return currentItem;
    }

    public ItemElementLayout(ItemRubric currentItem) {
        this.currentItem = currentItem;
        if (currentItem==null) {
            return;
        }
        this.itemDescriptionParagraph.setText(currentItem.getId().toString());
        this.itemDescriptionParagraph.setText(currentItem.getDescription());
        this.weightLabel.setText(String.valueOf(currentItem.getWeight()));

        this.deleteButton.setIcon(new Icon(VaadinIcon.CLOSE));

        this.setWidthFull();

        this.add(itemDescriptionParagraph,weightLabel,deleteButton);
        this.expand(itemDescriptionParagraph);
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

}
