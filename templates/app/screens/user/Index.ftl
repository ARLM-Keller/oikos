<#assign personMeta = beanTool.getBeanInfo("oikos.user.Person")/>
<h1>Cadastro de Usuários</h1>

<#--
<@f.bean id=0 meta=personMeta/>
-->

<form	id=""
		name=""
		method="POST"
		action="${link.action("RegistryControl").exec("store")}">
	Nome <@f.field meta=personMeta fieldName="name"/>
	Email <@f.field meta=personMeta fieldName="email"/>
	<input type="submit" value="enviar"/>
</form>
