package com.thomshutt.runbud.core;

import javax.persistence.*;

@Entity
@Table(name = "runs")
public class Run {

    @Id
    @Column(name = "runId")
    private String runId;

    public Run() {
    }

    public Run(String runId) {
        this.runId = runId;
    }

    public String getName() {
        return runId;
    }

}
