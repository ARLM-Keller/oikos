<h1>Cadastro de Usuários</h1>

<form 	action="${link.action("UserControl").exec("doStore")}"
		method="POST">

	Nome
	<input id="name" name="name" type="text"/>

	Email
	<input id="email" name="email" type="text"/>

	<input type="submit"/>
</form> 