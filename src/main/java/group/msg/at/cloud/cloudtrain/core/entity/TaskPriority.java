package group.msg.at.cloud.cloudtrain.core.entity;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Enumeration representing the priority of a {@link Task}.
 *
 * @author Michael Theis (mtheis@msg.group)
 * @version 1.0
 * @since release 1.0 31.10.2012 13:13:19
 */
@Schema(description = "enumeration representing the priority of a task")
public enum TaskPriority {

    UNDEFINED,
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}
