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
package org.n52.tasking.core.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.n52.tasking.data.cmd.CreateTask;
import org.n52.tasking.data.entity.Task;
import org.n52.tasking.data.repository.TaskRepository;

public class TaskService {

    private TaskRepository repository;

    public List<Resource> getTasks(String fullUrl) {
        final Function<Task, Resource> toResource =  dm
                -> Resource.aResource(dm.getId())
                .withHref(createHref(fullUrl, dm.getId()));

        return this.repository.getTasks()
                .stream()
                .map(toResource)
                .collect(Collectors.toList());
    }

    public Object getTask(String id) throws UnknownItemException {
        if (!this.repository.hasTask(id)) {
            throw new UnknownItemException("Not found");
        }

        return this.repository.getTask(id);
    }
    
    public Resource createTask(CreateTask createTask, String fullUrl) {
        Task task = this.repository.createTask(createTask);
        return Resource.aResource(task.getId())
                .withHref(createHref(fullUrl, task.getId()));
    }

    private String createHref(String fullUrl, String id) {
        return String.format("%s/%s", fullUrl, id);
    }

    public TaskRepository getRepository() {
        return repository;
    }

    public void setRepository(TaskRepository repository) {
        this.repository = repository;
    }


}