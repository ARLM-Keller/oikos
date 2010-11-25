<#assign personMeta = beanTool.getBeanInfo("oikos.user.Person") />
<h1>Cadastro de Usuários</h1>

<#if storeResults?? >
	<table cellPadding="6" cellSpacing="0" border="1">
		<tr>
			<th>Campo</th>
			<th>Valor</th>
			<th>Válido</th>
		<tr>
	<#list storeResults as storeResult>
		<#assign validatorContext = storeResult.validatorContext />
		<#assign bean = storeResult.bean />
		<#assign beanInfo = beanTool.getBeanInfo(bean) />
		Erros: ${validatorContext.errorCount}
		<br/>
		<#assign fields = validatorContext.fields />
		<#list fields as field>
			<#assign result = validatorContext.get(field) />
			<#assign value = beanInfo.getValueFormatted(field)!"" />
			<tr>
				<td>${field}</td>
				<td>${value}</td>
				<td>${result.isValid()?string}</td>
			</tr>				
		</#list>
	</#list>
	</table>
</#if>

<form	id=""
		name=""
		method="POST"
		action="${link.action("RegistryControl").exec("store")}">
	Nome <@f.field meta=personMeta fieldName="name"/>
	Email <@f.field meta=personMeta fieldName="email"/>
	Senha <@f.field meta=personMeta fieldName="password"/>
	<input type="submit" value="enviar"/>
</form>
