<task>
    <name>${task.name}</name>
    <link rel="self" href="${taskCollectionResource.uriInfo.baseUri}task/${task.id}"/>
    <link rel="http://sfwk.org/rest/tasks/move" href="${taskCollectionResource.uriInfo.baseUri}task/${task.id}/move?category={category-name}"/>
    <link rel="edit" href="${taskCollectionResource.uriInfo.baseUri}task/${task.id}"/>
    <resolved>${task.isResolved()?string}</resolved>
    <created>${task.created?datetime}</created>
    <updated>${task.updated?datetime}</updated>
</task>
