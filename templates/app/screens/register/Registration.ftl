<div style="width: 300px; padding: 10px; background-color: #eee; margin: 30px; margin-left: auto; margin-right: auto; border: 1px solid #ccc;">
	<h1 style="margin: 10px 15px 30px 15px;">Cadastro de Usuários</h1>
	<form	id=""
			name=""
			method="POST"
			action="${link.action("RegistryControl").exec("register")}">
	
			<b>Nome</b> <br/>
			<input type="text" id="person.name" name="person.name" size="30"/>
			<br/>&nbsp;<br/>
			
			<b>Email</b> <br/> 
			<input type="text" id="person.email" name="person.email" size="30"/>
			<br/>&nbsp;<br/>
			
			<b>Senha</b> <br/>
			<input type="password" id="person.password" name="person.password" size="12"/>
			<br/>&nbsp;<br/>
			
			<b>Confirmação da Senha</b> <br/>
			<input type="password" id="passwordConfirmation" name="passwordConfirmation" size="12"/>
			<br/>&nbsp;<br/>
			
			<input type="submit" name="send" value="Enviar" />
	</form>
</div>