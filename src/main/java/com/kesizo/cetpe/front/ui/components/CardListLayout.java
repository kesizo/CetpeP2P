package com.kesizo.cetpe.front.ui.components;

import com.kesizo.cetpe.front.controller.dtos.LearningProcess;
import com.kesizo.cetpe.front.controller.dtos.UserGroup;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.IronIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.time.format.DateTimeFormatter;

public class CardListLayout extends HorizontalLayout {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyyy");

    public CardListLayout(LearningProcess lprocess) {
        super();
        this.addClassName("card");
        this.setSpacing(false);

        Image image = new Image();
        //image.setSrc(person.getImage());
        image.setSrc("https://kesizo.github.io/assets/images/kesizo-logo-6-832x834.png");
        VerticalLayout description = new VerticalLayout();
        description.addClassName("description");
        description.setSpacing(false);
        description.setPadding(false);

        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("header-card");
        header.setSpacing(false);
        Span name = new Span(lprocess.getName());
        name.addClassName("name");
        Span date = new Span(" "+(lprocess.getStarting_date_time() !=null ?
                             lprocess.getStarting_date_time().format(formatter) :
                             "Not available") +
                " - " +
                                (lprocess.getEnd_date_time() !=null ?
                                        lprocess.getEnd_date_time().format(formatter) :
                                 "Not available")
                );
        date.addClassName("date");
        header.add(name, date);

        Span summary = new Span(lprocess.getDescription());
        summary.addClassName("post");

        HorizontalLayout actions = new HorizontalLayout();
        actions.addClassName("actions");
        actions.setSpacing(false);

        IronIcon usersIcon = new IronIcon("vaadin", "user");
        usersIcon.getElement().setProperty("title", "Students involved in the learning process");
        Span users = new Span(lprocess.getUserGroupList() != null ?
                                            String.valueOf(lprocess.getUserGroupList().stream()
                                                                                      .map(UserGroup::getLearningStudentList)
                                                                                      .map(list -> list.size())
                                                                                      .reduce(0, Integer::sum))
                                            : "0");
        users.addClassName("process-icon");

        IronIcon groupsIcon = new IronIcon("vaadin", "group");
        groupsIcon.getElement().setProperty("title", "User groups");
        Span groups = new Span(lprocess.getUserGroupList() != null ? String.valueOf(lprocess.getUserGroupList().size()) : "0");
        groups.addClassName("process-icon");

        IronIcon answersIcon = new IronIcon("vaadin", "comment");
        answersIcon.getElement().setProperty("title", "Available answers");
        Span answers = new Span("5");
        answers.addClassName("process-icon");

        IronIcon supervisorIcon = new IronIcon("vaadin", "gavel");
        supervisorIcon.getElement().setProperty("title", "Learning process administrator");
        Span supervisor = new Span(lprocess.getLearning_supervisor() != null ? lprocess.getLearning_supervisor().getUsername() : "null");
        supervisor.addClassName("process-icon");

        actions.add(usersIcon, users, groupsIcon, groups, answersIcon, answers, supervisorIcon, supervisor);

        description.add(header, summary, actions);
        this.add(image, description);
    }
}
