<categories>
    <link href="self" href="${categoryCollectionResource.uriInfo.baseUri}category?start=${categoryCollectionResource.start}&limit=${categoryCollectionResource.limit}"/>
<#if response?size == categoryCollectionResource.limit>
    <link href="next" href="${categoryCollectionResource.uriInfo.baseUri}category?start=${categoryCollectionResource.start + categoryCollectionResource.limit}&limit=${categoryCollectionResource.limit}"/>
</#if>
<#if (categoryCollectionResource.start >= categoryCollectionResource.limit)>
    <link href="previous" href="${categoryCollectionResource.uriInfo.baseUri}category?start=${categoryCollectionResource.start - categoryCollectionResource.limit}&limit=${categoryCollectionResource.limit}"/>
</#if>
<#list response as category>
    <category>
        <name>${category.name}</name>
        <link rel="self" href="${categoryCollectionResource.uriInfo.baseUri}category/${category.name}"/>
        <tasks>
            <#list category.tasks as task>
			<#include "/freemarker/task.ftl">
		</#list>
        </tasks>
    </category>
</#list>
    <categories>
