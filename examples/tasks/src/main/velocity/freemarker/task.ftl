#*
This file contains an Apache Velocity template. It uses FreeMarker's file extension (.ftl)
since we need to switch between FreeMarker / Velocity without changing the Java code (annotations).
*#
<task>
    <name>${task.name}</name>
    <link rel="self" href="${taskCollectionResource.uriInfo.baseUri}task/${task.id}"/>
    <link rel="http://sfwk.org/rest/tasks/move" href="${taskCollectionResource.uriInfo.baseUri}task/${task.id}/move?category={category-name}"/>
    <link rel="edit" href="${taskCollectionResource.uriInfo.baseUri}task/${task.id}"/>
    <resolved>${task.isResolved()}</resolved>
    <created>${task.created}</created>
    <updated>${task.updated}</updated>
</task>
