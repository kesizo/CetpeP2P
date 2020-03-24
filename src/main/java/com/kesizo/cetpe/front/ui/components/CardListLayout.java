package com.kesizo.cetpe.front.ui.components;

import com.kesizo.cetpe.front.controller.dtos.LearningProcess;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.IronIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CardListLayout extends HorizontalLayout {

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
        Span date = new Span(lprocess.getStartingDateTime());
        date.addClassName("date");
        header.add(name, date);

        Span summary = new Span(lprocess.getDescription());
        summary.addClassName("post");

        HorizontalLayout actions = new HorizontalLayout();
        actions.addClassName("actions");
        actions.setSpacing(false);

        IronIcon likeIcon = new IronIcon("vaadin", "heart");
        Span likes = new Span("3");
        likes.addClassName("likes");
        IronIcon commentIcon = new IronIcon("vaadin", "comment");
        Span comments = new Span("23");
        comments.addClassName("comments");
        IronIcon shareIcon = new IronIcon("vaadin", "connect");
        Span shares = new Span("5");
        shares.addClassName("shares");

        actions.add(likeIcon, likes, commentIcon, comments, shareIcon, shares);

        description.add(header, summary, actions);
        this.add(image, description);
    }
}
