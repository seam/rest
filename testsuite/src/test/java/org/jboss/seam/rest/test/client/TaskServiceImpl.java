package org.jboss.seam.rest.test.client;

import javax.ws.rs.core.Response;

public class TaskServiceImpl implements TaskService {
    public Response createTask(Task task) {
        if ((task.getId() == 1) && ("foo".equals(task.getName())) && ("bar".equals(task.getDescription()))) {
            return Response.status(200).build();
        } else {
            return Response.status(400).build();
        }
    }

    public Task getTask(int a, int b, int c) {
        if (a + b + c == 6) {
            return new Task(2, "alpha", "bravo");
        } else {
            return null;
        }
    }
}
