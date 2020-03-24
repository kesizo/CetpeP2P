package com.kesizo.cetpe.front.ui.views.mylearningprocess;

import com.kesizo.cetpe.front.controller.client.LearningProcessClient;
import com.kesizo.cetpe.front.controller.dtos.LearningProcess;
import com.kesizo.cetpe.front.ui.MainLayout;
import com.kesizo.cetpe.front.ui.components.CardListLayout;
import com.kesizo.cetpe.front.ui.views.mylearningprocess.form.LearningProcessForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Route(value = "mylearningprocess", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle("Assessments | CETPE P2P")
@CssImport(value = "styles/views/mylearningprocess/mylearningprocess-view.css", include = "lumo-badge")
@JsModule("@vaadin/vaadin-lumo-styles/badge.js")
public class MyLearningProcessesView extends Div implements AfterNavigationObserver {

    private LearningProcessClient learningService;
    private Grid<LearningProcess> grid = new Grid<>();
    private HorizontalLayout buttonSet;
    private TextField filterText = new TextField();
    private Button addLearningProcessButton;
    private LearningProcessForm form;

    public MyLearningProcessesView(@Autowired LearningProcessClient learningService) {

        this.learningService = learningService;

        setId("mylearningprocess-view");
        addClassName("mylearningprocess-view");
        setSizeFull();

        // Instantiate the button filter/button area
        this.buttonSet = createActionSetArea();

        // Configure the grid with the cards
        this.grid.addClassName("grid-cards");
        this.grid.setHeight("100%");
        this.grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        this.grid.addComponentColumn(CardListLayout::new);

        // Instantiate the Form
        this.form = new LearningProcessForm();
        this.form.setVisible(false);

        Div content = new Div(grid,form); // Creates a Div that wraps the grid and the form, gives it a CSS class name, and makes it full size
        content.addClassName("content");
        content.setSizeFull();

        add(buttonSet, content);

    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {

        grid.setItems(learningService.getLearningProcess());
    }

    private HorizontalLayout createActionSetArea() {
        HorizontalLayout buttonSet = new HorizontalLayout();
        buttonSet.addClassName("mylearningprocess-buttonSet");

        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        addLearningProcessButton = new Button("+", click -> toggleAddLearningProcessMenu());

        buttonSet.add(filterText, addLearningProcessButton);
        return buttonSet;
    }

    private void updateList() {

        grid.setItems(learningService.getLearningProcess()
                                      .stream()
                                      .filter(learningProcess -> learningProcess.getName().contains(filterText.getValue()))
                                      .collect(Collectors.toList()));
    }

    private void toggleAddLearningProcessMenu(){
        form.setVisible(!form.isVisible());
    }

}
