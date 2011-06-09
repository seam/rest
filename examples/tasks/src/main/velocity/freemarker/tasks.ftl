#*
This file contains an Apache Velocity template. It uses FreeMarker's file extension (.ftl)
since we need to switch between FreeMarker / Velocity without changing the Java code (annotations).
*#
<tasks>
    <link href="self" href="${taskCollectionResource.uriInfo.absolutePath}?start=${taskCollectionResource.start}&limit=${taskCollectionResource.limit}"/>
    #if ( $response.size() == $taskCollectionResource.limit)
    #set( $next = $taskCollectionResource.start + $taskCollectionResource.limit )
    <link href="next" href="${taskCollectionResource.uriInfo.absolutePath}?start=${next}&limit=${taskCollectionResource.limit}"/>
    #end
    #if ( $taskCollectionResource.start >= $taskCollectionResource.limit )
    #set( $previous = $taskCollectionResource.start - $taskCollectionResource.limit)
    <link href="previous" href="${taskCollectionResource.uriInfo.absolutePath}?start=${previous}&limit=${taskCollectionResource.limit}"/>
    #end
    #foreach( ${task} in ${response} )
    #parse("/freemarker/task.ftl")
    #end
</tasks>
