<h1>Cadastro de Informações Pessoais</h1>

<#assign personMeta = beanTool.getBeanInfo("oikos.user.Person") />

<form	id=""
		name=""
		method="POST"
		action="${link.action("RegistryControl").exec("wizard")}">
	<input type="hidden" name="formGroups" value="oikos.user.Person" />
	<input type="hidden" name="step" value="1" />
	
	<b>Nome</b><br/>
	<@f.field meta=personMeta fieldName="name"/>
	<br/>&nbsp;<br/>

	<b>Email</b><br/>
	<input type="text" id="person.email" name="person.email" size="12" value="${registration.email}" disabled/>
	<br/>&nbsp;<br/>
	
	<b>Senha</b><br/>
	<@f.field meta=personMeta fieldName="password"/>
	<br/>&nbsp;<br/>
	
	<b>Confirmação da Senha</b><br/>
	<input type="password" id="passwordConfirmation" name="passwordConfirmation" size="12"/>
	<br/>&nbsp;<br/>
	
	<input id="sendForm" type="submit" name="btnSend" value="Próximo Passo"/>
</form>
 