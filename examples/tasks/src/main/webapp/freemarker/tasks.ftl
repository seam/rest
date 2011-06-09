<tasks>
    <link href="self" href="${taskCollectionResource.uriInfo.absolutePath}?start=${taskCollectionResource.start}&limit=${taskCollectionResource.limit}"/>
<#if response?size == taskCollectionResource.limit>
    <link href="next" href="${taskCollectionResource.uriInfo.absolutePath}?start=${taskCollectionResource.start + taskCollectionResource.limit}&limit=${taskCollectionResource.limit}"/>
</#if>
<#if (taskCollectionResource.start >= taskCollectionResource.limit)>
    <link href="previous" href="${taskCollectionResource.uriInfo.absolutePath}?start=${taskCollectionResource.start - taskCollectionResource.limit}&limit=${taskCollectionResource.limit}"/>
</#if>
<#list response as task>
    <#include "/freemarker/task.ftl">
</#list>
</tasks>
