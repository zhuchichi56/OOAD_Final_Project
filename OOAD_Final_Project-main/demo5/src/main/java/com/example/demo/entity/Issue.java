package com.example.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Issue {
    String issueId;
    String title;
    List<Comment> content;
    String creator;
    Date publishTime;
}
