/*
 * Copyright (C) 2016-2016 52°North Initiative for Geospatial Open Source
 * Software GmbH
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 as published
 * by the Free Software Foundation.
 *
 * If the program is linked with libraries which are licensed under one of
 * the following licenses, the combination of the program with the linked
 * library is not considered a "derivative work" of the program:
 *
 *     - Apache License, version 2.0
 *     - Apache Software License, version 1.0
 *     - GNU Lesser General Public License, version 3
 *     - Mozilla Public License, versions 1.0, 1.1 and 2.0
 *     - Common Development and Distribution License (CDDL), version 1.0
 *
 * Therefore the distribution of the program linked with libraries licensed
 * under the aforementioned licenses, is permitted by the copyright holders
 * if the distribution is compliant with both the GNU General Public License
 * version 2 and the aforementioned licenses.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 */
package org.n52.tasking.data.sml.task;

import org.hamcrest.MatcherAssert;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.Before;
import org.junit.Test;
import org.n52.tasking.data.cmd.CreateTask;
import org.n52.tasking.data.entity.Task;
import org.n52.tasking.data.repository.TaskRepository;

public class InMemoryTaskRepositoryTest {

    private TaskRepository repository;

    @Before
    public void setUp() {
        repository = new InMemoryTaskRepository();
    }

    @Test
    public void when_emptyRepository_then_emptyList() {
        assertThat(repository.getTasks(), is(empty()));
    }

    @Test
    public void when_addingTask_then_expectNonNullTask() {
        CreateTask cmd = new CreateTask();
        cmd.setId("42");
        cmd.setParameters("some-fancy-parameters-here");
        assertThat(repository.createTask(cmd), notNullValue(Task.class));
    }

    @Test
    public void when_addingTask_then_expectValidTask() {
        final String id = "42";
        final String parameters = "some-fancy-parameters-here";

        CreateTask cmd = new CreateTask();
        cmd.setId(id);
        cmd.setParameters(parameters);
        Task task = repository.createTask(cmd);
        assertThat(task.getId(), is(id));
        assertThat(task.getEncodedParameters(), is(parameters));
    }

    @Test
    public void when_addingInvalidTask_then_expectRejectedTask() {
        final String id = "42";
        final String parameters = "some-fancy-parameters-here";
    }
}