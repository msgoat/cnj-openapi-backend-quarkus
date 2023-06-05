package group.msg.at.cloud.cloudtrain.adapter.rest;

import group.msg.at.cloud.cloudtrain.core.boundary.TaskManagement;
import group.msg.at.cloud.cloudtrain.core.entity.Task;
import group.msg.at.cloud.common.rest.uri.RouterAwareUriBuilderFactory;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.UUID;

/**
 * REST endpoint managing {@link Task} entities.
 * <p>
 * Handles only the mapping of a REST invocation to a Java method invocation;
 * all transactional business logic is encapsulated in a {@code Boundary} this resource class delegates to.
 * </p>
 */
@RequestScoped
@Path("v1/tasks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("CLOUDTRAIN_USER")
@SecurityRequirement(name = "oidc", scopes = { "openid" ,"microprofile-jwt" })
public class TasksResource {

    @Context
    private UriInfo uriInfo;

    @Context
    private HttpHeaders httpHeaders;

    @Inject
    private TaskManagement boundary;

    @GET
    @Operation(summary = "returns all tasks")
    @APIResponse(responseCode = "200",
            description = "body contains all available tasks",
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(ref = "#/components/schema/Task")))
    public Response getAllTasks() {
        Response result;
        List<Task> found = this.boundary.getAllTasks();
        result = Response.ok(found).build();
        return result;
    }

    @GET
    @Path("{taskId}")
    @Operation(summary = "returns the task with the given task ID")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "body contains the task with the given task ID",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "#/components/schema/Task"))),
            @APIResponse(responseCode = "400",
                    description = "unable to find a task with the given task ID; body is empty")
    })
    public Response getTask(@PathParam("taskId") UUID taskId) {
        Response result;
        Task found = this.boundary.getTaskById(taskId);
        if (found != null) {
            result = Response.ok(found).build();
        } else {
            result = Response.status(Response.Status.NOT_FOUND).build();
        }
        return result;
    }

    @POST
    @Operation(summary = "creates a new task based on the given task data and returns its location URI")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "task could be created successfully; header location contains URI of newly created task; body is empty",
                    headers = {@Header(name = "Location", description = "URI of newly created task")}
            ),
            @APIResponse(responseCode = "400", description = "failed to create new task due to invalid task data"),
            @APIResponse(responseCode = "500", description = "failed to create new task due to internal error")
    })
    public Response addTask(Task task) {
        Response result;
        UUID taskId = this.boundary.addTask(task);
        URI location = RouterAwareUriBuilderFactory.from(uriInfo, httpHeaders).path("{taskId}").build(taskId);
        result = Response.created(location).build();
        return result;
    }

    @PUT
    @Path("{taskId}")
    @Operation(summary = "updates the given task")
    @APIResponses({
            @APIResponse(responseCode = "204",
                    description = "task could be updated successfully; body is empty"
            ),
            @APIResponse(responseCode = "400", description = "failed to update task due to invalid task data"),
            @APIResponse(responseCode = "500", description = "failed to create new task due to internal error")
    })
    public Response modifyTask(@PathParam("taskId") UUID taskId, Task task) {
        Response result;
        this.boundary.modifyTask(task);
        result = Response.noContent().build();
        return result;
    }

    @DELETE
    @Path("{taskId}")
    @Operation(summary = "deletes the given task")
    @APIResponses({
            @APIResponse(responseCode = "204",
                    description = "task could be deleted successfully; body is empty"
            ),
            @APIResponse(responseCode = "500", description = "failed to delete task due to internal error")
    })
    public Response removeTask(@PathParam("taskId") UUID taskId) {
        Response result;
        this.boundary.removeTask(taskId);
        result = Response.noContent().build();
        return result;
    }
}
