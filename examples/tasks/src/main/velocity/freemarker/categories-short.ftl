#*
This file contains an Apache Velocity template. It uses FreeMarker's file extension (.ftl)
since we need to switch between FreeMarker / Velocity without changing the Java code (annotations).
*#
<categories>
    <link href="self" href="${categoryCollectionResource.uriInfo.baseUri}category?start=${categoryCollectionResource.start}&limit=${categoryCollectionResource.limit}"/>
    #if ( $response.size() == $categoryCollectionResource.limit )
    #set( $next = $categoryCollectionResource.start + $categoryCollectionResource.limit )
    <link href="next" href="${categoryCollectionResource.uriInfo.baseUri}category?start=${next}&limit=${categoryCollectionResource.limit}"/>
    #end
    #if ( $categoryCollectionResource.start >= $categoryCollectionResource.limit )
    #set( $previous = $categoryCollectionResource.start - $categoryCollectionResource.limit)
    <link href="previous" href="${categoryCollectionResource.uriInfo.baseUri}category?start=${previous}&limit=${categoryCollectionResource.limit}"/>
    #end
    #foreach( ${category} in ${response} )
    <category>
        <name>${category.name}</name>
        <link rel="self" href="${categoryCollectionResource.uriInfo.baseUri}category/${category.name}"/>
        <tasks>
            #foreach ( $task in $category.tasks )
            <task>
                <name>${task.name}</name>
                <link rel="self" href="${categoryCollectionResource.uriInfo.baseUri}task/${task.id}"/>
            </task>
            #end
        </tasks>
    </category>
    #end
    <categories>
