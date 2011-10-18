package org.jboss.seam.rest.test.client;

import javax.inject.Inject;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.seam.rest.client.RestClient;

public class InjectedBean {
    @Inject
    @RestClient("http://localhost:8080/test/ping")
    private ClientRequest request;

    @Inject
    @RestClient("http://#{service.host}:8080/#{service.context.path}/ping")
    private ClientRequest requestWithEl;

    @Inject
    @RestClient("http://localhost:8080/test")
    private TaskService taskService;

    @Inject
    @RestClient("http://localhost:8080/test")
    private PingService pingService;

    public ClientRequest getRequest() {
        return request;
    }

    public int createTask() {
        return taskService.createTask(new Task(1, "foo", "bar")).getStatus();
    }

    public Task getTask() {
        return taskService.getTask(1, 2, 3);
    }

    public String ping() {
        return pingService.ping();
    }

    public ClientRequest getRequestWithEl() {
        return requestWithEl;
    }
}
