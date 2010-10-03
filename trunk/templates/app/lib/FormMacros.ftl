<#function generateDomId meta fieldName index=-1>
	<#if (index >= 0) >
		<#assign domId = meta.beanName+"["+index+"]."+fieldName />
	<#else>
		<#assign domId = meta.beanName+"."+fieldName />
	</#if>
	<#return domId />
</#function>

<#macro bean id meta>
	<#assign beanName = meta.beanName />
	<form	id="${beanName}Form"
			method="POST"
			action=""
			enctype="">
		<input type="hidden" id="${beanName}.id" name="${beanName}.id" value="${id}">
	<table id="${beanName}Table" cellPadding="4" cellSpacing="0" class="guara ${beanName}">
		<thead>
			<tr>
				<th colSpan="2">
					Cadastre ${beanName}
				</th>
			</tr>
		</thead>
		<tfoot>
			<tr>
				<td colSpan="2">
					<a href="${link.page("admin.guara.${beanName}.List")}">listar</a> 
					<#if (id > 0) >
						| 
						<a href="#" onClick="Guara.enableEditInPlace('${beanName}Table')">editar</a>
						<input class="guaraShow" type="button" value="confirmar" onClick="Guara.submitForm('${beanName}Form')">
						| 
						<a href="${link.page("admin.guara.${beanName}.List").action(action).exec("delete").add("id",id)}">remover</a>			
					<#else>
						<input type="button" value="confirmar" onClick="Guara.submitForm('${beanName}Form')">
					</#if>
				</td>
			</tr>
		</tfoot>
		<tbody>
			<#list meta.inputFields as property >	
				<#if property.includeOnDetail() >
					<tr>
						<td>${property.name}</td>
						<td></td>
					<#-- 
					<@g.showField meta=meta fieldName=property.name mode="table" />
					-->
				</#if>
			</#list>
		</tbody>
	</table>
	</form>
</#macro>

<#macro field meta fieldName index=-1 style="" size=0 maxLength=0 >
	<#assign property = meta.getInputField(fieldName) />
	<#assign domId = generateDomId(meta, fieldName, index) />
	<#if property.widgetType?? >
		<#assign switchOn = property.widgetType />
	<#else>
		<#assign switchOn = property.inputType />
	</#if>
	<div id="${domId}:field" <#if property.inputType != "hidden" >style="${style}" class="field"</#if>>
		<#switch switchOn >
			<#case "text">
			<#case "hidden">
			<#case "password">
			<#case "file">
			<#case "iMask">
				<input 	id="${domId}"
						<#if switchOn == "iMask">
							class="iMask"
							alt="${property.mask!""}"
							name="${domId}::${property.format}"
						<#else>
							name="${domId}"
						</#if>
						type="${property.inputType}"  
						value="${meta.getValueFormatted(fieldName)!""}" 
						size="<#if (size > 0) >${size}<#else>${property.size!""}</#if>"
						maxLength="<#if (maxLength > 0) >${maxLength}<#else><#if (size > 0) >${size}<#else>${property.maxLength!""}</#if></#if>"/>
			<#break>
			<#case "textarea">
				<textarea	id="${domId}"
							name="${domId}" 
							rows="${property.rows!"5"}" 
							cols="${property.cols!"30"}">${meta.getValueFormatted(fieldName)!""}</textarea>
			<#break>
			<#case "select">
			<#case "checkbox">
			<#case "radio">
				<#assign loader = referenceTool.getLoader("${property.referenceLoader}") />
				<#assign selected = (loader.defaultOption.value)!"0" />
				<#if selected != "0" && !meta.hasPrimaryKey() && switchOn == "select">
					<#assign script>
						dojo.addOnLoad(function () {
							dojo.debug("firing event onChange on: ${domId}");
							var evt = document.createEvent("HTMLEvents");
							evt.initEvent("change", false, false);
							dojo.byId('${domId}').dispatchEvent(evt);
						});
					</#assign>
					<#assign eventsToFire = eventsToFire + [script] />
				</#if>
				<#if property.value?? && (meta.bean.id > 0)>
					<#if property.isCollection() >
						<#assign selectedOptions = property.value />
						<#assign selected = "" />
					<#else>
						<#assign selected = property.value?string />
						<#assign selectedOptions = [] />						
					</#if>
				</#if>
				<br>
				<@g.combo 	id="${domId}" 
					name="${domId}"
					type="${property.inputType}" 
					options=loader.loadOptions(selected)![]
					selectedOption=selected
					selectedOptions=selectedOptions />				
			<#break>
			<#default>
				WARNING: Can't handle ${property.inputType} for ${property.name}.						
		</#switch>
	</div>
</#macro>
